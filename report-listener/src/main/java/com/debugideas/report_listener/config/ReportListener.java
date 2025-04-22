package com.debugideas.report_listener.config;

import com.debugideas.report_listener.documents.ReportDocument;
import com.debugideas.report_listener.repositories.ReportRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.function.Consumer;

@Configuration
@Slf4j
@AllArgsConstructor

public class ReportListener {

    private final ReportRepository reportDocument;

    @Bean
    public Consumer<String> consumerReport() {
        log.info(">> Listening to consumerReport");

        return message -> {
            log.info(">> Received report: {}", message);
            reportDocument.save(ReportDocument.builder().content(message).build());
        };
    }
}
