package com.finance.budget.service;

import com.finance.budget.model.Company;
import com.finance.budget.model.repository.CompanyRepository;

import com.finance.budget.service.implementation.ImplCompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CompanyServiceTest {


    private CompanyService service;

    @MockBean
    private CompanyRepository repository;

    @BeforeEach
    void setUp(){
        this.service=new ImplCompanyService(repository);
    }

    @Test
    @DisplayName("should create a company")
    public void createCompany(){
        Company company=Company.builder()
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
        Mockito.when(repository.save(Mockito.any(Company.class))).thenReturn(company);

        Company savedCompany=service.create(company);

        assertThat(savedCompany.getId()).isNotNull();
        assertThat(savedCompany.getName()).isEqualTo("Mauricio");
        assertThat(savedCompany.getAccount()).isEqualTo("Banco of brazil");
        assertThat(savedCompany.getCellphone()).isEqualTo("984006537");

    }


    @Test
    @DisplayName("should return a company")
    public void findCompanyTest(){
        Company company=newInstanceCompany();
        Long id=1L;

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(company));

        Optional<Company> foundCompany= service.getById(id);

        assertThat(foundCompany.get().getId()).isNotNull();
        assertThat(foundCompany.get().getId()).isPositive();

    }

    @Test
    @DisplayName("should return a null company")
    public void companyNotFoundedTest(){

        Long id=1L;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Company> notFoundCompany= service.getById(id);

        assertThat(notFoundCompany.isPresent()).isFalse();


    }

    @Test
    @DisplayName("should return a delete company")
    public void deleteCompanyTest(){
        Company company=newInstanceCompany();

        Long id=1L;

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(company));

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(()-> service.delete(company));

        Mockito.verify(repository,Mockito.times(1)).delete(company);

    }

    @Test
    @DisplayName("must return an not found exception when try to delete a company that not exist")
    public void deleteNotExistCompanyTest(){
        Company company=new Company();
        Long id=1L;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows( IllegalArgumentException.class, ()->service.delete(company));

        Mockito.verify(repository, Mockito.never()).delete(company);
    }


    @Test
    @DisplayName("should return a updated company")
    public void updateCompanyTest(){

        Long id =1L;

        Company company=newInstanceCompany();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(company));

        Mockito.when(repository.save(Mockito.any(Company.class))).thenReturn(company);

        Company savedCompany= service.updateCompany(company);

        assertThat(savedCompany).isNotNull();
        assertThat(savedCompany.getId()).isNotNull();

    }


    @Test
    @DisplayName("should return a list of company")
    public void listCompanyTest(){
        Company company=newInstanceCompany();

        PageRequest pageRequest=PageRequest.of(0,10);
        Page<Company> page= new PageImpl<>(java.util.Arrays.asList(company),pageRequest,1);

        Mockito.when(repository.findAll(Mockito.any(Example.class),Mockito.any(PageRequest.class))).thenReturn(page);

        Page<Company> pageResult= service.listCompany(company,pageRequest);

        assertThat(pageResult.getTotalElements()).isEqualTo(1);
        assertThat(pageResult.getTotalPages()).isEqualTo(1);
        assertThat(pageResult.getPageable().getPageSize()).isEqualTo(10);
        assertThat(pageResult.getPageable().isPaged()).isTrue();


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
