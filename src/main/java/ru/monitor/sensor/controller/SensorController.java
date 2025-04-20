package ru.monitor.sensor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.monitor.sensor.model.entity.Sensor;
import ru.monitor.sensor.model.request.SensorCreateRequest;
import ru.monitor.sensor.service.SensorService;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensors")
@Tag(name = "Sensors", description = "Sensor API")
public class SensorController {

    private final SensorService sensorService;

    @GetMapping
    @Operation(summary = "Get all sensor",
            description = "Get all sensor with pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get all sensor"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Sensors")
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public Page<Sensor> getAllSensors(@RequestParam(required = false) String searcher,
                                      Pageable pageable) {
        return sensorService.getSensors(searcher, pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get sensor by ID",
            description = "Get sensor by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sensor found"),
                    @ApiResponse(responseCode = "404", description = "Sensor not found")
            },
            tags = "Sensors"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sensor> getSensorById(@PathVariable Long id) {
        return ResponseEntity.ok(sensorService.getSensorById(id));
    }

    @PostMapping
    @Operation(
            summary = "Create a new sensor",
            description = "Create a new sensor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sensor created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Sensors"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sensor> createSensor(@RequestBody @Valid SensorCreateRequest request) {
        return ResponseEntity.ok(sensorService.createSensor(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update sensor",
            description = "Update by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sensor updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Sensor not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid data")
            },
            tags = "Sensors"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long id, @RequestBody @Valid SensorCreateRequest request) {
        return ResponseEntity.ok(sensorService.updateSensor(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete sensor",
            description = "Delete sensor by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sensor deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Sensor not found")
            },
            tags = "Sensors"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        sensorService.deleteSensor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
