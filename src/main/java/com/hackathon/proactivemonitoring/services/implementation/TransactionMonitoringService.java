package com.hackathon.proactivemonitoring.services.implementation;

import com.hackathon.proactivemonitoring.models.Transaction;
import com.hackathon.proactivemonitoring.services.AlertingService;
import com.hackathon.proactivemonitoring.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class TransactionMonitoringService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AlertingService alertingService;

    @Value("${transaction.alert.maxDuration:5m}")
    private Duration maxDuration;

    @Scheduled(fixedDelayString = "${transaction.monitoring.interval:300000}") // Default: 5 minutes
    public void monitorTransactions() {
        List<Transaction> transactions = transactionService.findTransactionsWithoutStatusUpdate(maxDuration);
        for (Transaction transaction : transactions) {
            alertingService.raiseAlert(transaction);
        }
    }
}
