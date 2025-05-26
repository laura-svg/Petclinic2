package com.example.petclinic2.repository;

import com.example.petclinic2.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
