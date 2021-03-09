package com.finance.budget.model.repository;

import java.util.Optional;


import com.finance.budget.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String user_name);
}
