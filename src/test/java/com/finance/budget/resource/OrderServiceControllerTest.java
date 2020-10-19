package com.finance.budget.resource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.budget.model.Company;
import com.finance.budget.model.OrderService;
import com.finance.budget.resource.dto.OrderServiceDTO;
import com.finance.budget.service.CompanyService;
import com.finance.budget.service.OrderServiceService;
import com.finance.budget.service.implementation.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = OrderServiceController.class)
@AutoConfigureMockMvc

public class OrderServiceControllerTest {

    public static final String ORDER_SERVICE_API="/api/orders-service";

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private OrderServiceService service;


    @Test
    @ApiOperation("Create Order Service")
    @DisplayName("should create a order service")
    public void create() throws Exception {
        Company company=newInstanceCompany();
        OrderServiceDTO orderServiceDTO=newOrderServiceDTOInstance();
        OrderService orderService=newOrderServiceInstance();

        orderService.setCompany(company);



        String json=new ObjectMapper().writeValueAsString(orderServiceDTO);

        BDDMockito.given(companyService.getById(Mockito.anyLong())).willReturn(Optional.of(company));
        BDDMockito.given(service.create(Mockito.any(OrderService.class))).willReturn(orderService);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ORDER_SERVICE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("title").value("First budget"))
                //.andExpect(jsonPath("date").value("10032020"))
                .andExpect(jsonPath("annotation").value("Things about the budget"))
                .andExpect(jsonPath("status").value("Em concorrencia"))
        ;
    }

    @Test
    @DisplayName("should return a company not exist")
    public void createInvalidOrderServiceTest() throws Exception {

        OrderServiceDTO orderServiceDTO=newOrderServiceDTOInstance();

        String json=new ObjectMapper().writeValueAsString(orderServiceDTO);

        BDDMockito.given(companyService.getById(Mockito.anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ORDER_SERVICE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isNotFound())
        ;

    }

    @Test
    @DisplayName("should return a bad request order service")
    public void createNullOrderService() throws Exception {
        OrderServiceDTO orderServiceDTO=new OrderServiceDTO();

        String json=new ObjectMapper().writeValueAsString(orderServiceDTO);

        BDDMockito.given(companyService.getById(Mockito.anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ORDER_SERVICE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(4)))
        ;

    }

    @Test
    @DisplayName("should return order service by id company")
    public void findOrderServiceByIdCompanyTest() throws Exception {
        OrderService orderService=newOrderServiceInstance();

        orderService.setCompany(newInstanceCompany());
        Long id_company= 1L;
        String name="Company";



        BDDMockito.given(service.findOrderServiceByIdCompany(Mockito.any(Company.class),Mockito.any(Pageable.class)))
                .willReturn(new PageImpl<OrderService>(Arrays.asList(orderService), PageRequest.of(0,10),1));

        String queryString= String.format("?id=%s&name=%s&page=0&size=100", id_company,name  );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ORDER_SERVICE_API.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(1)))
                .andExpect(jsonPath("totalElements").value(1))
                .andExpect(jsonPath("pageable.pageSize").value(100))
                .andExpect(jsonPath("pageable.pageNumber").value(0))
        ;


    }


    private OrderService newOrderServiceInstance(){

        return OrderService
                .builder()
                .title("First budget")
                .annotation("Things about the budget")
                .description("Observation about material price and something like that")
                .registrationDate(LocalDate.now())
                .status("Em concorrencia")
                .build();
    }

    private OrderServiceDTO newOrderServiceDTOInstance(){

        return OrderServiceDTO
                .builder()
                .title("First budget")
                .annotation("Things about the budget")
                .description("Observation about material price and something like that")
                .registrationDate("10032020")
                .status("Em concorrencia")
                .id_company(1L)
                .build();
    }
    private Company newInstanceCompany(){

        return Company.builder()
                 .id(1l)
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