package com.finance.budget.model.repository;

import com.finance.budget.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {


    boolean existsByName(String name);

    //@Query("select c from Company c where c.name like :name")
    List<Company> findByNameContainingIgnoreCase(@Param("name") String name);

}
