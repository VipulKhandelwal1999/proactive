package com.hackathon.proactivemonitoring.services;

import com.hackathon.proactivemonitoring.models.Transaction;

public interface AlertingService {
    void raiseAlert(Transaction transaction);
}
