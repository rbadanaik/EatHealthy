package com.samaira.dietplan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samaira.dietplan.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
