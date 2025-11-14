package io.github.pedrozaz.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "simulation_results")
public class SimulationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime createdAt =  LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    private Double kp;
    private Double ki;
    private Double kd;
    private Double setpoint;
    private Double duration;

    @Column(columnDefinition = "TEXT")
    private String resultJson;
}
