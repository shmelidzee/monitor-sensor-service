package ru.monitor.sensor.service;

import ru.monitor.sensor.model.entity.SensorUnit;
import ru.monitor.sensor.model.request.SensorUnitCreateRequest;

import java.util.List;

public interface SensorUnitService {

    SensorUnit findSensorUnitByName(String name);

    List<SensorUnit> getAllSensorUnits();

    SensorUnit getSensorUnitById(Long id);

    SensorUnit createSensorUnit(SensorUnitCreateRequest request);

    SensorUnit updateSensorUnit(Long id, SensorUnitCreateRequest request);

    void deleteSensorUnit(Long id);
}
