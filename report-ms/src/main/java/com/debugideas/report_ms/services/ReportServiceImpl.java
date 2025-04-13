package com.debugideas.report_ms.services;

import com.debugideas.report_ms.helpers.ReportHerlper;
import com.debugideas.report_ms.models.Company;
import com.debugideas.report_ms.models.Website;
import com.debugideas.report_ms.repositories.CompaniesRepository;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@AllArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final CompaniesRepository companiesRepository;
    private final EurekaClient eurekaClient;
    private final ReportHerlper reportHerlper;

    @Override
    public String makeReport(String name) {
        return reportHerlper.readTemplate(companiesRepository.getByName(name).orElseThrow());
    }

    @Override
    public String saveReport(String report) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<String> template = reportHerlper.getFromTemplate(report);
        List<Website> website = Stream.of(template.get(3))
                .map(web-> Website.builder().name(web).build())
                .toList();

        Company company = Company.builder()
                .name(template.get(0))
                .foundationDate(LocalDate.parse(template.get(1), dateFormat))
                .founder(template.get(2))
                .websites(website)
                .build();

        companiesRepository.postByName(company);
        return "Ok";
    }

    @Override
    public void deleteReport(String name) {
        companiesRepository.deleteByName(name);
    }
}
