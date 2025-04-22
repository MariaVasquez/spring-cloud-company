package com.debugideas.report_listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReportListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportListenerApplication.class, args);
	}

}
