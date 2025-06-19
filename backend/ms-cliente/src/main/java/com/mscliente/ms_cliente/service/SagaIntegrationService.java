package com.mscliente.ms_cliente.service;

import com.mscontracts.ms_contracts.dto.client.ClientRequestDTO;
import com.mscontracts.ms_contracts.events.SagaAutocadastroIniciadaEvent;
import com.mscontracts.ms_contracts.queues.QueueNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Serviço responsável pela integração com o SAGA Orchestrator.
 * Gerencia comunicação entre ms-cliente e ms-sagas para processos de orquestração.
 * 
 * Funcionalidades:
 * - Inicialização de SAGAs de autocadastro
 * - Envio de eventos para o orchestrator
 * - Geração de correlationId único
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SagaIntegrationService {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Inicia a SAGA de autocadastro enviando evento para o orchestrator.
     * Cria correlationId único e envia dados do cliente para processamento.
     * 
     * Fluxo:
     * 1. Gera correlationId único
     * 2. Cria evento SagaAutocadastroIniciadaEvent
     * 3. Envia evento para fila do orchestrator
     * 4. Retorna correlationId para acompanhamento
     * 
     * @param dadosCliente Dados do cliente para autocadastro
     * @return CorrelationId único para acompanhamento da SAGA
     * @throws RuntimeException se houver erro na comunicação com RabbitMQ
     */
    public String iniciarSagaAutocadastro(ClientRequestDTO dadosCliente) {
        String correlationId = UUID.randomUUID().toString();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        log.info("🚀 Iniciando SAGA de autocadastro para cliente: {} com correlationId: {}", 
                dadosCliente.getEmail(), correlationId);
        
        try {
            // Criar evento para iniciar SAGA
            SagaAutocadastroIniciadaEvent event = new SagaAutocadastroIniciadaEvent(
                dadosCliente,
                correlationId,
                timestamp
            );
            
            // Enviar para o orchestrator
            log.info("📤 Enviando SagaAutocadastroIniciadaEvent para orchestrator: {}", correlationId);
            rabbitTemplate.convertAndSend(QueueNames.ORCHESTRATOR_QUEUE, event);
            
            log.info("✅ Evento SagaAutocadastroIniciadaEvent enviado com sucesso: {}", correlationId);
            
            return correlationId;
            
        } catch (Exception e) {
            log.error("❌ Erro ao iniciar SAGA de autocadastro: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao iniciar processo de autocadastro", e);
        }
    }
} 