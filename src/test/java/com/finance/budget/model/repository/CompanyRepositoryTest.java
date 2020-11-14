package com.finance.budget.model.repository;

import com.finance.budget.model.Company;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;



import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")

@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    CompanyRepository repository;

    @Autowired
    TestEntityManager entityManager;


    @Test
    @DisplayName("Should save a company")
    public void saveCompanyTest(){
        Company company=newInstanceCompany();

        Company savedCompany=repository.save(company);

        assertThat(savedCompany).isNotNull();
        assertThat(savedCompany.getId()).isPositive();

    }

    @Test
    @DisplayName("should return a company")
    public void findCompanyTest(){

        entityManager.persist(newInstanceCompany());

        boolean foundedCompany= repository.existsByName(newInstanceCompany().getName());

        assertThat(foundedCompany).isTrue();
    }

    @Test
    @DisplayName("should return null when try to find a company")
    public void shouldReturnNullCompany(){
        Long id=1L;

        boolean notFoundedCompany=repository.existsById(id);

        assertThat(notFoundedCompany).isFalse();
    }

    @Test
    @DisplayName("should delete a company")
    public void deleteCompanyTest(){

        Company company=newInstanceCompany();

        company=entityManager.persist(company);

        Company foundedCompany=entityManager.find(Company.class, company.getId());

        repository.delete(foundedCompany);

        Company deleteCompany= entityManager.find(Company.class, foundedCompany.getId());

        assertThat(deleteCompany).isNull();

    }

    @Test
    @DisplayName("should return a updated company")
    public void updateCompanyTest(){


        Company company = entityManager.persist(newInstanceCompany());
        company.setName("Mauricio2");


        Company savedCompany=repository.save(company);


        assertThat(savedCompany.getName()).isEqualTo("Mauricio2");

    }

    private Company newInstanceCompany(){

        return Company.builder()
               // .id(1l)
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


}
