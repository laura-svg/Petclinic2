package com.example.petclinic2.repository;

import com.example.petclinic2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
Optional<Role> findByRole(String role);
}
