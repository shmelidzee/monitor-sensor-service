package ru.monitor.sensor.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorTypeCreateRequest {
    @NotBlank(message = "Name is required")
    private String name;
}
