package com.finance.budget.service;

import com.finance.budget.model.Company;

import java.util.Optional;

public interface CompanyService {
    Company create(Company company);

    Optional<Company> getById(Long id);
}
