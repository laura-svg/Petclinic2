package com.example.petclinic2.controller;

import com.example.petclinic2.model.Pet;
import com.example.petclinic2.model.Veterinarian;
import com.example.petclinic2.model.Visit;
import com.example.petclinic2.service.PetService;
import com.example.petclinic2.service.VeterinarianService;
import com.example.petclinic2.service.VisitService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final PetService petService;
    private final VeterinarianService veterinarianService;
    private final VisitService visitService;

    public UserRestController(PetService petService, VeterinarianService veterinarianService, VisitService visitService) {
        this.petService = petService;
        this.veterinarianService = veterinarianService;
        this.visitService = visitService;
    }


    @GetMapping("/pets")
    public List<Pet> getAllPets() {
        return petService.findAll(); // Повертає JSON
    }

    @GetMapping("/vets")
    public List<Veterinarian> getVets() {
        return veterinarianService.findAll();
    }

    @GetMapping("/visits")
    public List<Visit> getVisits() {
        return visitService.findAll();
    }

    @GetMapping("/profile")
    public String getUserProfile(Principal principal) {
        return "Hello, " + (principal != null ? principal.getName() : "Guest");
    }
}
