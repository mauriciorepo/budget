package com.finance.budget.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.budget.model.Company;
import com.finance.budget.resource.dto.CompanyDTO;
import com.finance.budget.service.CompanyService;
import com.finance.budget.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CompanyController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc



public class CompanyControllerTest {

    private static final String COMPANY_API="/api/companies";

    @MockBean
    private CompanyService service;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    MockMvc mvc;


    @BeforeEach
    public void setUp(){


    }


    /*private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "budget");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("budget","@123"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }*/


    @Test
    @DisplayName("should create a company")
    public void createCompanyTest() throws Exception {
        CompanyDTO dto= createNewCompanyDTO();

        Company company= createCompany();
        String json= new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(service.create(Mockito.any(Company.class))).willReturn(company);


        MockHttpServletRequestBuilder request = post(COMPANY_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
          //      .header("Authorization", "Bearer " + accessToken)
                .content(json);


        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value("1"));


    }





    @Test
    @WithMockUser(username="mauricio")

    @DisplayName("should return Business exception when companies is null")
    public void createInvalidCompanyTest() throws Exception {


        String json= new ObjectMapper().writeValueAsString(new CompanyDTO());

        MockHttpServletRequestBuilder request = post(COMPANY_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(5)))

        ;

    }

    @Test
    @DisplayName("should return company")
    @WithMockUser(username="mauricio")

    public void shouldFindCompanyByIdTest() throws Exception {
        Company company=createCompany();
        Long id=1L;

        BDDMockito.given(service.getById(id)).willReturn(Optional.of(company));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(COMPANY_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);


        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
        ;

    }

    @Test
    @DisplayName("must return not found when the book is not founded")
    public void companyNotFoundTest() throws Exception {


        Long id=1L;

        BDDMockito.given(service.getById(id)).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(COMPANY_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);


        mvc
                .perform(request)
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("should receive a no content when try to  delete a company")
    public void deleteCompanyTest() throws Exception {
        Long id=1L;
        Company company=createCompany();
        company.setId(id);

        BDDMockito.given(service.getById(id)).willReturn(Optional.of(company));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(COMPANY_API.concat("/" + id));

        mvc.perform(request)
                .andExpect(status().isNoContent());


    }

    @Test
    @DisplayName("should throw an exception when try to delete a company that doesn´t exists")
    public void deleteNotExistCompany() throws Exception {
        Long id = 1L;
        BDDMockito.given(service.getById(id)).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(COMPANY_API.concat("/" + id));

        mvc.perform(request)
                .andExpect(status().isNotFound());
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("account").value("Banco of brazil2"))
                .andExpect(jsonPath("cellphone").value("9840065372"));



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
                .andExpect(status().isNotFound());

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
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("should return list of Companies")
    public void listCompanyTest() throws Exception {
        Company company=createCompany();


        BDDMockito.given(service.listCompany(Mockito.any(Company.class),Mockito.any(Pageable.class)))
                .willReturn(new PageImpl<Company>(Arrays.asList(company) , PageRequest.of(0,10),1));

        String queryString=String.format("?name=%s&contactName=%s&page=0&size=100",company.getName(),company.getContactName());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(COMPANY_API.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(1)))
                .andExpect(jsonPath("totalElements").value(1))
                .andExpect(jsonPath("pageable.pageSize").value(100))
                .andExpect(jsonPath("pageable.pageNumber").value(0));
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
                .stateAbbrev("PE")
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
                .stateAbbrev("PE")
                .telephone("558175286586")
                .telephone2("558175286586")
                .registrationDate("")
                .build();
    }
}
