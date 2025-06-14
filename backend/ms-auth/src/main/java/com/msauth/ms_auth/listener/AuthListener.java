package com.msauth.ms_auth.listener;

import com.mscontracts.ms_contracts.events.CriarUsuarioEvent;
import com.mscontracts.ms_contracts.queues.QueueNames;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuthListener {

    @RabbitListener(queues = QueueNames.AUTH_QUEUE)
    public void criarUsuario(CriarUsuarioEvent event) {
        System.out.println("Auth recebeu solicitação para criar usuário: " + event.getEmail());
        // Aqui: lógica de criar usuário
        // Depois: enviar um "UsuarioCriadoEvent" (que você também criará no ms-contracts)
    }
}
