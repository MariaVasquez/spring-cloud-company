package com.debugideas.report_ms.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReportPublisher {

    private final StreamBridge streamBridge;

    public void publishReport(String report){
        streamBridge.send("consumerReport", report);
        streamBridge.send("consumerReport-in-O", "IN: "+report);
        streamBridge.send("consumerReport-out-O", "OUT: "+report);
    }
}
