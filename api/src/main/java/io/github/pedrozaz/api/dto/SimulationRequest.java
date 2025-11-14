package io.github.pedrozaz.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SimulationRequest(
        PlantModelDto plant,
        PidParamsDto pid,
        Double setpoint,
        Double duration,
        @JsonProperty("time_step") Double timeStep
) {
}
