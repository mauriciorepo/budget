package com.finance.budget.resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.budget.model.Company;
import com.finance.budget.model.OrderService;
import com.finance.budget.model.OrderServiceItems;
import com.finance.budget.resource.dto.OrderServiceDTO;
import com.finance.budget.resource.dto.OrderServiceItemsDTO;
import com.finance.budget.service.CompanyService;
import com.finance.budget.service.OrderServiceService;
import com.finance.budget.service.implementation.UserServiceImpl;
import io.swagger.annotations.ApiOperation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @MockBean
    private OrderService orderServiceMock;



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
                .andExpect(jsonPath("errors", hasSize(2)))
        ;

    }

    @Test
    @DisplayName("should return a list of OrderService by id company")
    public void shouldReturnAListOfOrderService() throws Exception {
        Long id=1L;
        OrderService order=newOrderServiceInstance();
        BDDMockito.given(service.findByIdCompany(Mockito.anyLong())).willReturn(Arrays.asList(order));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ORDER_SERVICE_API.concat("/"+id+"/company"))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk());

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

    @Test
    @DisplayName("should return status created when OrderService is updated")
    public void updateOrderService() throws Exception {
        Long id=1L;
        Company company=newInstanceCompany();
        OrderServiceDTO orderServiceDTO=newOrderServiceDTOInstance();
        OrderService orderService=newOrderServiceInstance();
        orderServiceDTO.setId(id);
        orderService.setId(id);
        orderService.setCompany(company);

        String json=new ObjectMapper().writeValueAsString(orderServiceDTO);

        BDDMockito.given(companyService.getById(Mockito.anyLong())).willReturn(Optional.of(company));
        BDDMockito.given(service.getById(id)).willReturn(Optional.of(orderService));

        BDDMockito.given(service.update(Mockito.any(OrderService.class))).willReturn(orderService);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(ORDER_SERVICE_API.concat("/"+id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value("First budget"))
                //.andExpect(jsonPath("date").value("10032020"))
                .andExpect(jsonPath("annotation").value("Things about the budget"))
                .andExpect(jsonPath("status").value("Em concorrencia"))
        ;
    }
    @Test
    @DisplayName("should return status conflict when try to update OrderService")
    public void updateOrderServiceWithConflictTest() throws Exception {
        Long id=1L;
        Company company=newInstanceCompany();
        OrderServiceDTO orderServiceDTO=newOrderServiceDTOInstance();
        OrderService orderService=newOrderServiceInstance();
        //orderServiceDTO.setId(id);
        orderService.setId(id);
        orderService.setCompany(company);

        String json=new ObjectMapper().writeValueAsString(orderServiceDTO);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(ORDER_SERVICE_API.concat("/"+id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isConflict())
          ;
    }

    @Test
    @DisplayName("should return status created when OrderService is updated")
    public void updateNotFoundedOrderServiceTest() throws Exception {
        Long id=1L;
        Company company=newInstanceCompany();
        OrderServiceDTO orderServiceDTO=newOrderServiceDTOInstance();
        OrderService orderService=newOrderServiceInstance();
        orderServiceDTO.setId(id);
        orderService.setId(id);
        orderService.setCompany(company);

        String json=new ObjectMapper().writeValueAsString(orderServiceDTO);

        BDDMockito.given(companyService.getById(Mockito.anyLong())).willReturn(Optional.of(company));
        BDDMockito.given(service.getById(id)).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(ORDER_SERVICE_API.concat("/"+id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isNotFound())
        ;
    }

      @Test

    @DisplayName("should return OrderService updatable")
    public void shouldReturnAPageableOfOrderService(){

        Long id=1L;

        OrderService orderService=newOrderServiceInstance();
        orderService.setId(id);
        BDDMockito.given(service.getById(Mockito.anyLong())).willReturn(Optional.of(orderService));

        //String json=new  ObjectMapper().writeValueAsString()
        MockMvcRequestBuilders.put(ORDER_SERVICE_API.concat("/"+id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
    //            .content()
        ;

    }

    @Test
    @DisplayName("should return a Order Service when try to getOrderServiceById")
    public void returnOrderServiceWhenGetOrderServiceById() throws Exception {
        long id=1L;
        OrderService order= newOrderServiceInstance();

        BDDMockito.given(service.getById(Mockito.anyLong())).willReturn(Optional.of(order));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ORDER_SERVICE_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("should return a list of order Services ")
    public void returnListOfOrderService(){
        List<OrderService> list= Arrays.asList(newOrderServiceInstance());

        //BDDMockito.given(service.listOrderService()).willReturn(list)
    }



    public static OrderService newOrderServiceInstance(){

        return OrderService
                .builder()
                .title("First budget")
                .annotation("Things about the budget")
                .description("Observation about material price and something like that")
                .registrationDate(LocalDate.now())
                .status("Em concorrencia")
                .orderNumber("0001120")
                .list(new ArrayList<OrderServiceItems>(Arrays.asList(createNewOrderServiceItems())) )
                .build();
    }

    public static OrderServiceDTO newOrderServiceDTOInstance(){

        return OrderServiceDTO
                .builder()
                .title("First budget")
                .annotation("Things about the budget")
                .description("Observation about material price and something like that")
                .registrationDate("10032020")
                .status("Em concorrencia")
                .id_company(1L)
                .list(new ArrayList<OrderServiceItemsDTO>(Arrays.asList(createNewOrderServiceItemsDTO())))
               // .list(new ArrayList<>().add(createNewOrderServiceItemsDTO())/*Arrays.asList(createNewOrderServiceItemsDTO())*/)
                .build();
    }
        public static Company newInstanceCompany(){

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
                .stateAbbrev("PE")
                .telephone("558175286586")
                .telephone2("558175286586")
                .registrationDate("100384")
                .build();
    }


    static OrderServiceItemsDTO createNewOrderServiceItemsDTO(){

        return OrderServiceItemsDTO.builder()
                .description("do something")
                .quantity(1L)
                .scopeTitle("1")
                .value(34.67)
                .numItem(1)
                .build();
    }
    public static OrderServiceItems createNewOrderServiceItems(){

        return OrderServiceItems.builder()
                .description("do something")
                .quantity(1L)
                .scopeTitle("1")
                .numItem(1)
                .value(25.46)
                .build();

    }
}
