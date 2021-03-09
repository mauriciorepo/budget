package com.finance.budget.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class UserDto {

    private Long id;

    @NotNull
    private String username;

    private boolean account_non_expired;

    private boolean account_non_locked;

    private boolean credentials_non_expired;
    @NotNull
    private String password;

    private String email;

    private boolean enabled;

}