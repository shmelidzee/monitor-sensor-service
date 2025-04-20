package ru.monitor.sensor.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.monitor.sensor.model.entity.SensorType;
import ru.monitor.sensor.model.request.SensorTypeCreateRequest;
import ru.monitor.sensor.repository.SensorTypeRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorTypeServiceImplTest {

    @InjectMocks
    private SensorTypeServiceImpl sensorTypeService;

    @Mock
    private SensorTypeRepository sensorTypeRepository;

    @Test
    void should_return_sensor_type_by_name() {
        SensorType type = new SensorType(1L, "Temperature");
        when(sensorTypeRepository.findSensorTypeByName("Temperature")).thenReturn(Optional.of(type));

        SensorType result = sensorTypeService.findSensorTypeByName("Temperature");

        assertEquals("Temperature", result.getName());
        verify(sensorTypeRepository).findSensorTypeByName("Temperature");
    }

    @Test
    void should_throw_exception_when_sensor_type_name_not_found() {
        when(sensorTypeRepository.findSensorTypeByName("Unknown")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sensorTypeService.findSensorTypeByName("Unknown"));

        assertEquals("Sensor type with name Unknown not found", exception.getMessage());
    }

    @Test
    void should_return_all_sensor_types() {
        List<SensorType> types = List.of(
                new SensorType(1L, "Temperature"),
                new SensorType(2L, "Pressure")
        );
        when(sensorTypeRepository.findAll()).thenReturn(types);

        List<SensorType> result = sensorTypeService.getAllSensorTypes();

        assertEquals(2, result.size());
        verify(sensorTypeRepository).findAll();
    }

    @Test
    void should_return_sensor_type_by_id() {
        SensorType type = new SensorType(1L, "Humidity");
        when(sensorTypeRepository.findById(1L)).thenReturn(Optional.of(type));

        SensorType result = sensorTypeService.getSensorTypeById(1L);

        assertEquals("Humidity", result.getName());
        verify(sensorTypeRepository).findById(1L);
    }

    @Test
    void should_throw_exception_when_sensor_type_id_not_found() {
        when(sensorTypeRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> sensorTypeService.getSensorTypeById(1L));

        assertEquals("Sensor type not found with id: 1", exception.getMessage());
    }

    @Test
    void should_create_sensor_type() {
        SensorTypeCreateRequest request = new SensorTypeCreateRequest("Gas");
        SensorType saved = new SensorType(1L, "Gas");

        when(sensorTypeRepository.save(any(SensorType.class))).thenReturn(saved);

        SensorType result = sensorTypeService.createSensorType(request);

        assertEquals("Gas", result.getName());
        verify(sensorTypeRepository).save(any(SensorType.class));
    }

    @Test
    void should_update_sensor_type() {
        SensorType existing = new SensorType(1L, "OldName");
        SensorTypeCreateRequest request = new SensorTypeCreateRequest("NewName");

        when(sensorTypeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(sensorTypeRepository.save(any(SensorType.class))).thenAnswer(inv -> inv.getArgument(0));

        SensorType updated = sensorTypeService.updateSensorType(1L, request);

        assertEquals("NewName", updated.getName());
        verify(sensorTypeRepository).save(existing);
    }

    @Test
    void should_delete_sensor_type() {
        SensorType type = new SensorType(1L, "ToDelete");
        when(sensorTypeRepository.findById(1L)).thenReturn(Optional.of(type));

        sensorTypeService.deleteSensorType(1L);

        verify(sensorTypeRepository).delete(type);
    }

    @Test
    void should_throw_exception_when_deleting_non_existing_sensor_type() {
        when(sensorTypeRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> sensorTypeService.deleteSensorType(1L));

        assertEquals("Sensor type not found with id: 1", exception.getMessage());
    }
}