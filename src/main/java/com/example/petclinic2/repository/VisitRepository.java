package com.example.petclinic2.repository;

import com.example.petclinic2.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface VisitRepository  extends JpaRepository<Visit, Long> {
List<Visit> findByPet_Id(Long petId);
}
