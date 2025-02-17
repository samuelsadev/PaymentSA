package com.projectsa.ProducerSA.service;

import com.projectsa.ProducerSA.dto.ScheduledTransaction;
import com.projectsa.ProducerSA.dto.TransactionDTO;
import com.projectsa.ProducerSA.producer.TransactionProducerService;
import com.projectsa.ProducerSA.repository.ScheduledTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionProducerService producerService;
    private final ScheduledTransactionRepository repository;

    public TransactionService(TransactionProducerService producerService, ScheduledTransactionRepository repository) {
        this.producerService = producerService;
        this.repository = repository;
    }

    public ScheduledTransaction scheduleTransaction(TransactionDTO transactionDTO) {
        ScheduledTransaction transaction = new ScheduledTransaction(
                transactionDTO.getOriginAccountNumber(),
                transactionDTO.getDestinationAccountNumber(),
                transactionDTO.getAmount()
        );

        repository.save(transaction);
        logger.info("[TRANSACTION SCHEDULED] ID: {}, Amount: {}", transaction.getId(), transaction.getAmount());
        return transaction;
    }

    @Scheduled(cron = "0 0 6 * * ?")
    @Transactional
    public void processScheduledTransactions() {
        List<ScheduledTransaction> transactions = repository.findByProcessedFalse();

        for (ScheduledTransaction transaction : transactions) {
            TransactionDTO transactionDTO = new TransactionDTO(
                    transaction.getOriginAccountNumber(),
                    transaction.getDestinationAccountNumber(),
                    transaction.getAmount()
            );

            try {
                producerService.sendTransaction(transactionDTO);
                transaction.setProcessed(true);
                repository.save(transaction);
                logger.info("[TRANSACTION SENT] ID: {}, Amount: {}", transaction.getId(), transaction.getAmount());
            } catch (Exception e) {
                logger.error("[FAILED TO SEND TRANSACTION] ID: {}, Error: {}", transaction.getId(), e.getMessage());
            }
        }
    }
}
