package com.finance.budget.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Users implements UserDetails , Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name" ,unique = true)
    @NotEmpty
    private String username;
     @Column(name = "full_name")
    private String fullname;
    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @NotEmpty
    private String password;

    private String email;
    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="users_permission",joinColumns = {@JoinColumn(name="id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_permission")})
    private List<Permission> permissions;


    public List<String> getRoles(){
        List<String> roles=new ArrayList<>();

        for (Permission permission:this.permissions) {
            roles.add(permission.getDescription());
        }
        return roles;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
}
