package io.github.pedrozaz.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pedrozaz.api.client.SimulationClient;
import io.github.pedrozaz.api.dto.*;
import io.github.pedrozaz.api.entity.Plant;
import io.github.pedrozaz.api.entity.SimulationResult;
import io.github.pedrozaz.api.repository.PlantRepository;
import io.github.pedrozaz.api.repository.SimulationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/simulations")
public class SimulationController {

    private final PlantRepository plantRepository;
    private final SimulationClient simulationClient;
    private final SimulationRepository simulationRepository;
    private final ObjectMapper objectMapper;

    public SimulationController(PlantRepository plantRepository, SimulationClient simulationClient, SimulationRepository simulationRepository, ObjectMapper objectMapper) {
        this.plantRepository = plantRepository;
        this.simulationClient = simulationClient;
        this.simulationRepository = simulationRepository;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<SimulationResult> runSimulation(@RequestBody SimulationRunDTO runDto) {
        Plant plant = plantRepository.findById(runDto.plantId())
                .orElseThrow(() -> new RuntimeException("Plant not found."));

        PlantModelDto plantDto = new PlantModelDto(plant.getGain(), plant.getTimeConstant(), plant.getDeadTime());
        PidParamsDto pidDto = new PidParamsDto(runDto.kp(), runDto.ki(), runDto.kd());

        SimulationRequest request = new SimulationRequest(
                plantDto, pidDto, runDto.setpoint(), runDto.duration(), 0.1
        );

        SimulationResponse response = simulationClient.runSimulation(request);

        SimulationResult result = new SimulationResult();
        result.setPlant(plant);
        result.setKp(runDto.kp());
        result.setKi(runDto.ki());
        result.setKd(runDto.kd());
        result.setSetpoint(runDto.setpoint());
        result.setDuration(runDto.duration());

        try {
            String jsonString = objectMapper.writeValueAsString(response.results());
            result.setResultJson(jsonString);
        } catch (Exception e) {
            throw new RuntimeException("Error while serializing JSON", e);
        }

        SimulationResult savedResult = simulationRepository.save(result);

        return ResponseEntity.ok(savedResult);
    }

    @GetMapping("/plant/{plantId}")
    public ResponseEntity<List<SimulationResult>> getByPlant(@PathVariable UUID plantId) {
        return ResponseEntity.ok(simulationRepository.findByPlantId(plantId));
    }
}
