package com.projectsa.ProducerSA.serviceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projectsa.ProducerSA.dto.ScheduledTransaction;
import com.projectsa.ProducerSA.dto.TransactionDTO;
import com.projectsa.ProducerSA.producer.TransactionProducerService;
import com.projectsa.ProducerSA.repository.ScheduledTransactionRepository;
import com.projectsa.ProducerSA.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionProducerService producerService;

    @Mock
    private ScheduledTransactionRepository repository;

    @InjectMocks
    private TransactionService transactionService;

    private TransactionDTO transactionDTO;
    private ScheduledTransaction scheduledTransaction;

    @BeforeEach
    void setUp() {
        transactionDTO = new TransactionDTO("12345", "67890", 100.0);
    }

    @Test
    void testScheduleTransaction() {
        when(repository.save(any(ScheduledTransaction.class))).thenReturn(scheduledTransaction);
        ScheduledTransaction result = transactionService.scheduleTransaction(transactionDTO);
        assertNotNull(result);
        assertEquals(scheduledTransaction.getId(), result.getId());
        verify(repository, times(1)).save(any(ScheduledTransaction.class));
    }

    @Test
    void testProcessScheduledTransactions() {
        when(repository.findByProcessedFalse()).thenReturn(List.of(scheduledTransaction));

        transactionService.processScheduledTransactions();

        verify(producerService, times(1)).sendTransaction(any(TransactionDTO.class));
        verify(repository, times(1)).save(any(ScheduledTransaction.class));
    }
}
