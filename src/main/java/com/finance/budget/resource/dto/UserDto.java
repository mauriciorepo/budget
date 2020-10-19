package com.finance.budget.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class UserDto {

    private Long id;

    private String login;

    private String password;

    private String email;

    private String role;

}