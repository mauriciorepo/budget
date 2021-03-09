package com.finance.budget.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permission implements GrantedAuthority, Serializable {

    private static  final  long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  long id;

   private String description;

    @Override
    public String getAuthority() {
        return this.description;
    }
}
