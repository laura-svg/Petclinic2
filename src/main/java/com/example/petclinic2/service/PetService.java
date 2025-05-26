package com.example.petclinic2.service;

import com.example.petclinic2.model.Pet;
import com.example.petclinic2.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private static final Logger logger = LoggerFactory.getLogger(PetService.class);
       private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet findById(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        return optionalPet.orElseThrow(() -> new RuntimeException("Pet not found" + id));
    }

    public Pet save(Pet pet) {
        logger.info("Збережено тварину: {}", pet.getName());
        return petRepository.save(pet);
    }
}
