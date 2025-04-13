package com.debugideas.report_ms.helpers;

import com.debugideas.report_ms.models.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ReportHerlper {

    @Value("${report.template}")
    private String reportTemplate;

    public ReportHerlper(){}

    public String readTemplate(Company company){
        return reportTemplate
                .replace("{company}", company.getName())
                .replace("{foundation_date}", company.getFoundationDate().toString())
                .replace("{founder}", company.getFounder())
                .replace("{web_sites}", company.getWebsites().toString());
    }

    public List<String> getFromTemplate(String template){
        return Arrays.stream(template.split("\\{"))
                .map(String::trim)
                .filter(line -> line.contains("}"))
                .map(line -> line.substring(0, line.indexOf("}")))
                .toList();
    }
}
