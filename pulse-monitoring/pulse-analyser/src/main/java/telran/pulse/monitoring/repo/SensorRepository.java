package telran.pulse.monitoring.repo;

import org.springframework.data.repository.CrudRepository;

import telran.pulse.monitoring.entities.SensorLastValueRedis;

public interface SensorRepository extends CrudRepository<SensorLastValueRedis, Integer> {

}
