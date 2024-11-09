package com.example.loginback.repository;

import com.example.loginback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPidAndProvider(String pid, String provider);
    Optional<User> findBySub(String sub);
}
