package com.hackathon.proactivemonitoring.services;

import com.hackathon.proactivemonitoring.models.Transaction;

import java.time.Duration;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    String getTransactionStatus(Long id);

    List<Transaction> findTransactionsWithoutStatusUpdate(Duration maxDuration);
}

