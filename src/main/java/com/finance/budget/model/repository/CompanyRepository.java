package com.finance.budget.model.repository;

import com.finance.budget.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {


    boolean existsByName(String name);
}
