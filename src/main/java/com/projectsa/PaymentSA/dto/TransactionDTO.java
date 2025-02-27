package com.projectsa.PaymentSA.dto;

import java.time.LocalDate;

public class TransactionDTO {
    private String originAccountNumber;
    private String destinationAccountNumber;
    private Double amount;
    private LocalDate scheduledDate;

    public TransactionDTO() {
    }

    public TransactionDTO(String originAccountNumber, String destinationAccountNumber, Double amount, LocalDate scheduledDate) {
        this.originAccountNumber = originAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
        this.scheduledDate = scheduledDate;
    }

    public String getOriginAccountNumber() {
        return originAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }
}
