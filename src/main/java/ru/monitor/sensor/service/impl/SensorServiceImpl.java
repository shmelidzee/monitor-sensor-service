package ru.monitor.sensor.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.monitor.sensor.model.entity.Sensor;
import ru.monitor.sensor.model.entity.SensorType;
import ru.monitor.sensor.model.entity.SensorUnit;
import ru.monitor.sensor.model.request.SensorCreateRequest;
import ru.monitor.sensor.repository.SensorRepository;
import ru.monitor.sensor.service.SensorService;
import ru.monitor.sensor.service.SensorTypeService;
import ru.monitor.sensor.service.SensorUnitService;


@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final SensorUnitService sensorUnitService;
    private final SensorTypeService sensorTypeService;

    @Override
    public Page<Sensor> getSensors(String searcher, Pageable pageable) {
        if (searcher == null) {
            return sensorRepository.findAll(pageable);
        }
        return sensorRepository.findSensorsBySearcher(searcher, pageable);
    }

    @Override
    public Sensor getSensorById(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Sensor with id %d not found", id)));
    }

    @Override
    public Sensor createSensor(SensorCreateRequest request) {
        SensorType sensorType = sensorTypeService.findSensorTypeByName(request.getType());
        SensorUnit sensorUnit = sensorUnitService.findSensorUnitByName(request.getUnit());

        Sensor sensor = Sensor.builder()
                .name(request.getName())
                .model(request.getModel())
                .rangeFrom(request.getRange().getFrom())
                .rangeTo(request.getRange().getTo())
                .type(sensorType)
                .unit(sensorUnit)
                .location(request.getLocation())
                .description(request.getDescription())
                .build();
        return sensorRepository.save(sensor);
    }

    @Override
    public Sensor updateSensor(Long id, SensorCreateRequest request) {
        Sensor existingSensor = sensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Sensor with id %d not found", id)));

        SensorType sensorType = sensorTypeService.findSensorTypeByName(request.getType());
        SensorUnit sensorUnit = sensorUnitService.findSensorUnitByName(request.getUnit());
        updateSensorFields(existingSensor, request, sensorType, sensorUnit);
        return sensorRepository.save(existingSensor);
    }

    @Override
    public void deleteSensor(Long id) {
        Sensor existingSensor = sensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Sensor with id %d not found", id)));
        sensorRepository.delete(existingSensor);
    }

    private void updateSensorFields(Sensor existingSensor, SensorCreateRequest request, SensorType sensorType, SensorUnit sensorUnit) {
        existingSensor.setName(request.getName());
        existingSensor.setModel(request.getModel());
        existingSensor.setRangeFrom(request.getRange().getFrom());
        existingSensor.setRangeTo(request.getRange().getTo());
        existingSensor.setType(sensorType);
        existingSensor.setUnit(sensorUnit);
        existingSensor.setLocation(request.getLocation());
        existingSensor.setDescription(request.getDescription());
    }
}
