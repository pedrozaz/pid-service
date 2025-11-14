package io.github.pedrozaz.api.repository;

import io.github.pedrozaz.api.entity.SimulationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SimulationRepository extends JpaRepository<SimulationResult, UUID> {
    List<SimulationResult> findByPlantId(UUID plantId);
}
