package com.finance.budget.resource.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderServiceItemsDTO {

    private Long id;

    private String description;

    private Long quantity;

    private boolean scopeTitle;

    private double value;

    private int numItem;

    private Long ORDERSERVICE_ID;


}
