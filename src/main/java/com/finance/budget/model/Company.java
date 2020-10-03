package com.finance.budget.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private Long id;

    private String name;

    private String telephone;

    private String localization;

    private String contactName;

    private String telephone2;

    private String cellphone;

    private String country;

    private String stateRegistration;

    private String neighborhood;

    //uf
    private String StateAbbrev;

    private String account;

    private String registrationDate;
}
