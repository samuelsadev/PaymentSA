package com.projectsa.ProducerSA.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectsa.ProducerSA.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducerService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProducerService.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    public TransactionProducerService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendTransaction(TransactionDTO transactionDTO) {
        try {
            String transactionJson = objectMapper.writeValueAsString(transactionDTO);
            logger.info("[SENDING TRANSACTION] {}", transactionJson);

            rabbitTemplate.convertAndSend(exchange, routingKey, transactionJson);
            logger.info("[MESSAGE SENT] Exchange: {}, RoutingKey: {}", exchange, routingKey);
        } catch (Exception e) {
            logger.error("Failed to send transaction: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send transaction", e);
        }
    }
}
