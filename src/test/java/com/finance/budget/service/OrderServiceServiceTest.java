package com.finance.budget.service;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import com.finance.budget.model.Company;
import com.finance.budget.model.OrderService;
import com.finance.budget.model.repository.OrderServiceRepository;
import com.finance.budget.service.implementation.OrderServiceServiceImpl;
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

public class OrderServiceServiceTest {

    private OrderServiceService orderServiceService;

    @MockBean
    OrderServiceRepository orderServiceRepository;

    @BeforeEach
    void setUp(){
        this.orderServiceService=new OrderServiceServiceImpl(orderServiceRepository);
    }
    @Test
    @DisplayName("should create a order service")
    public void shouldCreateAValidOrderService(){
         OrderService orderService= newOrderServiceInstance();

        Mockito.when(orderServiceRepository.save(Mockito.any(OrderService.class))).thenReturn(orderService);

        OrderService saved= orderServiceService.create(orderService);

        assertThat(saved).isNotNull();

    }

    private OrderService newOrderServiceInstance(){

        return OrderService
                .builder()
                .title("First budget")
                .annotation("Things about the budget")
                .description("Observation about material price and something like that")
                .registrationDate(LocalDate.now())
                .company(newInstanceCompany())
                .status("Em concorrencia")
                .build();
    }

    private Company newInstanceCompany(){

        return Company.builder()
                .id(1L)
                .cellphone("984006537")
                .account("Banco of brazil")
                .contactName("Marcos")
                .country("Brazil")
                .stateRegistration("123454337")
                .localization("park avenue")
                .name("Mauricio")
                .neighborhood("Casa Forte")
                .StateAbbrev("PE")
                .telephone("558175286586")
                .telephone2("558175286586")
                .registrationDate("100384")
                .build();
    }

}
