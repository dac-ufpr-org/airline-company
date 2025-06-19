package com.mscliente.ms_cliente.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mscontracts.ms_contracts.queues.QueueNames;

/**
 * Configuração do RabbitMQ para o serviço de cliente.
 */
@Configuration
public class RabbitMQConfig {

    /**
     * Configura o conversor de mensagens para JSON.
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Configura o RabbitTemplate com o conversor JSON.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * Define a fila de cliente.
     */
    @Bean
    public Queue clienteQueue() {
        return new Queue(QueueNames.CLIENTE_QUEUE, true);
    }

    /**
     * Define a fila do orchestrator.
     */
    @Bean
    public Queue orchestratorQueue() {
        return new Queue(QueueNames.ORCHESTRATOR_QUEUE, true);
    }
} 