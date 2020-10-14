package com.finance.budget.model.repository;

import java.util.Optional;

import com.finance.budget.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByLogin(String login);
}
