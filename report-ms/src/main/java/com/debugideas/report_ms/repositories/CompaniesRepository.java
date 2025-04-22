package com.debugideas.report_ms.repositories;

import com.debugideas.report_ms.models.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "COMPANIES")
public interface CompaniesRepository {

    @GetMapping("/companies/api/company/{name}")
    Optional<Company> getByName(@PathVariable String name);

    @PostMapping("/companies/api/company")
    Optional<Company> postByName(@RequestBody Company company);

    @DeleteMapping("/companies/api/company/{name}")
    void deleteByName(@PathVariable String name);
}
