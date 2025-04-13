package com.debugideas.companies_fallback;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private long id;
    private String name;
    private String founder;
    private String logo;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate foundationDate;
    private List<Website> websites;
}
