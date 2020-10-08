package com.finance.budget.service.Implementation;

import com.finance.budget.model.Company;
import com.finance.budget.model.repository.CompanyRepository;
import com.finance.budget.service.CompanyService;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImplCompanyService implements CompanyService {

  private   CompanyRepository repository;

    public ImplCompanyService(CompanyRepository repository){
        this.repository=repository;
    }



    @Override
    public Company create(Company company) {

        return this.repository.save(company);
    }

    @Override
    public Optional<Company> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Company company) {
        if(company.getId() ==null || company ==null){
            throw new IllegalArgumentException("Company does not exist");
        }

        this.repository.delete(company);
    }

    @Override
    public Company updateCompany(Company company) {
        if(company.getId() ==null || company ==null){
            throw new IllegalArgumentException("Company does not exist");
        }

        repository.save(company);
        return company;
    }
}
