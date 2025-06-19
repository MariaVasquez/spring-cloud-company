package com.debugideas.co.company.controllers;

import com.debugideas.co.company.entities.Company;
import com.debugideas.co.company.services.CompanyService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/company")
@Tag(name = "Company resource")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/{name}")
    @Operation(summary = "Get by name company")
    @Observed(name = "company.name")
    @Timed(value = "company.name")
    public ResponseEntity<Company> getByName(@PathVariable String name){
        /*try{
            Thread.sleep(7000);
        }catch (Exception e){
            throw new RuntimeException(e);
        }*/
        log.info("GET company: {}", name);
        return ResponseEntity.ok(companyService.getByName(name));
    }

    @PostMapping
    @Operation(summary = "Create a company")
    @Observed(name = "company.save")
    @Timed(value = "company.save")
    public ResponseEntity<Company> create(@RequestBody Company companyRequest){
        Company company = companyService.create(companyRequest);
        return ResponseEntity
                .created(URI.create(company.getName()))
                .body(company);
    }

    @PutMapping("/{name}")
    @Operation(summary = "Update a company")
    public ResponseEntity<Company> update(@RequestBody Company company, @PathVariable String name){
        return ResponseEntity
                .ok(companyService.update(company, name));
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Delete a company")
    public ResponseEntity<Void> delete(@PathVariable String name){
        companyService.delete(name);
        return ResponseEntity.noContent().build();
    }
}
