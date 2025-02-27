package com.projectsa.PaymentSA.serviceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projectsa.PaymentSA.dto.ScheduledTransaction;
import com.projectsa.PaymentSA.dto.TransactionDTO;
import com.projectsa.PaymentSA.producer.TransactionProducerService;
import com.projectsa.PaymentSA.repository.ScheduledTransactionRepository;
import com.projectsa.PaymentSA.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

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
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        transactionDTO = new TransactionDTO("12345", "67890", 100.0, tomorrow);
        scheduledTransaction = new ScheduledTransaction("12345", "67890", 100.0, tomorrow);
    }

    @Test
    void testScheduleTransaction() {
        // Simula o retorno de um objeto ScheduledTransaction com UUID aleatório
        when(repository.save(any(ScheduledTransaction.class))).thenReturn(scheduledTransaction);
        ScheduledTransaction result = transactionService.scheduleTransaction(transactionDTO);
        assertNotNull(result);
        assertEquals(transactionDTO.getScheduledDate(), result.getScheduledDate());
        verify(repository, times(1)).save(any(ScheduledTransaction.class));
    }


    @Test
    void testProcessScheduledTransactions() {
        LocalDate today = LocalDate.now();
        when(repository.findByProcessedFalseAndScheduledDate(today)).thenReturn(List.of(scheduledTransaction));

        transactionService.processScheduledTransactions();

        verify(producerService, times(1)).sendTransaction(any(TransactionDTO.class));
        verify(repository, times(1)).save(any(ScheduledTransaction.class));
    }
}
