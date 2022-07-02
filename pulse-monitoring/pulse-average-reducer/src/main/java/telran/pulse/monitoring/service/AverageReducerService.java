package telran.pulse.monitoring.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Service;

import telran.pulse.monitoring.dto.Sensor;
import telran.pulse.monitoring.entities.SensorListRedis;
import telran.pulse.monitoring.repo.SensorRepository;

@Service
@ManagedResource
public class AverageReducerService {

	static Logger LOG = LoggerFactory.getLogger(AverageReducerService.class);
	@Autowired
	SensorRepository sensorRepository;
	@Autowired
	StreamBridge streamBridge;
	
	Instant timestamp = Instant.now();
	@Value("${app.period.reduction:600000}")
	long reducingPeriod;
	@ManagedOperation
	public long getReducingPeriod() {
		return reducingPeriod;
	}
	@ManagedOperation
	public void setReducingPeriod(long reducingPeriod) {
		this.reducingPeriod = reducingPeriod;
	}
	@Value("${app.size.reduction:100}")
	int reducingSize;
	@ManagedOperation
	public int getReducingSize() {
		return reducingSize;
	}
	@ManagedOperation
	public void setReducingSize(int reducingSize) {
		this.reducingSize = reducingSize;
	}
	
	@Bean
	Consumer<Sensor> pulseConsumer() {
		return this::pulseAvgProcessing;
	}
	
	private void pulseAvgProcessing(Sensor sensor) {
		LOG.trace("Received sensor {} value {}", sensor.id, sensor.value);
		SensorListRedis sensorList = sensorRepository.findById(sensor.id).orElse(null);
		if (sensorList == null) {
			sensorList = new SensorListRedis(sensor.id);
		}
		List<Integer> values = sensorList.getValues();
		values.add(sensor.value);
		if (checkAverageProcessing(values.size())) {
			averageProcessing(values, sensor.id);
			values.clear();
		}
		sensorRepository.save(sensorList);
	}
	
	private void averageProcessing(List<Integer> values, int sensorId) {
		double avg = values.stream().mapToInt(x -> x).average().getAsDouble();
		streamBridge.send("avg-values-out-0", new Sensor(sensorId, (int)avg));
		LOG.info("Sensor id {} avg value {} was sent to redis", sensorId, avg);
		timestamp = Instant.now();
	}
	
	private boolean checkAverageProcessing(int valuesSize) {
		return ChronoUnit.MILLIS.between(timestamp, Instant.now()) > reducingPeriod || 
				valuesSize > reducingSize;
	}
}
