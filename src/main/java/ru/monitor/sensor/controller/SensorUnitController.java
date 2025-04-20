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
import ru.monitor.sensor.model.entity.SensorUnit;
import ru.monitor.sensor.model.request.SensorUnitCreateRequest;
import ru.monitor.sensor.service.SensorUnitService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensors/unit")
@Tag(name = "Sensor units", description = "Sensor units API")
public class SensorUnitController {

    private final SensorUnitService sensorUnitService;


    @GetMapping
    @Operation(
            summary = "Get sensor units",
            description = "Get all sensor units",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of sensor units retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Sensor units not found")
            },
            tags = "Sensor units"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SensorUnit>> getAllSensorUnits() {
        return ResponseEntity.ok(sensorUnitService.getAllSensorUnits());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get sensor unit by ID",
            description = "Get sensor unit by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sensor unit found"),
                    @ApiResponse(responseCode = "404", description = "Sensor unit not found")
            },
            tags = "Sensor units"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SensorUnit> getSensorUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(sensorUnitService.getSensorUnitById(id));
    }

    @PostMapping
    @Operation(
            summary = "Create new sensor unit",
            description = "Create a new sensor unit.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sensor unit created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Sensor units"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SensorUnit> createSensorUnit(@RequestBody SensorUnitCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorUnitService.createSensorUnit(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update sensor unit",
            description = "Update sensor unit by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sensor unit updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Sensor unit not found"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Sensor units"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SensorUnit> updateSensorUnit(@PathVariable Long id, @RequestBody SensorUnitCreateRequest request) {
        return ResponseEntity.ok(sensorUnitService.updateSensorUnit(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete sensor unit",
            description = "Delete a sensor unit by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sensor unit deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Sensor unit not found")
            },
            tags = "Sensor units"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSensorUnit(@PathVariable Long id) {
        sensorUnitService.deleteSensorUnit(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
