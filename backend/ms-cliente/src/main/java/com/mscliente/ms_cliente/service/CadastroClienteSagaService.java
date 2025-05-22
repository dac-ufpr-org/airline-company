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
        // 1. Preencher endereço via ViaCEP
        AddressDto endereco = viaCepService.consultarCep(dto.getCep());

        // 2. Gerar senha aleatória (4 dígitos)
        String senhaAleatoria = String.format("%04d", new SecureRandom().nextInt(10000));

        // 3. Publicar evento para ms-auth
        CriarUsuarioEvent event = new CriarUsuarioEvent(
                dto.getEmail(),
                "CLIENTE",
                senhaAleatoria
        );
        rabbitTemplate.convertAndSend("saga.criar.usuario", event);
    }
}
