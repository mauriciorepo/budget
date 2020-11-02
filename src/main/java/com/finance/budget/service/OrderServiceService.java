package com.finance.budget.service;

import com.finance.budget.model.Company;
import com.finance.budget.model.OrderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface OrderServiceService {

    OrderService create(OrderService orderService);

    Page<OrderService> findOrderServiceByIdCompany(Company company, Pageable pageable);

    List<OrderService> findByIdCompany(Long id);

    OrderService update(OrderService orderService);

    Optional<OrderService> getById(long id);
}
