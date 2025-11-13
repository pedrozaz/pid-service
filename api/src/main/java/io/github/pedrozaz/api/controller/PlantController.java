package io.github.pedrozaz.api.controller;

import io.github.pedrozaz.api.dto.PlantCreateDTO;
import io.github.pedrozaz.api.entity.Plant;
import io.github.pedrozaz.api.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plants")
public class PlantController {

    private final PlantRepository plantRepository;

    public  PlantController(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    @PostMapping
    public ResponseEntity<Plant> createPlant(@RequestBody PlantCreateDTO dto) {
        Plant plant = new Plant();
        plant.setName(dto.name());
        plant.setGain(dto.gain());
        plant.setTimeConstant(dto.timeConstant());
        plant.setDeadTime(dto.deadTime());

        Plant savedPlant = plantRepository.save(plant);

        return ResponseEntity.ok(savedPlant);
    }

    @GetMapping
    public ResponseEntity<List<Plant>> getAllPlants() {
        return ResponseEntity.ok(plantRepository.findAll());
    }
}
