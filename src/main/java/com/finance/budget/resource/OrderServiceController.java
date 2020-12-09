package com.finance.budget.resource;

import javax.validation.Valid;



import com.finance.budget.model.Company;
import com.finance.budget.model.OrderService;
import com.finance.budget.model.OrderServiceItems;
import com.finance.budget.resource.dto.CompanyDTO;
import com.finance.budget.resource.dto.OrderServiceDTO;
import com.finance.budget.resource.dto.OrderServiceItemsDTO;
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
import org.springframework.web.bind.annotation.PutMapping;
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

             order.getList().forEach(entity-> entity.setOrderService(order));

             return orderServiceService.create(order);

        }).orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not exist"));

    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderServiceDTO> findOrderServiceByIdCompany(CompanyDTO companyDTO, Pageable pageRequest){
        Company company= modelMapper.map(companyDTO, Company.class);

        Page<OrderService> resultList=orderServiceService.findOrderServiceByIdCompany(company, pageRequest);
        modelMapper.typeMap(OrderServiceItems.class, OrderServiceItemsDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getOrderService().getId(),
                    OrderServiceItemsDTO::setORDERSERVICE_ID);
        });
        List list=resultList.getContent()
                .stream()
                .map(entity->modelMapper.map(entity, OrderServiceDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<OrderServiceDTO>(list,pageRequest,resultList.getTotalElements());

    }

    @GetMapping("{id}/company")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderServiceDTO> findOrderService(@PathVariable Long id){

       List<OrderService> listResult= orderServiceService.findByIdCompany(id);

        List<OrderServiceDTO> list=listResult
                .stream()
                .map(  entity-> OrderServiceToDTO(entity) )
                .collect(Collectors.toList());

       return list;
    }






    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderServiceDTO update(@PathVariable Long id, @RequestBody OrderServiceDTO orderServiceDTO){

        if((id!=orderServiceDTO.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "different id value");
        }
        OrderService order=orderServiceService.getById(id).map(orderService-> {

            orderService.setAnnotation(orderServiceDTO.getAnnotation());
            orderService.setStatus(orderServiceDTO.getStatus());
            orderService.setTitle(orderServiceDTO.getTitle());
            orderService.setDescription(orderServiceDTO.getDescription());
            return orderService;
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not exist" ));

        List<OrderServiceItems> listItems=orderServiceDTO
                .getList()
                .stream()
                .map(orderServiceItemsDTO-> DTOToOrderServiceItems(orderServiceItemsDTO))
                .collect(Collectors.toList());
        order.updateItems(listItems);

        OrderService updateOrderService= orderServiceService.update(order);

        return OrderServiceToDTO(updateOrderService);

    }


    private OrderServiceDTO OrderServiceToDTO(OrderService entity){
        modelMapper.typeMap(OrderServiceItems.class,OrderServiceItemsDTO.class ).addMappings(mapper ->{
            mapper.map(src -> src.getOrderService().getId(),(dest, v) -> dest.setORDERSERVICE_ID((Long) v) );

        });

        modelMapper.typeMap(OrderService.class,OrderServiceDTO.class ).addMappings(mapper ->{
            mapper.map(src -> src.getCompany().getId(),(dest, v) -> dest.setId_company((Long) v) );

        });

        return modelMapper.map(entity, OrderServiceDTO.class );
    }

    private OrderServiceItems DTOToOrderServiceItems(OrderServiceItemsDTO dto){
        modelMapper.typeMap(OrderServiceItemsDTO.class,OrderServiceItems.class ).addMappings(mapper ->{
            mapper.map(src -> src.getORDERSERVICE_ID(),(dest, v) -> dest.getOrderService().setId((Long) v) );

        });
        return modelMapper.map(dto,OrderServiceItems.class);
    }

}
