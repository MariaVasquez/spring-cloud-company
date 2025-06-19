package com.debugideas.companies_fallback;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/company")
public class CompanyController {

    private static final Company DEFAULT_COMPANY = Company
            .builder()
            .id(0L)
            .founder("Fallback")
            .name("FallbackCompany")
            .logo("http://default-logo.com")
            .foundationDate(LocalDate.now())
            .websites(Collections.emptyList())
            .build();

    @GetMapping("/{name}")
    public ResponseEntity<Company> getByName(@PathVariable String name){
        return ResponseEntity.ok(DEFAULT_COMPANY);
    }

    @PostMapping
    public ResponseEntity<String> postFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("POST fallback: Company service unavailable.");
    }

}
