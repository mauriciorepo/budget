package com.finance.budget.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
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
