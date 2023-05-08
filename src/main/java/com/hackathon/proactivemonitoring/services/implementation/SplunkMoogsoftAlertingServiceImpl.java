package com.hackathon.proactivemonitoring.services.implementation;

import com.hackathon.proactivemonitoring.models.Transaction;
import com.hackathon.proactivemonitoring.services.AlertingService;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class SplunkMoogsoftAlertingServiceImpl implements AlertingService {

    @Value("${splunk.hec.url}")
    private String splunkHecUrl;

    @Value("${splunk.hec.token}")
    private String splunkHecToken;

    @Override
    public void raiseAlert(Transaction transaction) {
        // Build the JSON payload for the Splunk event
        String jsonPayload = String.format("{\"event\": \"Transaction Alert\", \"transaction_id\": \"%d\", \"transaction_status\": \"%s\", \"received_time\": \"%s\"}",
                transaction.getId(), transaction.getStatus(), transaction.getReceivedTime());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(splunkHecUrl);
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Splunk " + splunkHecToken);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setEntity(new StringEntity(jsonPayload, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    System.err.println("Error sending alert to Splunk. Status code: " + statusCode + ". Response: " + responseString);
                }
            }
        } catch (IOException e) {
            System.err.println("Error sending alert to Splunk: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
