package ru.monitor.sensor.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import ru.monitor.sensor.model.entity.Sensor;
import ru.monitor.sensor.model.request.SensorCreateRequest;


public interface SensorService {

    Page<Sensor> getSensors(String searcher, Pageable pageable);

    Sensor getSensorById(Long id);

    Sensor createSensor(SensorCreateRequest request);

    Sensor updateSensor(Long id, SensorCreateRequest request);

    void deleteSensor(Long id);
}
