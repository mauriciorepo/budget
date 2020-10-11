package com.finance.budget.service.implementation;

import com.finance.budget.model.User;
import com.finance.budget.model.repository.UserRepository;
import com.finance.budget.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository=repository;
    }

    @Override
    public User create(User user) {

        return repository.save(user);
    }


}
