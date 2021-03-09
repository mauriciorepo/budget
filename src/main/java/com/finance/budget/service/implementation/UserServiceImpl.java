package com.finance.budget.service.implementation;



import com.finance.budget.model.Users;
import com.finance.budget.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements  UserDetailsService  {


    @Autowired
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository=repository;
    }


    public Users create(Users user) {

        return repository.save(user);
    }



    @Override
    public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {

        Users user=repository
                .findByUsername(user_name)
                .orElseThrow( ()-> new UsernameNotFoundException("Login not founded"));

        return  org.springframework.security.core.userdetails.User
                 .builder()
                 .username(user.getUsername())
                 .password(user.getPassword())
                 .roles("USER")
                 .build()
                 ;
    }
}
