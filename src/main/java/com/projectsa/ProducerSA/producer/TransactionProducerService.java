package com.projectsa.ProducerSA.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectsa.ProducerSA.config.RabbitMQProducerConfig;
import com.projectsa.ProducerSA.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducerService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProducerService.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public TransactionProducerService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendTransaction(TransactionDTO transactionDTO) {
        try {
            String transactionJson = objectMapper.writeValueAsString(transactionDTO);
            logger.info("[SENDING TRANSACTION] {}", transactionJson);

            rabbitTemplate.convertAndSend(RabbitMQProducerConfig.EXCHANGE_TRANSACTIONS,
                    RabbitMQProducerConfig.ROUTING_KEY, transactionJson);
        } catch (Exception e) {
            logger.error("Failed to serialize transaction: {}", e.getMessage());
        }
    }
}