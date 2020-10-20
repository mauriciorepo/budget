package com.finance.budget.resource.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class OrderServiceDTO {


    private Long id;

    @NotEmpty
    private String status;

    @NotNull
    private Long id_company;

    private String orderNumber;

    private String description;

    @NotEmpty
    private String title;

    private String annotation;

    private  String registrationDate;
}
