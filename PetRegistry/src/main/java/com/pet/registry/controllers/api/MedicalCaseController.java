package com.pet.registry.controllers.api;

import com.pet.registry.dtos.MedicalCaseDto;
import com.pet.registry.models.MedicalCase;
import com.pet.registry.services.MedicalCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/medical_cases")
public class MedicalCaseController {
    @Autowired
    MedicalCaseService medicalCaseService;

    @PreAuthorize("hasRole('ROLE_VET') or hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseBody
    public MedicalCase createMedicalCase(@RequestBody @Valid MedicalCaseDto medicalCase) {
        return medicalCaseService.save(medicalCase);
    }

    @PreAuthorize("hasRole('ROLE_VET') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseBody
    public MedicalCase updateMedicalCase(@RequestBody @Valid MedicalCaseDto medicalCase, @PathVariable("id") int id) {
        return medicalCaseService.update(medicalCase, id);
    }

    @PreAuthorize("hasRole('ROLE_VET') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @ResponseBody
    public MedicalCase getMedicalCase(@PathVariable("id") int id) {
        return medicalCaseService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_VET') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public int deleteMedicalCase(@PathVariable("id") int id) {
        return medicalCaseService.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_VET')")
    @GetMapping
    @ResponseBody
    public Collection<MedicalCase> getMedicalCases() {
        return medicalCaseService.findAll();
    }
}

