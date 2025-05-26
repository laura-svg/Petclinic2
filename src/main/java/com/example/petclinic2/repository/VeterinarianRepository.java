package com.example.petclinic2.repository;

import com.example.petclinic2.model.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VeterinarianRepository extends JpaRepository <Veterinarian, Long> {
}
