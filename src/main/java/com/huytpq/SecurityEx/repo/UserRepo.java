package com.huytpq.SecurityEx.repo;

import com.huytpq.SecurityEx.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

}
