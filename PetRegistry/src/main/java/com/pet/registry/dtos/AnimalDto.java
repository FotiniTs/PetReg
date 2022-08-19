package com.pet.registry.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pet.registry.models.enums.AnimalType;
import com.pet.registry.models.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
public class AnimalDto {

    AnimalType type;

    Gender gender;

    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    Date birth_date;

    @NotNull
    Integer microchip_code;

    boolean missing;

    boolean validated;


}
