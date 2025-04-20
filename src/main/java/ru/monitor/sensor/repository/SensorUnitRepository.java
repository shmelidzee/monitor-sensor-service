package ru.monitor.sensor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.monitor.sensor.model.entity.SensorUnit;

import java.util.Optional;

@Repository
public interface SensorUnitRepository extends JpaRepository<SensorUnit, Long> {

    Optional<SensorUnit> findSensorUnitByName(String name);
}
