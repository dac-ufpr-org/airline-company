package com.mssagas.ms_sagas.listener;

import com.mscontracts.ms_contracts.events.*;
import com.mscontracts.ms_contracts.queues.QueueNames;
import com.mssagas.ms_sagas.service.CadastroClienteSagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Listener responsável por receber eventos da SAGA de autocadastro.
 * Implementa o padrão Command/Reply para orquestração.
 * 
 * Eventos processados:
 * - SagaAutocadastroIniciadaEvent: Inicia nova SAGA
 * - UsuarioCriadoEvent: Confirma criação de usuário
 * - ClienteCriadoEvent: Confirma criação de cliente
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrchestratorListener {

    private final CadastroClienteSagaService sagaService;

    /**
     * Recebe evento inicial da SAGA de autocadastro do ms-cliente.
     * Inicia o processo de orquestração da SAGA.
     * 
     * @param event Evento de início da SAGA com dados do cliente
     */
    @RabbitListener(queues = QueueNames.ORCHESTRATOR_QUEUE)
    public void handleSagaAutocadastroIniciada(SagaAutocadastroIniciadaEvent event) {
        log.info("🎬 Orchestrator recebeu SagaAutocadastroIniciadaEvent: {} - correlationId: {}", 
                event.getDadosCliente().getEmail(), event.getCorrelationId());
        
        try {
            sagaService.iniciarSagaAutocadastro(event.getDadosCliente());
        } catch (Exception e) {
            log.error("❌ Erro ao processar SagaAutocadastroIniciadaEvent: {}", e.getMessage(), e);
        }
    }

    /**
     * Recebe evento de usuário criado com sucesso no ms-auth.
     * Próximo passo: criar cliente no ms-cliente.
     * 
     * @param event Evento de confirmação de criação de usuário
     */
    @RabbitListener(queues = QueueNames.ORCHESTRATOR_QUEUE)
    public void handleUsuarioCriado(UsuarioCriadoEvent event) {
        log.info("✅ Orchestrator recebeu UsuarioCriadoEvent: {} - correlationId: {}", 
                event.getEmail(), event.getCorrelationId());
        
        try {
            sagaService.processarUsuarioCriado(event);
        } catch (Exception e) {
            log.error("❌ Erro ao processar UsuarioCriadoEvent: {}", e.getMessage(), e);
        }
    }

    /**
     * Recebe evento de cliente criado com sucesso no ms-cliente.
     * Marca SAGA como completa.
     * 
     * @param event Evento de confirmação de criação de cliente
     */
    @RabbitListener(queues = QueueNames.ORCHESTRATOR_QUEUE)
    public void handleClienteCriado(ClienteCriadoEvent event) {
        log.info("✅ Orchestrator recebeu ClienteCriadoEvent: {} - correlationId: {}", 
                event.getEmail(), event.getCorrelationId());
        
        try {
            sagaService.processarClienteCriado(event);
        } catch (Exception e) {
            log.error("❌ Erro ao processar ClienteCriadoEvent: {}", e.getMessage(), e);
        }
    }

    /**
     * Recebe evento de falha na criação de usuário.
     * Inicia processo de compensação.
     */
    @RabbitListener(queues = QueueNames.ORCHESTRATOR_QUEUE)
    public void handleFalhaUsuario(String errorMessage) {
        log.error("Orchestrator recebeu falha na criação de usuário: {}", errorMessage);
        // Implementar lógica de compensação se necessário
    }

    /**
     * Recebe evento de falha na criação de cliente.
     * Inicia processo de compensação.
     */
    @RabbitListener(queues = QueueNames.ORCHESTRATOR_QUEUE)
    public void handleFalhaCliente(String errorMessage) {
        log.error("Orchestrator recebeu falha na criação de cliente: {}", errorMessage);
        // Implementar lógica de compensação se necessário
    }
}
