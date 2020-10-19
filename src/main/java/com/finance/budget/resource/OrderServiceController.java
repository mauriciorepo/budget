package com.finance.budget.resource;

import javax.validation.Valid;



import com.finance.budget.model.Company;
import com.finance.budget.model.OrderService;
import com.finance.budget.resource.dto.CompanyDTO;
import com.finance.budget.resource.dto.OrderServiceDTO;
import com.finance.budget.service.CompanyService;
import com.finance.budget.service.OrderServiceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders-service")
@RequiredArgsConstructor

public class OrderServiceController {

    private final CompanyService companyService;
    private final OrderServiceService orderServiceService;
    private final ModelMapper modelMapper;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderService create(@RequestBody @Valid OrderServiceDTO dto){


        return companyService.getById(dto.getId_company()).map(company -> {

             OrderService order=modelMapper.map(dto,OrderService.class);

             order.setCompany(company);

             return orderServiceService.create(order);

        }).orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not exist"));

    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderServiceDTO> findOrderService(CompanyDTO companyDTO, Pageable pageRequest){
        Company company= modelMapper.map(companyDTO, Company.class);

        Page<OrderService> resultList=orderServiceService.findOrderServiceByIdCompany(company, pageRequest);

        List list=resultList.getContent()
                .stream()
                .map(entity->modelMapper.map(entity, OrderServiceDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<OrderServiceDTO>(list,pageRequest,resultList.getTotalElements());

    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderService> findOrderServiceByIdCompany(@PathVariable Long id){

       List<OrderService> listResult= orderServiceService.findByIdCompany(id);

       return listResult;
    }
 }
