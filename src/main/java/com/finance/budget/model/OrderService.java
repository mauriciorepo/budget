package com.finance.budget.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;


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

    @NotNull
    private String status;

    private String orderNumber;

    private String description;

    @NotNull
    private String title;

    private String annotation;
    @NotNull
    private LocalDate registrationDate;

    @ManyToOne
    @JoinColumn(name = "id_company")
    private Company company;


    private LocalDate modified;

    @PostPersist
    void noReturn(){

        Long size=4-this.company.getId();
        String number=this.company.getId()+"";


        this.setOrderNumber(String.format(number, "0"+size+"0d"));
                //.concat(this.getId()+"").concat(LocalDate.now().getYear()+""));
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
}
