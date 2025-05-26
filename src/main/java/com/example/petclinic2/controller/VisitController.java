package com.example.petclinic2.controller;

import com.example.petclinic2.model.Visit;
import com.example.petclinic2.service.PetService;
import com.example.petclinic2.service.VisitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/visits")
public class VisitController {
    private final VisitService visitService;
    private final  PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @PostMapping
    public Visit addVisit(@RequestBody Visit visit) {
        Long petId = visit.getPet().getId(); // Отримуємо id
        visit.setPet(petService.findById(petId)); // Встановлюємо у Visit
        return visitService.save(visit);
    }


    @GetMapping("/by-pet/{petId}")
    public List<Visit> getVisitsByPet(@PathVariable Long petId) {
    return visitService.getVisitsByPetId(petId);
    }

    @GetMapping
    public List<Visit> getAllVisits() {
    return visitService.findAll();
    }
}
