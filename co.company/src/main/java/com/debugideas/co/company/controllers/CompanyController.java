package com.debugideas.co.company.controllers;

import com.debugideas.co.company.entities.Company;
import com.debugideas.co.company.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/company")
@Tag(name = "Company resource")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/{name}")
    @Operation(summary = "Get by name company")
    public ResponseEntity<Company> getByName(@PathVariable String name){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(companyService.getByName(name));
    }

    @PostMapping
    @Operation(summary = "Create a company")
    public ResponseEntity<Company> create(@RequestBody Company company){
        return ResponseEntity
                .created(URI.create(companyService.create(company).getName()))
                .build();
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
