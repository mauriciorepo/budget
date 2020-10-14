package com.finance.budget.resource;

import javax.validation.Valid;

import com.finance.budget.model.User;
import com.finance.budget.resource.dto.UserDto;

import com.finance.budget.service.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper modelMapper;
    private final UserServiceImpl service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserDto userDto){
        User user= modelMapper.map(userDto, User.class);

        user=service.create(user);
        return modelMapper.map(user,UserDto.class);
    }
}
