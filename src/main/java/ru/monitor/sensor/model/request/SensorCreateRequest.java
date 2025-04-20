package ru.monitor.sensor.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class SensorCreateRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;

    @NotBlank(message = "Model is required")
    @Size(max = 15, message = "Model must not exceed 15 characters")
    private String model;

    @Valid
    @NotNull(message = "Range is required")
    private Range range;

    @NotBlank(message = "Type is required")
    private String type;

    private String unit;

    @Size(max = 40, message = "Location must not exceed 40 characters")
    private String location;

    @Size(max = 200, message = "Description must not exceed 200 characters")
    private String description;

    @Data
    public static class Range {

        @NotNull(message = "From is required")
        @Positive(message = "From must be a positive number")
        private Integer from;

        @NotNull(message = "To is required")
        @Positive(message = "To must be a positive number")
        private Integer to;

        @AssertTrue(message = "'from' must be less than 'to'")
        public boolean isValidRange() {
            return from != null && to != null && from < to;
        }
    }
}
