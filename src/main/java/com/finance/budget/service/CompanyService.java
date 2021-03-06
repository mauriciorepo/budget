package com.finance.budget.service;

import com.finance.budget.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

public interface CompanyService {

    Company create(Company company);

    Optional<Company> getById(Long id);

    void delete(Company company);

    Company updateCompany(Company any);

    Page<Company> listCompany(Company company, Pageable page);

    List<Company> findCompanyList();

    List<Company> findCompanyByName(String name);
}
