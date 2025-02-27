package com.projectsa.PaymentSA.service;

import com.projectsa.PaymentSA.dto.ScheduledTransaction;
import com.projectsa.PaymentSA.dto.TransactionDTO;
import com.projectsa.PaymentSA.producer.TransactionProducerService;
import com.projectsa.PaymentSA.repository.ScheduledTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        LocalDate now = LocalDate.now();
        LocalDateTime nowDateTime = LocalDateTime.now();

        if (transactionDTO.getScheduledDate().isBefore(now)) {
            throw new IllegalArgumentException("Scheduling for past dates is not permitted.");
        }

        if (transactionDTO.getScheduledDate().isAfter(now.plusDays(30))) {
            throw new IllegalArgumentException("The schedule cannot exceed 30 days.");
        }

        if (transactionDTO.getScheduledDate().isEqual(now.plusDays(1)) && nowDateTime.toLocalTime().isAfter(LocalTime.of(23, 0))) {
            throw new IllegalArgumentException("Appointments for the following day are only permitted until 11pm the day before.");
        }

        ScheduledTransaction transaction = new ScheduledTransaction(
                transactionDTO.getOriginAccountNumber(),
                transactionDTO.getDestinationAccountNumber(),
                transactionDTO.getAmount(),
                transactionDTO.getScheduledDate()
        );

        repository.save(transaction);
        logger.info("[TRANSACTION SCHEDULED] ID: {}, Amount: {}", transaction.getId(), transaction.getAmount());
        return transaction;
    }

    @Scheduled(cron = "0 0 6 * * ?")
    @Transactional
    public void processScheduledTransactions() {
        logger.info("[PROCESSING SCHEDULED TRANSACTIONS] Starting transaction processing at 6 AM");

        LocalDate today = LocalDate.now();
        List<ScheduledTransaction> transactions = repository.findByProcessedFalseAndScheduledDate(today);

        for (ScheduledTransaction transaction : transactions) {
            TransactionDTO transactionDTO = new TransactionDTO(
                    transaction.getOriginAccountNumber(),
                    transaction.getDestinationAccountNumber(),
                    transaction.getAmount(),
                    transaction.getScheduledDate()
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
