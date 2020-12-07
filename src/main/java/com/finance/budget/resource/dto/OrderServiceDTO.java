package com.finance.budget.resource.dto;


import javax.persistence.OrderBy;
import javax.validation.constraints.NotEmpty;


import java.util.ArrayList;
import java.util.List;


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

    //@NotNull
    private Long id_company;

    private String orderNumber;

    private String description;
    @OrderBy("numItem ASC")
     private List<OrderServiceItemsDTO> list= new ArrayList();

    @NotEmpty
    private String title;


    private String annotation;

    private  String registrationDate;

    private boolean aval;
}
