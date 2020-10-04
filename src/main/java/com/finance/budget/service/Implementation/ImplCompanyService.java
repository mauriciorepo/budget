package com.finance.budget.service.Implementation;

import com.finance.budget.model.Company;
import com.finance.budget.model.repository.CompanyRepository;
import com.finance.budget.service.CompanyService;
import org.springframework.stereotype.Service;

@Service
public class ImplCompanyService implements CompanyService {

    CompanyRepository repository;

    public ImplCompanyService(CompanyRepository repository){
        this.repository=repository;
    }



    @Override
    public Company create(Company company) {
        return this.repository.save(company);
    }
}
