package com.finance.budget.service;

import java.util.Optional;

import com.finance.budget.model.OrderService;
import com.finance.budget.model.OrderServiceItems;
import com.finance.budget.model.repository.OrderServiceItemsRepository;
import com.finance.budget.resource.OrderServiceControllerTest;
import com.finance.budget.service.implementation.OrderServiceItemsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")

public class OrderServiceItemsServiceTest {

    @MockBean
    OrderServiceItemsRepository repository;

    OrderServiceItemsService orderServiceItemsService ;

    @BeforeEach
    void setUp(){
        this.orderServiceItemsService=new OrderServiceItemsServiceImpl(repository);

    }
@Test
@DisplayName("should delete a item")
    public void deleteItemTest(){
    OrderService orderService= OrderServiceControllerTest.newOrderServiceInstance();
    OrderServiceItems  item= OrderServiceItems
            .builder()
            .id(1L)
            .value(10)
            .description("to do sometihng")
            .quantity(1L)
            .scopeTitle("false")
            .orderService(orderService)
            .build();
    Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(item));




}

}
