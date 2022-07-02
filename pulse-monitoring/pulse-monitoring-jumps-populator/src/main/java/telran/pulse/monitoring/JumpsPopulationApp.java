package telran.pulse.monitoring;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitoring.dto.SensorJump;
import telran.pulse.monitoring.entities.JumpDoc;
import telran.pulse.monitoring.repo.JumpsRepository;

import java.util.function.Consumer;

@SpringBootApplication
public class JumpsPopulationApp {

    public static void main(String[] args) {
        SpringApplication.run(JumpsPopulationApp.class, args);
    }
    
    Logger LOG = LoggerFactory.getLogger(JumpsPopulationApp.class);
    
    @Autowired
    JumpsRepository jumpsRepository;

    @Bean
    Consumer<SensorJump> jumpsConsumer() {
        return this::jumpsProcessing;
    }

    private void jumpsProcessing(SensorJump sensorJump) {
        LOG.trace("received sensor id {} previous value {} - cur value {} = jump {}",
                sensorJump.sensorId,
                sensorJump.previousValue,
                sensorJump.currentValue,
                Math.abs(sensorJump.previousValue - sensorJump.currentValue));
        jumpsRepository.insert(JumpDoc.build(sensorJump));
    }
}