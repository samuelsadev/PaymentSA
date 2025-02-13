package com.projectsa.ProducerSA.controller;

import com.projectsa.ProducerSA.dto.ScheduledTransaction;
import com.projectsa.ProducerSA.dto.TransactionDTO;
import com.projectsa.ProducerSA.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<ScheduledTransaction> scheduleTransaction(@RequestBody TransactionDTO transactionDTO) {
        ScheduledTransaction scheduledTransaction = transactionService.scheduleTransaction(transactionDTO);
        return ResponseEntity.ok(scheduledTransaction);
    }
}
