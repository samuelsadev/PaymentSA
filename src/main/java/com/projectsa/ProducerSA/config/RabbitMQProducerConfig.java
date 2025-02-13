package com.projectsa.ProducerSA.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQProducerConfig {

    public static final String QUEUE_TRANSACTIONS = "transaction_queue";
    public static final String EXCHANGE_TRANSACTIONS = "transaction_exchange";
    public static final String ROUTING_KEY = "transaction_routingKey";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_TRANSACTIONS, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_TRANSACTIONS);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
