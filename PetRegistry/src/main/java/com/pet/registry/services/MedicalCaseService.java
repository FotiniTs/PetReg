package com.pet.registry.services;

import com.pet.registry.dtos.MedicalCaseDto;
import com.pet.registry.models.MedicalCase;

import java.util.Collection;

public interface MedicalCaseService {

    MedicalCase save(MedicalCaseDto medical_case);

    int delete(int id);

    MedicalCase update(MedicalCaseDto medical_case, int id);

    MedicalCase findById(int id);

    Collection<MedicalCase> findAll();


}
