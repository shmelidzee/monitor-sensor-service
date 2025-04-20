package ru.monitor.sensor.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SensorUnitCreateRequest {
    @NotBlank(message = "Name is required")
    private String name;
}
