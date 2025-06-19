package com.mssagas.ms_sagas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscontracts.ms_contracts.dto.client.ClientRequestDTO;
import com.mscontracts.ms_contracts.dto.saga.SagaStateDTO;
import com.mscontracts.ms_contracts.enums.SagaStatus;
import com.mscontracts.ms_contracts.events.*;
import com.mscontracts.ms_contracts.queues.QueueNames;
import com.mssagas.ms_sagas.config.RabbitMQSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Serviço responsável pela orquestração da SAGA de autocadastro de clientes.
 * Implementa o padrão Saga Orchestration com compensação automática.
 * 
 * Fluxo da SAGA:
 * 1. INICIADA: SAGA iniciada, aguardando criação de usuário
 * 2. USUARIO_CRIADO: Usuário criado no ms-auth, aguardando criação de cliente
 * 3. CLIENTE_CRIADO: Cliente criado no ms-cliente, SAGA completa
 * 4. COMPLETADA: SAGA finalizada com sucesso
 * 5. COMPENSANDO: Processo de compensação em andamento
 * 6. FALHOU: SAGA falhou e compensação foi executada
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CadastroClienteSagaService {

    private final RabbitMQSender rabbitMQSender;
    private final ObjectMapper objectMapper;
    
    // Armazena o estado das sagas em memória (em produção, usar Redis ou banco)
    private final Map<String, SagaStateDTO> sagaStates = new ConcurrentHashMap<>();

    /**
     * Inicia a SAGA de autocadastro de cliente.
     * Cria correlationId único e envia primeiro evento para ms-auth.
     * 
     * Fluxo: Cliente → Auth → Cliente → Email
     * 
     * @param dadosCliente Dados do cliente para autocadastro
     * @return CorrelationId único para acompanhamento da SAGA
     */
    public String iniciarSagaAutocadastro(ClientRequestDTO dadosCliente) {
        String correlationId = UUID.randomUUID().toString();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        log.info("🚀 Iniciando SAGA de autocadastro para cliente: {} com correlationId: {}", 
                dadosCliente.getEmail(), correlationId);
        
        try {
            // 1. Salvar estado inicial da SAGA
            SagaStateDTO initialState = new SagaStateDTO(
                correlationId, 
                SagaStatus.INICIADA, 
                objectMapper.writeValueAsString(dadosCliente), 
                timestamp
            );
            sagaStates.put(correlationId, initialState);
            
            // 2. Enviar evento para criação de usuário no ms-auth
            CriarUsuarioEvent criarUsuarioEvent = new CriarUsuarioEvent(
                dadosCliente.getEmail(),
                null, // Senha será gerada no ms-auth
                dadosCliente.getName(),
                correlationId
            );
            
            log.info("📤 Enviando evento CriarUsuarioEvent para ms-auth: {}", correlationId);
            rabbitMQSender.send(QueueNames.AUTH_QUEUE, criarUsuarioEvent);
            
            return correlationId;
            
        } catch (JsonProcessingException e) {
            log.error("❌ Erro ao serializar dados do cliente: {}", e.getMessage());
            throw new RuntimeException("Erro interno ao processar dados do cliente", e);
        }
    }

    /**
     * Processa evento de usuário criado com sucesso.
     * Atualiza estado da SAGA e envia próximo evento para ms-cliente.
     * 
     * @param event Evento de confirmação de criação de usuário
     */
    public void processarUsuarioCriado(UsuarioCriadoEvent event) {
        String correlationId = event.getCorrelationId();
        log.info("✅ Processando UsuarioCriadoEvent para correlationId: {}", correlationId);
        
        try {
            // Atualizar estado da SAGA
            SagaStateDTO currentState = sagaStates.get(correlationId);
            if (currentState == null) {
                log.error("❌ Estado da SAGA não encontrado para correlationId: {}", correlationId);
                return;
            }
            
            currentState.setStatus(SagaStatus.USUARIO_CRIADO);
            sagaStates.put(correlationId, currentState);
            
            // Recuperar dados do cliente do estado
            ClientRequestDTO dadosCliente = objectMapper.readValue(
                currentState.getDadosCliente(), 
                ClientRequestDTO.class
            );
            
            // Enviar evento para criação do cliente
            ClienteCriadoEvent clienteEvent = new ClienteCriadoEvent(
                dadosCliente.getCpf(),
                dadosCliente.getEmail(),
                dadosCliente.getName(),
                correlationId
            );
            
            log.info("📤 Enviando evento ClienteCriadoEvent para ms-cliente: {}", correlationId);
            rabbitMQSender.send(QueueNames.CLIENTE_QUEUE, clienteEvent);
            
        } catch (Exception e) {
            log.error("❌ Erro ao processar UsuarioCriadoEvent: {}", e.getMessage());
            iniciarCompensacao(correlationId, "Erro ao processar criação de usuário: " + e.getMessage());
        }
    }

    /**
     * Processa evento de cliente criado com sucesso.
     * Marca SAGA como completa e finaliza o processo.
     * 
     * @param event Evento de confirmação de criação de cliente
     */
    public void processarClienteCriado(ClienteCriadoEvent event) {
        String correlationId = event.getCorrelationId();
        log.info("✅ Processando ClienteCriadoEvent para correlationId: {}", correlationId);
        
        try {
            // Atualizar estado da SAGA
            SagaStateDTO currentState = sagaStates.get(correlationId);
            if (currentState == null) {
                log.error("❌ Estado da SAGA não encontrado para correlationId: {}", correlationId);
                return;
            }
            
            currentState.setStatus(SagaStatus.CLIENTE_CRIADO);
            sagaStates.put(correlationId, currentState);
            
            // Recuperar dados do cliente do estado
            ClientRequestDTO dadosCliente = objectMapper.readValue(
                currentState.getDadosCliente(), 
                ClientRequestDTO.class
            );
            
            // Marcar SAGA como completa
            log.info("🎉 SAGA completada com sucesso para correlationId: {}", correlationId);
            currentState.setStatus(SagaStatus.COMPLETADA);
            sagaStates.put(correlationId, currentState);
            
        } catch (Exception e) {
            log.error("❌ Erro ao processar ClienteCriadoEvent: {}", e.getMessage());
            iniciarCompensacao(correlationId, "Erro ao processar criação de cliente: " + e.getMessage());
        }
    }

    /**
     * Inicia processo de compensação quando algo falha na SAGA.
     * Envia evento de compensação para reverter operações já realizadas.
     * 
     * @param correlationId ID de correlação da SAGA
     * @param motivo Motivo da falha para compensação
     */
    private void iniciarCompensacao(String correlationId, String motivo) {
        log.warn("🔄 Iniciando compensação para correlationId: {} - Motivo: {}", correlationId, motivo);
        
        try {
            SagaStateDTO currentState = sagaStates.get(correlationId);
            if (currentState != null) {
                currentState.setStatus(SagaStatus.COMPENSANDO);
                currentState.setErro(motivo);
                sagaStates.put(correlationId, currentState);
            }
            
            // Enviar evento de compensação para ms-auth
            CompensarUsuarioEvent compensarEvent = new CompensarUsuarioEvent(
                null, // Será preenchido pelo listener
                correlationId,
                motivo
            );
            
            log.info("📤 Enviando evento CompensarUsuarioEvent para compensação: {}", correlationId);
            rabbitMQSender.send(QueueNames.COMPENSACAO_QUEUE, compensarEvent);
            
        } catch (Exception e) {
            log.error("❌ Erro ao iniciar compensação: {}", e.getMessage());
        }
    }

    /**
     * Consulta o estado atual de uma SAGA.
     * 
     * @param correlationId ID de correlação da SAGA
     * @return Estado atual da SAGA ou null se não encontrada
     */
    public SagaStateDTO consultarEstadoSaga(String correlationId) {
        return sagaStates.get(correlationId);
    }
} 