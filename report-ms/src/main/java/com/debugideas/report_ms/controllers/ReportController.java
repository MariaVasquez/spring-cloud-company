package com.debugideas.report_ms.controllers;

import com.debugideas.report_ms.helpers.ReportHerlper;
import com.debugideas.report_ms.services.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/report")
public class ReportController {
    private final ReportService reportService;


    @GetMapping("/{name}")
    public ResponseEntity<String> getByName(@PathVariable String name){
        return ResponseEntity.ok(reportService.makeReport(name));
    }

    @PostMapping
    public ResponseEntity<String> saveReport(@RequestBody String name){
        return ResponseEntity.ok(reportService.saveReport(name));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteReport(@PathVariable String name){
        reportService.deleteReport(name);
        return ResponseEntity.noContent().build();
    }
}
