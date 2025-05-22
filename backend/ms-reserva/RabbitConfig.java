package com.msreserva.ms_reserva.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "reserva.exchange";
    public static final String QUEUE = "reserva.queue";
    public static final String ROUTING_KEY = "reserva.nova";

    @Bean
    public Exchange reservaExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue reservaQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding reservaBinding(Queue reservaQueue, Exchange reservaExchange) {
        return BindingBuilder.bind(reservaQueue).to(reservaExchange).with(ROUTING_KEY).noargs();
    }
}
