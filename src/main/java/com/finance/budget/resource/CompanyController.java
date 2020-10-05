package com.finance.budget.resource;

import com.finance.budget.exception.BusinessException;
import com.finance.budget.model.Company;
import com.finance.budget.model.dto.CompanyDTO;
import com.finance.budget.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController()
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO create(@RequestBody @Valid CompanyDTO companyDTO){

        Company company= modelMapper.map(companyDTO, Company.class);

        company= companyService.create(company);

        return modelMapper.map(company, CompanyDTO.class);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyDTO findCompanyById(@PathVariable Long id){

        return   companyService
                .getById(id)
                .map(company ->modelMapper.map(company, CompanyDTO.class) )
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));


    }
}
