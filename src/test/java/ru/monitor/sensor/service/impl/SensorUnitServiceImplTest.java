package ru.monitor.sensor.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.monitor.sensor.model.entity.SensorUnit;
import ru.monitor.sensor.model.request.SensorUnitCreateRequest;
import ru.monitor.sensor.repository.SensorUnitRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorUnitServiceImplTest {

    @InjectMocks
    private SensorUnitServiceImpl sensorUnitService;

    @Mock
    private SensorUnitRepository sensorUnitRepository;

    @Test
    void should_return_sensor_unit_by_name() {
        SensorUnit unit = new SensorUnit(1L, "Celsius");
        when(sensorUnitRepository.findSensorUnitByName("Celsius")).thenReturn(Optional.of(unit));

        SensorUnit result = sensorUnitService.findSensorUnitByName("Celsius");

        assertEquals("Celsius", result.getName());
        verify(sensorUnitRepository).findSensorUnitByName("Celsius");
    }

    @Test
    void should_throw_exception_when_sensor_unit_name_not_found() {
        when(sensorUnitRepository.findSensorUnitByName("Unknown")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sensorUnitService.findSensorUnitByName("Unknown"));

        assertEquals("Sensor unit with name Unknown not found", exception.getMessage());
    }

    @Test
    void should_return_all_sensor_units() {
        List<SensorUnit> units = List.of(
                new SensorUnit(1L, "Celsius"),
                new SensorUnit(2L, "Kelvin")
        );
        when(sensorUnitRepository.findAll()).thenReturn(units);

        List<SensorUnit> result = sensorUnitService.getAllSensorUnits();

        assertEquals(2, result.size());
        verify(sensorUnitRepository).findAll();
    }

    @Test
    void should_return_sensor_unit_by_id() {
        SensorUnit unit = new SensorUnit(1L, "Kelvin");
        when(sensorUnitRepository.findById(1L)).thenReturn(Optional.of(unit));

        SensorUnit result = sensorUnitService.getSensorUnitById(1L);

        assertEquals("Kelvin", result.getName());
        verify(sensorUnitRepository).findById(1L);
    }

    @Test
    void should_throw_exception_when_sensor_unit_id_not_found() {
        when(sensorUnitRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> sensorUnitService.getSensorUnitById(1L));

        assertEquals("Sensor unit with id 1 not found", exception.getMessage());
    }

    @Test
    void should_create_sensor_unit() {
        SensorUnitCreateRequest request = new SensorUnitCreateRequest("Lux");
        SensorUnit saved = new SensorUnit(1L, "Lux");

        when(sensorUnitRepository.save(any(SensorUnit.class))).thenReturn(saved);

        SensorUnit result = sensorUnitService.createSensorUnit(request);

        assertEquals("Lux", result.getName());
        verify(sensorUnitRepository).save(any(SensorUnit.class));
    }

    @Test
    void should_update_sensor_unit() {
        SensorUnit existing = new SensorUnit(1L, "OldUnit");
        SensorUnitCreateRequest request = new SensorUnitCreateRequest("NewUnit");

        when(sensorUnitRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(sensorUnitRepository.save(any(SensorUnit.class))).thenAnswer(inv -> inv.getArgument(0));

        SensorUnit updated = sensorUnitService.updateSensorUnit(1L, request);

        assertEquals("NewUnit", updated.getName());
        verify(sensorUnitRepository).save(existing);
    }

    @Test
    void should_delete_sensor_unit() {
        SensorUnit unit = new SensorUnit(1L, "ToDelete");
        when(sensorUnitRepository.findById(1L)).thenReturn(Optional.of(unit));

        sensorUnitService.deleteSensorUnit(1L);

        verify(sensorUnitRepository).delete(unit);
    }

    @Test
    void should_throw_exception_when_deleting_non_existing_sensor_unit() {
        when(sensorUnitRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> sensorUnitService.deleteSensorUnit(1L));

        assertEquals("Sensor unit with id 1 not found", exception.getMessage());
    }
}