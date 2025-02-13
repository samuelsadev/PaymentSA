package com.projectsa.ProducerSA.producer;


import com.projectsa.ProducerSA.config.RabbitMQProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducerService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProducerService.class);

    private final RabbitTemplate rabbitTemplate;

    public TransactionProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTransaction(com.projectsa.ProducerSA.dto.TransactionDTO transactionDTO) {
        logger.info("[SENDING TRANSACTION] Origin: {}, Destination: {}, Amount: {}",
                transactionDTO.getOriginAccountNumber(),
                transactionDTO.getDestinationAccountNumber(),
                transactionDTO.getAmount());

        rabbitTemplate.convertAndSend(RabbitMQProducerConfig.EXCHANGE_TRANSACTIONS,
                RabbitMQProducerConfig.ROUTING_KEY, transactionDTO);
    }
}
