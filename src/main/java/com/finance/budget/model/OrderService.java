package com.finance.budget.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



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
    @OneToMany(mappedBy = "orderService",cascade = {CascadeType.ALL},orphanRemoval = true)
    @JsonManagedReference
    @OrderBy("numItem ASC")
    public List<OrderServiceItems> list=new ArrayList<>();

    @NotNull
    private String status;

    private String orderNumber;

    private String description;

    @NotNull
    private String title;

    private String annotation;
    @NotNull
    private LocalDate registrationDate;

    private boolean indorsement;



    private LocalDate modified;

    @PostPersist
    void noReturn(){

        String number=this.company.getId()+"";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YY");

        this.setOrderNumber(String.format("%0"+(4-this.company.getId().toString().length())+"d%s",0,number )
                .concat(this.getId()+"")
                .concat(LocalDate.now().format(formatter)+""));

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


    public void updateItems(List<OrderServiceItems> items){
        if(this.list == null){

        }else{
            this.list.retainAll(items);
            this.list.addAll(items);
        }
    }

}
