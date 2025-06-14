package com.mssagas.ms_sagas.listener;

import com.mscontracts.ms_contracts.events.CriarUsuarioEvent;
import com.mscontracts.ms_contracts.queues.QueueNames;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrchestratorListener {

    @RabbitListener(queues = QueueNames.ORCHESTRATOR_QUEUE)
    public void handleClientCreation(CriarUsuarioEvent event) {
        System.out.println("Orchestrator recebeu evento: " + event.getEmail());
        // Lógica: chamar ms-auth, etc...
    }
}
