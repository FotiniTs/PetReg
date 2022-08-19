package com.pet.registry.repositories;

import com.pet.registry.models.Animal;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AnimalRepository extends CrudRepository<Animal, Integer> {

    Collection<Animal> findAllByCitizenId(int id);

    Collection<Animal> findAllByCitizenState(String state);

    Collection<Animal> findAllByCitizenCity(String city);

}
