package com.finance.budget.service.implementation;



import java.util.List;
import java.util.Optional;

import com.finance.budget.model.Company;
import com.finance.budget.model.OrderService;
import com.finance.budget.model.repository.OrderServiceRepository;
import com.finance.budget.service.OrderServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceServiceImpl implements OrderServiceService {

    private final OrderServiceRepository orderServiceRepository;

    public OrderServiceServiceImpl(OrderServiceRepository orderServiceRepository) {
       this.orderServiceRepository=orderServiceRepository;
    }

    @Override
    public OrderService create(OrderService orderService) {
        return this.orderServiceRepository.save(orderService);
    }

    @Override
    public Page<OrderService> findOrderServiceByIdCompany(Company company, Pageable pageable) {
        return orderServiceRepository.findOrderServiceByIdCompanyOrName(company.getId(), company.getName(), pageable);

    }

    @Override
    public List<OrderService> findByIdCompany(Long id) {
        return orderServiceRepository.findOrderServiceByIdCompany(id);
    }

    @Override
    public OrderService update(OrderService orderService) {
        if(orderService.getId()==null || orderService ==null){
            throw new IllegalArgumentException("Order does not exist");
        }
         orderServiceRepository.save(orderService);
        return orderService;
    }

    @Override
    public Optional<OrderService> getById(long id) {
        return orderServiceRepository.findById(id);
    }


}
