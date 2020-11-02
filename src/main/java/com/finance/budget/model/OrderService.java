package com.finance.budget.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.criterion.Order;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_company")
    private Company company;


    //@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.MERGE, CascadeType.REMOVE})
    //@JoinColumn(name="ORDERSERVICE_ID" , nullable = false)
    @OneToMany(mappedBy = "orderService",cascade = {CascadeType.ALL})
    @JsonManagedReference
    public List<OrderServiceItems> list=new ArrayList();

    @NotNull
    private String status;

    private String orderNumber;

    private String description;

    @NotNull
    private String title;

    private String annotation;
    @NotNull
    private LocalDate registrationDate;



    private LocalDate modified;

    @PostPersist
    void noReturn(){

        String number=this.company.getId()+"";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YY");

        this.setOrderNumber(String.format("%0"+(4-this.company.getId().toString().length())+"d%s",0,number )
                .concat(this.getId()+"")
                .concat(LocalDate.now().format(formatter)+""));

        OrderService order= OrderService.builder().id(this.getId()).build();
        //List<OrderServiceItems> orderServiceItemsList =this.getList().stream().map(entity-> entity.setOrderService(order)).collect(Collectors.toList());
        //this.setList(orderServiceItemsList);

    }


    @PrePersist
    void   onCreate(){
        this.setModified(LocalDate.now());
        this.setRegistrationDate(LocalDate.now());
    }

    @PreUpdate
    void onUpdate(){
        this.setModified(LocalDate.now());
    }

    public void removeItem(OrderServiceItems item){
        this.getList().remove(item);
    }

}
