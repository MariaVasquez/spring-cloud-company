package com.debugideas.co.company.services.impl;

import com.debugideas.co.company.entities.Category;
import com.debugideas.co.company.entities.Company;
import com.debugideas.co.company.repositories.CompanyRepository;
import com.debugideas.co.company.services.CompanyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company create(Company company) {
        company.getWebsites().forEach(website -> {
            if(Objects.isNull(website.getCategory())){
                website.setCategory(Category.NONE);
            }
        });
        return companyRepository.save(company);
    }

    @Override
    public Company getByName(String name) {
        return companyRepository
                .findByName(name)
                .orElseThrow(()-> new NoSuchElementException("Company not found"));
    }

    @Override
    public Company update(Company company, String name) {
        Company setCompany = getByName(name);
        setCompany.setLogo(company.getLogo());
        setCompany.setFounder(company.getFounder());
        setCompany.setFoundationDate(company.getFoundationDate());
        return companyRepository.save(setCompany);
    }

    @Override
    public void delete(String name) {
        companyRepository.delete(getByName(name));
    }
}
