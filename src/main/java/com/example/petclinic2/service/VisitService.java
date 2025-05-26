package com.example.petclinic2.service;

import com.example.petclinic2.model.Visit;
import com.example.petclinic2.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private static final Logger logger = LoggerFactory.getLogger(VisitService.class);
    private final VisitRepository visitRepository;
    
    public Visit save(Visit visit) {
        logger.info("Збереження візиту: дата={}, опис={}, petId={}",
                visit.getVisitDate(), visit.getDescription(), visit.getPet().getId());

        return visitRepository.save(visit);
    }

    public List<Visit> getVisitsByPetId(Long petId) {
        return visitRepository.findByPet_Id(petId);
    }

    public List<Visit> findAll() {
        return visitRepository.findAll();
    }
}
