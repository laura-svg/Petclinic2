package com.example.petclinic2.service;

import com.example.petclinic2.model.Veterinarian;
import com.example.petclinic2.repository.VeterinarianRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarianService {
    private static final Logger logger = LoggerFactory.getLogger(VeterinarianService.class);

    private final VeterinarianRepository veterinarianRepository;

    public VeterinarianService(VeterinarianRepository veterinarianRepository) {
        this.veterinarianRepository = veterinarianRepository;
    }

    public List<Veterinarian> findAll() {
        return veterinarianRepository.findAll();
    }

    public Veterinarian findById(Long id) {
        return veterinarianRepository.findById(id).orElseThrow(() -> new RuntimeException("Veterinarian not found" + id));
    }

    public Veterinarian save(Veterinarian veterinarian) {
logger.info("Saving veterinarian : name={}, specialization={}, veterinarianId={}",
        veterinarian.getSpecialization(), veterinarian.getName(), veterinarian.getId());
        return veterinarianRepository.save(veterinarian);
    }
}
