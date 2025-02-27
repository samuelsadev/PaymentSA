package com.projectsa.PaymentSA.controller;

import com.projectsa.PaymentSA.dto.ScheduledTransaction;
import com.projectsa.PaymentSA.dto.TransactionDTO;
import com.projectsa.PaymentSA.service.TransactionService;
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
