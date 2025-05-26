package com.example.petclinic2.controller;

import com.example.petclinic2.model.Veterinarian;
import com.example.petclinic2.service.VeterinarianService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Getter
@Setter
@RestController
@RequestMapping("api/admin/vets")
public class VeterinarianController {

private VeterinarianService veterinarianService;

    public VeterinarianController(VeterinarianService veterinarianService) {
        this.veterinarianService = veterinarianService;
    }

    @GetMapping
    public List<Veterinarian> getAllVeterinarians() {
    return veterinarianService.findAll();
    }

    @GetMapping("/{id}")
    public Veterinarian getVeterinarianById(@PathVariable Long id) {
    return veterinarianService.findById(id);
    }

@PostMapping
    public Veterinarian addVeterinarian(@RequestBody Veterinarian veterinarian) {
    return veterinarianService.save(veterinarian);
}
}
