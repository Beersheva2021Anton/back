package telran.pulse.monitoring;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import telran.pulse.monitoring.dto.Sensor;

@SpringBootApplication
public class PulseSourceApp {

	int count = 0;
	static ConfigurableApplicationContext ctx;
	
	public static void main(String[] args) {		
		ctx = SpringApplication.run(PulseSourceApp.class, args);
	}
	
	@Bean
	Supplier<Sensor> pulseSupplier() {
		return this::pulseRandomGeneration;
	}
	
	Sensor pulseRandomGeneration() {
		int id = getRandomNumber(1, 10);
		int value = getRandomNumber(40, 220);
		var sensor = new Sensor(id, value);
		return sensor;
	}

	private int getRandomNumber(int min, int max) {		
		return ThreadLocalRandom.current().nextInt(min, max);
	}
}
