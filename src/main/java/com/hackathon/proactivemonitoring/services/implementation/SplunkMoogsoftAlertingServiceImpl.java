package com.hackathon.proactivemonitoring.services.implementation;

import com.hackathon.proactivemonitoring.models.Transaction;
import com.hackathon.proactivemonitoring.services.AlertingService;
import org.springframework.stereotype.Service;

@Service
public class SplunkMoogsoftAlertingServiceImpl implements AlertingService {

    @Override
    public void raiseAlert(Transaction transaction) {
        // Send the alert using the Splunk or Moogsoft API, or directly
    }
}
