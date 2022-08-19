package com.pet.registry.services;

import com.pet.registry.dtos.AnimalDto;
import com.pet.registry.models.Animal;

import java.util.Collection;

public interface AnimalService {
    Animal save(AnimalDto animal);

    int delete(int id);

    Animal update(AnimalDto animal, int id);

    Animal findById(int id);

    Collection<Animal> findAll();

}
