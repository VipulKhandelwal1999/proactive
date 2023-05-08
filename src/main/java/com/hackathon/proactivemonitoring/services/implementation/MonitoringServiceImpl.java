package com.hackathon.proactivemonitoring.services.implementation;

import com.hackathon.proactivemonitoring.models.Transaction;
import com.hackathon.proactivemonitoring.repositories.TransactionRepository;
import com.hackathon.proactivemonitoring.services.AlertingService;
import com.hackathon.proactivemonitoring.services.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AlertingService alertingService;

    @Override
    public void monitorTransactions() {
        // Calculate the threshold time for raising an alert
        int c = 5; // The number of minutes to wait before raising an alert
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(c);

        // Query the database to check for transactions not updated within the specified time
        List<Transaction> transactions = transactionRepository.findUnprocessedTransactions(threshold);

        // Raise alerts for unprocessed transactions
        for (Transaction transaction : transactions) {
            alertingService.raiseAlert(transaction);
        }
    }

    @Scheduled(fixedRate = 60000) // Run the monitoring task every minute (60000 ms)
    public void scheduleMonitoringTask() {
        monitorTransactions();
    }
}
