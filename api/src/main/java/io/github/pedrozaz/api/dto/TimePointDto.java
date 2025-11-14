package io.github.pedrozaz.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TimePointDto(
        Double time,
        @JsonProperty("process_variable") Double processVariable,
        Double setpoint
) {
}
