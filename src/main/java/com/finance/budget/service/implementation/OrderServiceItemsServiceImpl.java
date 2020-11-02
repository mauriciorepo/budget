package com.finance.budget.service.implementation;

import com.finance.budget.model.OrderServiceItems;
import com.finance.budget.model.repository.OrderServiceItemsRepository;

import com.finance.budget.service.OrderServiceItemsService;

import org.springframework.stereotype.Service;

@Service

public class OrderServiceItemsServiceImpl  implements OrderServiceItemsService {

    private  final OrderServiceItemsRepository repository;

    public OrderServiceItemsServiceImpl(OrderServiceItemsRepository repository){
        this.repository=repository;
    }
    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);

    }
    @Override
    public void delete(OrderServiceItems item){
        this.repository.delete(item);
    }
}
