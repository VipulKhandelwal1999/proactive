package com.hackathon.proactivemonitoring.services.implementation;

import com.hackathon.proactivemonitoring.models.Transaction;
import com.hackathon.proactivemonitoring.repositories.TransactionRepository;
import com.hackathon.proactivemonitoring.services.AlertingService;
import com.hackathon.proactivemonitoring.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AlertingService alertingService;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        // Save the transaction to the database
        Transaction createdTransaction = transactionRepository.save(transaction);

        // Check if the transaction needs to raise an alert and do so if necessary
        // This logic can be adjusted based on the requirements
        if (createdTransaction.getStatus().equals("Available")) {
            alertingService.raiseAlert(createdTransaction);
        }

        return createdTransaction;
    }

    @Override
    public String getTransactionStatus(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.map(Transaction::getStatus).orElse(null);
    }

    @Override
    public List<Transaction> findTransactionsWithoutStatusUpdate(Duration maxDuration) {
        LocalDateTime threshold = LocalDateTime.now().minus(maxDuration);
        return transactionRepository.findTransactionsWithoutStatusUpdate(threshold);
    }
}

