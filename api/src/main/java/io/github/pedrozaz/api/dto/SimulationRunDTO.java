package io.github.pedrozaz.api.dto;

import java.util.UUID;

public record SimulationRunDTO(
        UUID plantId,
        Double kp, Double ki, Double kd,
        Double setpoint, Double duration
) {
}
