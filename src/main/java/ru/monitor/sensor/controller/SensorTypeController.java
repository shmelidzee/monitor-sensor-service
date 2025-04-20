package ru.monitor.sensor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.monitor.sensor.model.entity.SensorType;
import ru.monitor.sensor.model.request.SensorTypeCreateRequest;
import ru.monitor.sensor.service.SensorTypeService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensors/types")
@Tag(name = "Sensor types", description = "Sensor type API")
public class SensorTypeController {

    private final SensorTypeService sensorTypeService;

    @GetMapping
    @Operation(
            summary = "Get sensor types",
            description = "Get all sensor types",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of sensor types retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Sensor types not found")
            },
            tags = "Sensor types"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SensorType>> getAllSensorTypes() {
        return ResponseEntity.ok(sensorTypeService.getAllSensorTypes());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get sensor type by ID",
            description = "Get sensor type by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sensor type found"),
                    @ApiResponse(responseCode = "404", description = "Sensor type not found")
            },
            tags = "Sensor types"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SensorType> getSensorTypeById(@PathVariable Long id) {
        SensorType sensorType = sensorTypeService.getSensorTypeById(id);
        return sensorType != null ? ResponseEntity.ok(sensorType) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
            summary = "Create new sensor type",
            description = "Create a new sensor type.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sensor type created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Sensor types"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SensorType> createSensorType(@RequestBody SensorTypeCreateRequest request) {
        SensorType createdSensorType = sensorTypeService.createSensorType(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSensorType);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update sensor type",
            description = "Update sensor type by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sensor type updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Sensor type not found"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Sensor types"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SensorType> updateSensorType(@PathVariable Long id, @RequestBody SensorTypeCreateRequest request) {
        SensorType updatedSensorType = sensorTypeService.updateSensorType(id, request);
        return updatedSensorType != null ? ResponseEntity.ok(updatedSensorType) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete sensor type",
            description = "Delete a sensor type by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sensor type deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Sensor type not found")
            },
            tags = "Sensor types"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSensorType(@PathVariable Long id) {
        sensorTypeService.deleteSensorType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
