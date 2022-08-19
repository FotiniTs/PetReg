package com.pet.registry.services.Impl;

import com.pet.registry.dtos.MedicalCaseDto;
import com.pet.registry.exception.EntityNotFoundException;
import com.pet.registry.models.Animal;
import com.pet.registry.models.MedicalCase;
import com.pet.registry.repositories.MedicalCaseRepository;
import com.pet.registry.repositories.AnimalRepository;
import com.pet.registry.services.MedicalCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class MedicalCaseServiceImpl implements MedicalCaseService {

    @Autowired
    MedicalCaseRepository medicalCaseRepository;
    @Autowired
    AnimalRepository animalRepository;

    @Override
    public MedicalCase save(MedicalCaseDto medical_case) {
        MedicalCase new_medical_case = new MedicalCase();
        new_medical_case.setTitle(medical_case.getTitle());
        new_medical_case.setDescription(medical_case.getDescription());
        new_medical_case.setAnimal(animalRepository.findById(medical_case.getAnimal_id()).orElseThrow(() -> new EntityNotFoundException(Animal.class, "id", String.valueOf(medical_case.getAnimal_id()))));
        return medicalCaseRepository.save(new_medical_case);
    }

    @Override
    public int delete(int id) {
        MedicalCase medical_case = medicalCaseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MedicalCase.class, "id", String.valueOf(id)));
        medicalCaseRepository.delete(medical_case);
        return id;
    }

    @Override
    public MedicalCase update(MedicalCaseDto medical_case, int id) {
        MedicalCase edit_medical_case = medicalCaseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MedicalCase.class, "id", String.valueOf(id)));
        edit_medical_case.setTitle(medical_case.getTitle());
        edit_medical_case.setDescription(medical_case.getDescription());
        return medicalCaseRepository.save(edit_medical_case);
    }

    @Override
    public MedicalCase findById(int id) {
        return medicalCaseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MedicalCase.class, "id", String.valueOf(id)));
    }

    @Override
    public Collection<MedicalCase> findAll() {
        Iterable<MedicalCase> itr = medicalCaseRepository.findAll();
        return (Collection<MedicalCase>) itr;
    }
}

