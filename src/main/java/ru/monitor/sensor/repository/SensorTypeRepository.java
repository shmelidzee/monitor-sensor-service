package ru.monitor.sensor.repository;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.monitor.sensor.model.entity.SensorType;

import java.util.Optional;

@Repository
public interface SensorTypeRepository extends JpaRepository<SensorType, Long> {

    Optional<SensorType> findSensorTypeByName(String name);
}
