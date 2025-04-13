package com.debugideas.report_ms.repositories;

import com.debugideas.report_ms.models.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
@Slf4j
public class CompaniesFallbackRepository {
    private final WebClient webClient;

    private final String baseUrl;

    public CompaniesFallbackRepository(WebClient webClient,
                                       @Value("${fallback.url}") String baseUrl){
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    public Company getCompany(String name){
        log.warn("Calling companies fallback {}", baseUrl);

        return webClient
                .get()
                .uri(baseUrl, name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Company.class).log().block();
    }
}
