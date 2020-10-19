package com.finance.budget.service;

import com.finance.budget.model.Company;
import com.finance.budget.model.OrderService;
import com.finance.budget.resource.dto.CompanyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
public interface OrderServiceService {

    OrderService create(OrderService orderService);

    Page<OrderService> findOrderServiceByIdCompany(Company company, Pageable pageable);

    List<OrderService> findByIdCompany(Long id);
}