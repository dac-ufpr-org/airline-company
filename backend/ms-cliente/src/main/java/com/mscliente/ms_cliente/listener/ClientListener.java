package com.mscliente.ms_cliente.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscontracts.ms_contracts.dto.client.ClientRequestDTO;
import com.mscliente.ms_cliente.service.ClientService;
import com.mscontracts.ms_contracts.events.ClienteCriadoEvent;
import com.mscontracts.ms_contracts.queues.QueueNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Listener responsável por receber eventos relacionados ao cliente.
 * Participa da SAGA de autocadastro como um dos serviços participantes.
 * 
 * Eventos processados:
 * - ClienteCriadoEvent: Cria cliente no banco de dados PostgreSQL
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClientListener {

    private final ClientService clientService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Recebe evento para criar cliente como parte da SAGA de autocadastro.
     * 
     * Fluxo:
     * 1. Recebe ClienteCriadoEvent do orchestrator
     * 2. Cria cliente no PostgreSQL
     * 3. Envia confirmação para o orchestrator
     * 
     * @param event Evento de criação de cliente
     */
    @RabbitListener(queues = QueueNames.CLIENTE_QUEUE)
    public void criarCliente(ClienteCriadoEvent event) {
        log.info("📥 Cliente recebeu solicitação para criar cliente: {} - correlationId: {}", 
                event.getEmail(), event.getCorrelationId());
        
        try {
            // Converter para DTO interno
            ClientRequestDTO clientRequestDto = new ClientRequestDTO();
            clientRequestDto.setCpf(event.getCpf());
            clientRequestDto.setEmail(event.getEmail());
            clientRequestDto.setName(event.getNome());
            
            // Criar cliente no banco de dados
            log.info("💾 Criando cliente no banco de dados: {}", event.getEmail());
            clientService.cadastrar(clientRequestDto);
            
            // Enviar evento de sucesso para o orchestrator
            ClienteCriadoEvent clienteCriadoEvent = new ClienteCriadoEvent(
                event.getCpf(),
                event.getEmail(),
                event.getNome(),
                event.getCorrelationId()
            );
            
            log.info("📤 Enviando ClienteCriadoEvent para orchestrator: {}", event.getCorrelationId());
            rabbitTemplate.convertAndSend(QueueNames.ORCHESTRATOR_QUEUE, clienteCriadoEvent);
            
        } catch (Exception e) {
            log.error("❌ Erro ao criar cliente: {} - correlationId: {}", 
                    e.getMessage(), event.getCorrelationId());
            
            // Enviar evento de falha para o orchestrator
            String errorMessage = "Falha na criação de cliente: " + e.getMessage();
            log.error("📤 Enviando evento de falha para orchestrator: {}", errorMessage);
            rabbitTemplate.convertAndSend(QueueNames.ORCHESTRATOR_QUEUE, errorMessage);
        }
    }
} 