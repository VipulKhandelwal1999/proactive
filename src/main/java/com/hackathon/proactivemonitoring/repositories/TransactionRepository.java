package com.hackathon.proactivemonitoring.repositories;

import com.hackathon.proactivemonitoring.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.status = 'RECEIVED' AND t.receivedTime <= :threshold")
    List<Transaction> findUnprocessedTransactions(@Param("threshold") LocalDateTime threshold);
}
