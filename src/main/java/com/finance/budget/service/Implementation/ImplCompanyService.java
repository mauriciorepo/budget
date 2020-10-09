package com.finance.budget.service.Implementation;

import com.finance.budget.model.Company;
import com.finance.budget.model.repository.CompanyRepository;
import com.finance.budget.service.CompanyService;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Company> listCompany(Company company, Pageable page) {

        Example<Company> example= Example.of(company,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        return repository.findAll(example,page);
    }
}
