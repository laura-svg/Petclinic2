package com.example.petclinic2.controller;

import com.example.petclinic2.model.Pet;
import com.example.petclinic2.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.findAll();
    }
    @PostMapping
    public Pet addPet(@RequestBody Pet pet) {
        return petService.save(pet);
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.findById(id);
    }



}
