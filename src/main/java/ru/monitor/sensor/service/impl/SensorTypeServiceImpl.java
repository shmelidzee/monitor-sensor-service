package ru.monitor.sensor.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.monitor.sensor.model.entity.SensorType;
import ru.monitor.sensor.model.request.SensorTypeCreateRequest;
import ru.monitor.sensor.repository.SensorTypeRepository;
import ru.monitor.sensor.service.SensorTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorTypeServiceImpl implements SensorTypeService {

    private final SensorTypeRepository sensorTypeRepository;

    @Override
    public SensorType findSensorTypeByName(String name) {
        return sensorTypeRepository.findSensorTypeByName(name)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Sensor type with name %s not found", name)));
    }

    @Override
    public List<SensorType> getAllSensorTypes() {
        return sensorTypeRepository.findAll();
    }

    @Override
    public SensorType getSensorTypeById(Long id) {
        return sensorTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Sensor type not found with id: %d", id)));
    }

    @Override
    public SensorType createSensorType(SensorTypeCreateRequest request) {
        SensorType sensorType = new SensorType();
        sensorType.setName(request.getName());
        return sensorTypeRepository.save(sensorType);
    }

    @Override
    public SensorType updateSensorType(Long id, SensorTypeCreateRequest request) {
        SensorType existingSensorType = sensorTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Sensor type not found with id: %d", id)));
        existingSensorType.setName(request.getName());
        return sensorTypeRepository.save(existingSensorType);
    }

    @Override
    public void deleteSensorType(Long id) {
        SensorType existingSensorType = sensorTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Sensor type not found with id: %d", id)));
        sensorTypeRepository.delete(existingSensorType);
    }
}
