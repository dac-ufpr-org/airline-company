package com.mssagas.ms_sagas.service;

import com.mscontracts.ms_contracts.events.CriarUsuarioEvent;
import com.mscontracts.ms_contracts.queues.QueueNames;
import com.mssagas.ms_sagas.config.RabbitMQSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CadastroClienteSagaService {

    private final RabbitMQSender rabbitMQSender;

    public interface CadastroClienteSagaService {

        void iniciarSagaAutocadastro(ClientRequestDto dto);  // Alterado para receber DTO
    }

    public void iniciarSagaAutocadastro(String email, String senha, String nome) {
        String correlationId = UUID.randomUUID().toString();

        CriarUsuarioEvent event = new CriarUsuarioEvent(email, senha, nome, correlationId);

        System.out.println("Orchestrator disparando criação de usuário para ms-auth");

        rabbitMQSender.send(QueueNames.AUTH_QUEUE, event);
    }
}
