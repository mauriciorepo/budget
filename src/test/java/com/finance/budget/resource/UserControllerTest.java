package com.finance.budget.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.budget.model.User;
import com.finance.budget.resource.dto.UserDto;
import com.finance.budget.service.UserService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private  UserService service;

    private static final  String USER_API="/api/users";

    @Test
    @ApiOperation("Create a new user")
    public void createUserTest() throws Exception {
      User user= createNewUser();
      UserDto dto= createNewUserDto();

      String json= new ObjectMapper().writeValueAsString(dto);
        BDDMockito.given(service.create(Mockito.any(User.class))).willReturn(user);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated());

    }

    /*@Test
    @DisplayName("should return a null user")
    public void createInvalidUser(){

    }*/

    private User createNewUser(){
        return User
                .builder()
                .id(1L)
                .login("mauricio")
                .password("123")
                .email("fakeemail@gmail.com")
                .build();
    }

    private UserDto createNewUserDto(){
        return UserDto
                .builder()
                .login("mauricio")
                .password("123")
                .email("fakeemail@gmail.com")
                .build();
    }


}
