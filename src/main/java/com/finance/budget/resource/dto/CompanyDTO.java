package com.finance.budget.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CompanyDTO {

        private  Long id;


        @NotEmpty
        private String name;

        private String telephone;

        @NotEmpty
        private String localization;

        @NotEmpty
        private String contactName;

        private String telephone2;

        @NotEmpty
        private String cellphone;

        @NotEmpty
        private String country;

        private String stateRegistration;

        private String neighborhood;

        @NotEmpty//uf
        private String StateAbbrev;

        private String account;

        //@NotEmpty
        private String registrationDate;

        private boolean situation;


}
