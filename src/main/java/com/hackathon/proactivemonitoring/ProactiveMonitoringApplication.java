package com.hackathon.proactivemonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProactiveMonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProactiveMonitoringApplication.class, args);
	}

}