package com.pet.registry.dtos;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class MedicalCaseDto {

    @NotEmpty
    @NotNull
    String title;

    @NotEmpty
    @NotNull
    String description;

    int animal_id;

}
