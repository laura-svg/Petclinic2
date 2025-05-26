package com.example.petclinic2.repository;

import com.example.petclinic2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String username);
    boolean existsByLogin(String username);
}
