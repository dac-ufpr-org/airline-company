package com.mscliente.ms_cliente.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.mscliente.ms_cliente.dto.ClientRequestDto;
import com.mscliente.ms_cliente.dto.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import java.security.SecureRandom;
import com.mscliente.ms_cliente.dto.CriarUsuarioEvent;

@Service
@RequiredArgsConstructor
public class CadastroClienteSagaService {

    private final RabbitTemplate rabbitTemplate;
    private final ViaCepService viaCepService;

    public void iniciarSagaAutocadastro(ClientRequestDto dto) {
        AddressDto endereco = viaCepService.consultarCep(dto.getCep());

        String senhaAleatoria = String.format("%04d", new SecureRandom().nextInt(10000));

        CriarUsuarioEvent event = new CriarUsuarioEvent();
        event.setEmail(dto.getEmail());
        event.setTipo("CLIENTE");
        event.setSenhaTemporaria(senhaAleatoria);

        rabbitTemplate.convertAndSend("saga.criar.usuario", event);
    }
}
