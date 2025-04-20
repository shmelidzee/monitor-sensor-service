package ru.monitor.sensor.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.monitor.sensor.model.entity.SensorUnit;
import ru.monitor.sensor.model.request.SensorUnitCreateRequest;
import ru.monitor.sensor.repository.SensorUnitRepository;
import ru.monitor.sensor.service.SensorUnitService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorUnitServiceImpl implements SensorUnitService {

    private final SensorUnitRepository sensorUnitRepository;

    @Override
    public SensorUnit findSensorUnitByName(String name) {
        return sensorUnitRepository.findSensorUnitByName(name)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Sensor unit with name %s not found", name)));
    }

    @Override
    public List<SensorUnit> getAllSensorUnits() {
        return sensorUnitRepository.findAll();
    }

    @Override
    public SensorUnit getSensorUnitById(Long id) {
        return sensorUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Sensor unit with id %d not found", id)));
    }

    @Override
    public SensorUnit createSensorUnit(SensorUnitCreateRequest request) {
        SensorUnit sensorUnit = new SensorUnit();
        sensorUnit.setName(request.getName());
        return sensorUnitRepository.save(sensorUnit);
    }

    @Override
    public SensorUnit updateSensorUnit(Long id, SensorUnitCreateRequest request) {
        SensorUnit existingSensorUnit = sensorUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Sensor unit with id %d not found", id)));
        existingSensorUnit.setName(request.getName());
        return sensorUnitRepository.save(existingSensorUnit);
    }

    @Override
    public void deleteSensorUnit(Long id) {
        SensorUnit existingSensorUnit = sensorUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Sensor unit with id %d not found", id)));
        sensorUnitRepository.delete(existingSensorUnit);
    }
}

