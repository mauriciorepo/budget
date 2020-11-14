package com.finance.budget.resource;


import com.finance.budget.model.Company;
import com.finance.budget.resource.dto.CompanyDTO;
import com.finance.budget.service.CompanyService;
import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class CompanyController {

    private final CompanyService companyService;
    private final ModelMapper modelMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("CREATE A COMPANY")

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

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable Long id){
        Company company=companyService.getById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        companyService.delete(company);

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyDTO  update(@PathVariable Long id, @RequestBody @Valid  CompanyDTO dto){


        if((id!=dto.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "different id value");
        }
      return  companyService.getById(id).map(
              company-> {
                  //BeanUtils.copyProperties(company,dto);
                  company.setLocalization(dto.getLocalization());
                  company.setNeighborhood(dto.getNeighborhood());
                  company.setName(dto.getName());
                  company.setCellphone(dto.getCellphone());
                  company.setContactName(dto.getContactName());
                  company.setCountry(dto.getCountry());
                  company.setRegistrationDate(dto.getRegistrationDate());
                  company.setStateRegistration(dto.getStateRegistration());
                  company.setStateAbbrev(dto.getStateAbbrev());
                  company.setTelephone(dto.getTelephone());
                  company.setTelephone2(dto.getTelephone2());
                  company.setSituation(dto.isSituation());


                  Company updatableCompany =companyService.updateCompany(company);
                  return modelMapper.map(updatableCompany,CompanyDTO.class);

              }).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not exist"));

    }

    @GetMapping()
    public Page<CompanyDTO> listCompany( CompanyDTO dto, Pageable pageRequest){
        Company filter= modelMapper.map(dto, Company.class);

        Page<Company> result= companyService.listCompany(filter, pageRequest);

      List list= result.getContent()
                .stream()
                .map(entity->modelMapper.map(entity,CompanyDTO.class))
                .collect(Collectors.toList());

      return new PageImpl<CompanyDTO>(list,pageRequest,result.getTotalElements());
    }

    @GetMapping("/all")
    public List<CompanyDTO> getAllCompany(){
        List list=companyService.findCompanyList();
       return (List<CompanyDTO>) list.stream().map(entity->modelMapper.map(entity, CompanyDTO.class)).collect(Collectors.toList());
    }
}
