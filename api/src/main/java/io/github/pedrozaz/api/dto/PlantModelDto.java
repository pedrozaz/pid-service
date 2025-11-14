package io.github.pedrozaz.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlantModelDto(
        Double gain,
        @JsonProperty("time_constant") Double timeConstant,
        @JsonProperty("dead_time") Double deadTime
) {
}
