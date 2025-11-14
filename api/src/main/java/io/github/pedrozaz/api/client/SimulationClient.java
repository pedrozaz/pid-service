package io.github.pedrozaz.api.client;

import io.github.pedrozaz.api.dto.SimulationRequest;
import io.github.pedrozaz.api.dto.SimulationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "simulation-service", url = "${simulation.service.url:http://localhost:8001}")
public interface SimulationClient {

    @PostMapping("/simulate")
    SimulationResponse runSimulation(@RequestBody SimulationRequest request);
}
