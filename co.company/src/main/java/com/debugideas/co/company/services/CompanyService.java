package com.debugideas.co.company.services;

import com.debugideas.co.company.entities.Company;

public interface CompanyService {
    Company create(Company company);
    Company getByName(String name);
    Company update(Company company, String name);
    void delete(String name);
}
