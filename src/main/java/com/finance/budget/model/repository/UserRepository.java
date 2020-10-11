package com.finance.budget.model.repository;

import com.finance.budget.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
