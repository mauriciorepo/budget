package com.finance.budget.service;

import java.time.LocalDate;

import static com.finance.budget.resource.OrderServiceControllerTest.createNewOrderServiceItems;
import static org.assertj.core.api.Assertions.assertThat;



import com.finance.budget.model.Company;
import com.finance.budget.model.OrderService;
import com.finance.budget.model.OrderServiceItems;
import com.finance.budget.model.repository.OrderServiceRepository;

import com.finance.budget.resource.OrderServiceControllerTest;
import com.finance.budget.service.implementation.OrderServiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        assertThat(saved.getTitle()).isNotNull();
        assertThat(saved.getCompany()).isNotNull();
        assertThat(saved.getCompany().getId()).isNotNull();
        assertThat(saved.getOrderNumber()).isNotNull();
        assertThat(saved.getOrderNumber()).isEqualTo("0001120");

    }

    @Test
    @DisplayName("should return a list of OrderService by id company")
    public void returnOrderServiceByIdCompany(){
        Long id=1L;
        List<OrderService> order= Arrays.asList(OrderServiceControllerTest.newOrderServiceInstance());

        Mockito.when(orderServiceRepository.
                findOrderServiceByIdCompany(Mockito.anyLong()))
                .thenReturn(order);

        List<OrderService> list=orderServiceService.findByIdCompany(id);

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getTitle()).isEqualTo("First budget");

        assertThat(list.get(0).getList().size()).isEqualTo(1);

    }

    @Test
    @DisplayName("should return OrderService updatable")
    public void returnUpdateOrderService(){
        Long id=1L;
        OrderService foundOrder= newOrderServiceInstance();
        foundOrder.setId(id);
        foundOrder.setList(new ArrayList<OrderServiceItems>(Arrays.asList(createNewOrderServiceItems())));

        Mockito.when(orderServiceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(foundOrder));
        Mockito.when(orderServiceRepository.save(Mockito.any(OrderService.class))).thenReturn(foundOrder);
        OrderService order=orderServiceService.getById(id).get();
        OrderService savedOrderService=orderServiceService.update(order);

        assertThat(savedOrderService).isNotNull();
        assertThat(savedOrderService.getTitle()).isEqualTo("First budget");
        assertThat(savedOrderService.getList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("should return an exception when try to update OrderService with id or object null")
    public void shouldReturnExceptionUpdateOrderService(){
        OrderService orderService =new OrderService();
        Mockito.when(orderServiceRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, ()-> orderServiceService.update(orderService));

        Mockito.verify(orderServiceRepository,Mockito.never()).save(orderService);

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
                .orderNumber("0001120")

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
