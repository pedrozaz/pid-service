package io.github.pedrozaz.api.dto;

import java.util.List;

public record SimulationResponse(
        List<TimePointDto> results
) {
}
