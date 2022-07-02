package telran.pulse.monitoring;

import java.util.function.Consumer;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import telran.pulse.monitoring.dto.Sensor;
import telran.pulse.monitoring.entities.SensorDoc;
import telran.pulse.monitoring.repo.SensorRepository;

@SpringBootApplication
public class AveragePopulatorApp {

	public static void main(String[] args) {
		SpringApplication.run(AveragePopulatorApp.class, args);
	}
	
	static Logger LOG = LoggerFactory.getLogger(AveragePopulatorApp.class);
	
	@Autowired
	SensorRepository sensorRepository;

	@Bean
	Consumer<Sensor> averageConsumer() {
		
		return this::averageProcessing;
	}
	
	private void averageProcessing(Sensor sensor) {
		
		LOG.debug("received sensor id {} with avg value {}", sensor.id, sensor.value);
		sensorRepository.insert(SensorDoc.build(sensor));
	}
}
