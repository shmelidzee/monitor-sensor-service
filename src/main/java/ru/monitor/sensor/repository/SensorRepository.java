package ru.monitor.sensor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.monitor.sensor.model.entity.Sensor;


@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Query("SELECT s FROM Sensor s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :searcher, '%')) OR LOWER(s.model) LIKE LOWER(CONCAT('%', :searcher, '%'))")
    Page<Sensor> findSensorsBySearcher(String searcher, Pageable pageable);

}
