package io.github.pedrozaz.api.controller;

import io.github.pedrozaz.api.client.SimulationClient;
import io.github.pedrozaz.api.dto.*;
import io.github.pedrozaz.api.entity.Plant;
import io.github.pedrozaz.api.repository.PlantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/simulations")
public class SimulationController {

    private final PlantRepository plantRepository;
    private final SimulationClient simulationClient;

    public SimulationController(PlantRepository plantRepository, SimulationClient simulationClient) {
        this.plantRepository = plantRepository;
        this.simulationClient = simulationClient;
    }

    @PostMapping
    public ResponseEntity<SimulationResponse> runSimulation(@RequestBody SimulationRunDTO runDTO) {
        Plant plant = plantRepository.findById(runDTO.plantId())
                .orElseThrow(() -> new RuntimeException("Plant not found"));

        PlantModelDto plantDto = new PlantModelDto(
                plant.getGain(),
                plant.getTimeConstant(),
                plant.getDeadTime()
        );

        PidParamsDto pidDto = new PidParamsDto(
                runDTO.kp(), runDTO.ki(), runDTO.kd()
        );

        SimulationRequest request = new SimulationRequest(
                plantDto,
                pidDto,
                runDTO.setpoint(),
                runDTO.duration(),
                0.1
        );

        SimulationResponse response = simulationClient.runSimulation(request);

        return ResponseEntity.ok(response);
    }
}
