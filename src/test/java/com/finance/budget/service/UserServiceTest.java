package com.finance.budget.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.finance.budget.model.User;
import com.finance.budget.model.repository.UserRepository;

import com.finance.budget.service.implementation.UserServiceImpl;
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
public class UserServiceTest {

    @MockBean
    private UserRepository repository;



    private UserServiceImpl service;



    @BeforeEach
    void setUp(){
        this.service=new UserServiceImpl(repository);
    }

    @Test
    @DisplayName("should return a user")
    public void createUser(){
        User user= createNewUser();
          Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);

        user=service.create(user);

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("fakeemail@gmail.com");

    }


    private User createNewUser(){
        return User
                .builder()
                .id(1L)
                .login("mauricio")
                .password("123")
                .email("fakeemail@gmail.com")
                .build();
    }



}
