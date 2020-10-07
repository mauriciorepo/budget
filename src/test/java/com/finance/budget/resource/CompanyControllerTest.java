package com.finance.budget.resource;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.budget.model.Company;

import com.finance.budget.model.dto.CompanyDTO;
import com.finance.budget.service.CompanyService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@WebMvcTest
@ActiveProfiles("test")
@AutoConfigureMockMvc


public class CompanyControllerTest {

    private static final String COMPANY_API="/api/companies";

    @MockBean
    private CompanyService service;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("should create a company")
    public void createCompanyTest() throws Exception {
        CompanyDTO dto= createNewCompanyDTO();

        Company company= createCompany();
        String json= new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(service.create(Mockito.any(Company.class))).willReturn(company);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(COMPANY_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);


        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value("1"));


    }





    @Test
    @DisplayName("should return Business exception when companies is null")
    public void createInvalidCompanyTest() throws Exception {


        String json= new ObjectMapper().writeValueAsString(new CompanyDTO());
        //BDDMockito.given(service.create(Mockito.any(Company.class))).willThrow(NullPointerException.class);

        //BDDMockito.given(service.create(Mockito.any(Company.class))).willReturn(new Company());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(COMPANY_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(7)))

        ;

    }

    @Test
    @DisplayName("should return company")
    public void shouldFindCompanyByIdTest() throws Exception {
        Company company=createCompany();
        Long id=1L;

        BDDMockito.given(service.getById(id)).willReturn(Optional.of(company));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(COMPANY_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);
                ;

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value("1"))
        ;

    }

    @Test
    @DisplayName("must return not found when the book is not founded")
    public void companyNotFoundTest() throws Exception {

        Company company=createCompany();
        Long id=1L;

        BDDMockito.given(service.getById(id)).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(COMPANY_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);
        ;

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @DisplayName("should receive a no content when try to  delete a company")
    public void deleteCompanyTest() throws Exception {
        Long id=1L;
        Company company=createCompany();
        company.setId(id);

        BDDMockito.given(service.getById(id)).willReturn(Optional.of(company));
        //BDDMockito.given(service.delete(id)).willReturn()

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(COMPANY_API.concat("/" + id));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());


    }

    @Test
    @DisplayName("should throw an exception when try to delete a company that doesn´t exists")
    public void deleteNotExistCompany() throws Exception {
        Long id = 1L;
        BDDMockito.given(service.getById(id)).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(COMPANY_API.concat("/" + id));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    @DisplayName("should return a updated company")
    public void updateCompanyTest() throws Exception {
        Long id=1L;
        Company company=createCompany();
        company.setAccount("Banco of brazil2");
        company.setCellphone("9840065372");
        CompanyDTO dto=createNewCompanyDTO();
        dto.setId(id);
        dto.setAccount("Banco of brazil2");
                dto.setCellphone("9840065372");

        String json= new ObjectMapper().writeValueAsString(dto);
        BDDMockito.given(service.updateCompany(Mockito.any(Company.class))).willReturn(company);

        BDDMockito.given(service.getById(id)).willReturn(Optional.of(company));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(COMPANY_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                ;

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("account").value("Banco of brazil2"))
                .andExpect(MockMvcResultMatchers.jsonPath("cellphone").value("9840065372"));



    }


    @Test
    @DisplayName("should return a not founded status when try to update a company")
    public void updateNotFoundedCompanyTest() throws Exception {
        Long id =1L;

        CompanyDTO dto= createNewCompanyDTO();
         dto.setId(id);
        String json= new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(service.getById(id)).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(COMPANY_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                ;

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @DisplayName("should return a conflict exception when try to update company with different id")
    public void updateCompanyWithConflictIdTest() throws Exception {
        Long id =10L;

        CompanyDTO dto= createNewCompanyDTO();

        String json= new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(service.getById(id)).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(COMPANY_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                ;

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
    private CompanyDTO createNewCompanyDTO(){
        return CompanyDTO.builder()
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

    private Company createCompany(){

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
                .registrationDate("")
                .build();
    }
}
