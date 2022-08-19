package com.pet.registry.services.Impl;

import com.pet.registry.dtos.AnimalDto;
import com.pet.registry.exception.EntityNotFoundException;
import com.pet.registry.models.Animal;
import com.pet.registry.models.User;
import com.pet.registry.models.enums.Role;
import com.pet.registry.repositories.AnimalRepository;
import com.pet.registry.repositories.UserRepository;
import com.pet.registry.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Service
@Transactional
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnimalRepository animalRepository;

    @Override
    public Animal save(AnimalDto animal) {
        Animal new_animal = new Animal();
        new_animal.setBirth_date(animal.getBirth_date());
        new_animal.setGender(animal.getGender());
        new_animal.setMissing(animal.isMissing());
        new_animal.setType(animal.getType());
        new_animal.setMicrochip_code(animal.getMicrochip_code());
        new_animal.setCitizen(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return animalRepository.save(new_animal);
    }

    @Override
    public int delete(int id) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Animal.class, "id", String.valueOf(id)));
        animalRepository.delete(animal);
        return id;
    }

    @Override
    public Animal update(AnimalDto animal, int id) {
        Animal edit_animal = animalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Animal.class, "id", String.valueOf(id)));
        edit_animal.setBirth_date(animal.getBirth_date());
        edit_animal.setGender(animal.getGender());
        edit_animal.setMissing(animal.isMissing());
        edit_animal.setType(animal.getType());
        edit_animal.setMicrochip_code(animal.getMicrochip_code());
        edit_animal.setValidated(animal.isValidated());
        return animalRepository.save(edit_animal);
    }

    @Override
    public Animal findById(int id) {
        return animalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Animal.class, "id", String.valueOf(id)));
    }

    @Override
    public Collection<Animal> findAll() {
        Collection<Animal> itr;
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.getRole() == Role.ROLE_ADMIN) {
            itr = (Collection<Animal>) animalRepository.findAll();
        } else if (user.getRole() == Role.ROLE_CITIZEN) {
            itr = animalRepository.findAllByCitizenId(user.getId());
        } else if (user.getRole() == Role.ROLE_VET) {
            itr = animalRepository.findAllByCitizenCity(user.getCity());
        } else if (user.getRole() == Role.ROLE_EMPLOYEE) {
            itr = animalRepository.findAllByCitizenState(user.getState());
        } else {
            itr = new ArrayList<>();
        }
        return itr;
    }
}
