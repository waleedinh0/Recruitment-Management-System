package com.ManagementSystem.API.Repositories;

import com.ManagementSystem.API.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findByUserName(String userName);
}
