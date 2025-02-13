package com.projectsa.ProducerSA.dto;

public class TransactionDTO {
    private String originAccountNumber;
    private String destinationAccountNumber;
    private Double amount;

    public TransactionDTO() {
    }

    public TransactionDTO(String originAccountNumber, String destinationAccountNumber, Double amount) {
        this.originAccountNumber = originAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
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
}
