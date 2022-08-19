package com.pet.registry.controllers.api;

import com.pet.registry.dtos.AnimalDto;
import com.pet.registry.models.Animal;
import com.pet.registry.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/animals")
public class AnimalController {
    @Autowired
    AnimalService animalService;

    @PreAuthorize("hasRole('ROLE_CITIZEN') or hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseBody
    public Animal createAnimal(@RequestBody @Valid AnimalDto animal) {
        return animalService.save(animal);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Animal updateAnimal(@RequestBody @Valid AnimalDto animal, @PathVariable("id") int id) {
        return animalService.update(animal, id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Animal getAnimal(@PathVariable("id") int id) {
        return animalService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_CITIZEN') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public int deleteAnimal(@PathVariable("id") int id) {
        return animalService.delete(id);
    }

    @GetMapping
    @ResponseBody
    public Collection<Animal> getAnimals() {
        return animalService.findAll();
    }
}
