package com.debugideas.companies_fallback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Website implements Serializable {
    private Long id;
    private String name;
    private Category category;
    private String description;
}
