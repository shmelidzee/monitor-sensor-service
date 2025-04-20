package ru.monitor.sensor.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.monitor.sensor.model.entity.Sensor;
import ru.monitor.sensor.model.entity.SensorType;
import ru.monitor.sensor.model.entity.SensorUnit;
import ru.monitor.sensor.model.request.SensorCreateRequest;
import ru.monitor.sensor.model.request.SensorCreateRequest.Range;
import ru.monitor.sensor.repository.SensorRepository;
import ru.monitor.sensor.service.SensorTypeService;
import ru.monitor.sensor.service.SensorUnitService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorServiceImplTest {

    @InjectMocks
    private SensorServiceImpl sensorService;

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private SensorTypeService sensorTypeService;

    @Mock
    private SensorUnitService sensorUnitService;

    @Test
    void should_return_sensor_by_id() {
        Sensor sensor = Sensor.builder().id(1L).name("Test Sensor").build();
        when(sensorRepository.findById(1L)).thenReturn(Optional.of(sensor));

        Sensor result = sensorService.getSensorById(1L);

        assertEquals("Test Sensor", result.getName());
        verify(sensorRepository).findById(1L);
    }

    @Test
    void should_return_exception_because_sensor_not_found() {
        when(sensorRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> sensorService.getSensorById(1L));

        assertEquals("Sensor with id 1 not found", exception.getMessage());
    }

    @Test
    void should_crete_sensor() {
        Range range = new Range(39, 57);
        SensorCreateRequest request = new SensorCreateRequest(
                "seek", "box", range,
                "past", "window", "Lauraton", "Description"
        );

        SensorType sensorType = new SensorType(1L, "past");
        SensorUnit sensorUnit = new SensorUnit(1L, "window");

        when(sensorTypeService.findSensorTypeByName("past")).thenReturn(sensorType);
        when(sensorUnitService.findSensorUnitByName("window")).thenReturn(sensorUnit);

        Sensor savedSensor = Sensor.builder()
                .id(1L).name("seek").model("box").rangeFrom(39).rangeTo(57)
                .type(sensorType).unit(sensorUnit).location("Lauraton")
                .description("Description").build();

        when(sensorRepository.save(any(Sensor.class))).thenReturn(savedSensor);

        Sensor result = sensorService.createSensor(request);

        assertEquals("seek", result.getName());
        assertEquals("box", result.getModel());
        verify(sensorRepository).save(any(Sensor.class));
    }

    @Test
    void should_update_sensor() {
        Sensor existing = Sensor.builder()
                .id(1L).name("old").model("oldModel")
                .rangeFrom(0).rangeTo(10)
                .type(new SensorType(1L, "oldType"))
                .unit(new SensorUnit(1L, "oldUnit"))
                .location("OldCity")
                .description("OldDesc").build();

        SensorCreateRequest request = new SensorCreateRequest(
                "seek", "box", new Range(39, 57),
                "past", "window", "Lauraton", "NewDesc"
        );

        when(sensorRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(sensorTypeService.findSensorTypeByName("past")).thenReturn(new SensorType(2L, "past"));
        when(sensorUnitService.findSensorUnitByName("window")).thenReturn(new SensorUnit(2L, "window"));
        when(sensorRepository.save(any(Sensor.class))).thenAnswer(inv -> inv.getArgument(0));

        Sensor updated = sensorService.updateSensor(1L, request);

        assertEquals("seek", updated.getName());
        assertEquals("NewDesc", updated.getDescription());
        verify(sensorRepository).save(existing);
    }

    @Test
    void should_delete_sensor() {
        Sensor sensor = Sensor.builder().id(1L).name("ToDelete").build();
        when(sensorRepository.findById(1L)).thenReturn(Optional.of(sensor));

        sensorService.deleteSensor(1L);

        verify(sensorRepository).delete(sensor);
    }

    @Test
    void should_return_exception_delete_because_sensor_not_found() {
        when(sensorRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> sensorService.deleteSensor(1L));

        assertEquals("Sensor with id 1 not found", exception.getMessage());
    }

}