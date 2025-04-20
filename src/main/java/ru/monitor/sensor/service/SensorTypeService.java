package ru.monitor.sensor.service;

import ru.monitor.sensor.model.entity.SensorType;
import ru.monitor.sensor.model.request.SensorTypeCreateRequest;

import java.util.List;

public interface SensorTypeService {

    SensorType findSensorTypeByName(String name);

    List<SensorType> getAllSensorTypes();

    SensorType getSensorTypeById(Long id);

    SensorType createSensorType(SensorTypeCreateRequest request);

    SensorType updateSensorType(Long id, SensorTypeCreateRequest request);

    void deleteSensorType(Long id);
}
