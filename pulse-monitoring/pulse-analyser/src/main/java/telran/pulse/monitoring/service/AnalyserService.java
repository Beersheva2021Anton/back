package telran.pulse.monitoring.service;

import java.util.function.Consumer;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Service;

import telran.pulse.monitoring.dto.*;
import telran.pulse.monitoring.entities.SensorLastValueRedis;
import telran.pulse.monitoring.repo.SensorRepository;

@Service
@ManagedResource
public class AnalyserService {

	@Autowired
	StreamBridge streamBridge;
	@Autowired
	SensorRepository sensorRepository;
	@Value("${app.jump.threshold:50}")
	int jumpPercentThreshold;
	
	@ManagedOperation
	public int getJumpPercentThreshold() {
		return jumpPercentThreshold;
	}

	@ManagedOperation
	public void setJumpPercentThreshold(int jumpPercentThreshold) {
		this.jumpPercentThreshold = jumpPercentThreshold;
	}

	@Value("${app.critical.threshold:100}")
	int criticalPercentThreshold;
	
	static Logger LOG = LoggerFactory.getLogger(AnalyserService.class);
	
	@Bean
	Consumer<Sensor> pulseConsumer() {
		return this::pulseProcessing;
	}
	
	void pulseProcessing(Sensor sensor) {
		LOG.trace("Received sensor id {}; value {}", sensor.id, sensor.value);
		SensorLastValueRedis sensorRedis = sensorRepository.findById(sensor.id).orElse(null);
		if (sensorRedis == null) {
			LOG.debug("For sensor id {} nor found record in Redis", sensor.id);
			sensorRedis = new SensorLastValueRedis(sensor.id);			
		} else {
			int lastValue = sensorRedis.getLastValue();
			int delta = Math.abs(lastValue - sensor.value);
			double percent = (double) delta / lastValue * 100;
			if (percent > jumpPercentThreshold) {
				LOG.debug("Sensor id {} has values jump {}", sensor.id, delta);
				SensorJump sensorJump = new SensorJump(sensor.id, lastValue, sensor.value);
				streamBridge.send("jumps-out-0", sensorJump);
				
				if (percent > criticalPercentThreshold) {
					LOG.info("Sensor id {} has critical values jump {}", sensor.id, delta);
					streamBridge.send("crit-jumps-out-0", sensorJump);
				}
			}			
		}
		sensorRedis.setCurrentValue(sensor.value);
		sensorRepository.save(sensorRedis);
	}
}
