package telran.pulse.monitoring.repo;

import org.springframework.data.repository.CrudRepository;

import telran.pulse.monitoring.entities.SensorListRedis;

public interface SensorRepository extends CrudRepository<SensorListRedis, Integer> {

}
