package io.github.pedrozaz.api.dto;

public record PlantCreateDTO(
        String name,
        Double gain,
        Double timeConstant,
        Double deadTime
) {
}
