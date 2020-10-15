package com.finance.budget.service.implementation;


import com.finance.budget.model.User;
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


    public User create(User user) {

        return repository.save(user);
    }



    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user=repository
                .findByLogin(login)
                .orElseThrow( ()-> new UsernameNotFoundException("Login not founded"));

        return  org.springframework.security.core.userdetails.User
                 .builder()
                 .username(user.getLogin())
                 .password(user.getPassword())
                 .roles("USER")
                 .build()
                 ;
    }
}
