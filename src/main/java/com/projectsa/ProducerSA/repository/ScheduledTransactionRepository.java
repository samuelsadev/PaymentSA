package com.projectsa.ProducerSA.repository;


import com.projectsa.ProducerSA.dto.ScheduledTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledTransactionRepository extends MongoRepository<ScheduledTransaction, String> {
    List<ScheduledTransaction> findByProcessedFalse();
}
