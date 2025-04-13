package com.debugideas.companies_fallback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CompaniesFallbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompaniesFallbackApplication.class, args);
	}

}
