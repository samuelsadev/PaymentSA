package com.projectsa.PaymentSA.repository;


import com.projectsa.PaymentSA.dto.ScheduledTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduledTransactionRepository extends MongoRepository<ScheduledTransaction, String> {
    List<ScheduledTransaction> findByProcessedFalseAndScheduledDate(LocalDate scheduledDate);
}
