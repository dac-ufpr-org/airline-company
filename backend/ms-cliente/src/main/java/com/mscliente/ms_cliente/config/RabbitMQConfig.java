package com.mscliente.ms_cliente.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    @Bean
    public Queue criarUsuarioQueue() {
        return new Queue("saga.criar.usuario");
    }

    @Bean
    public Queue compensarUsuarioQueue() {
        return new Queue("saga.compensar.usuario");
    }
}
