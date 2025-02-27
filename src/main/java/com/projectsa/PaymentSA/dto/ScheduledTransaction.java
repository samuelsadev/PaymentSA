package com.projectsa.PaymentSA.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Document(collection = "scheduled_transactions")
public class ScheduledTransaction {
    @Id
    private String id;
    private String originAccountNumber;
    private String destinationAccountNumber;
    private double amount;
    private LocalDate scheduledDate;
    private boolean processed;

    public ScheduledTransaction(String originAccountNumber, String destinationAccountNumber, double amount, LocalDate scheduledDate) {
        this.id = UUID.randomUUID().toString();
        this.originAccountNumber = originAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
        this.scheduledDate = scheduledDate;
        this.processed = false;
    }

    public String getId() {
        return id;
    }

    public String getOriginAccountNumber() {
        return originAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
