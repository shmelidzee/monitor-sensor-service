package ru.monitor.sensor.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sensors")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String model;

    @Column(name = "range_from", nullable = false)
    private Integer rangeFrom;

    @Column(name = "range_to", nullable = false)
    private Integer rangeTo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id")
    private SensorType type;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private SensorUnit unit;

    private String location;

    private String description;
}
