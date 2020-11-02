package com.finance.budget.service;

import com.finance.budget.model.OrderServiceItems;
import org.springframework.stereotype.Service;


public interface OrderServiceItemsService {

    void delete(Long idd);

    void delete(OrderServiceItems item);
}
