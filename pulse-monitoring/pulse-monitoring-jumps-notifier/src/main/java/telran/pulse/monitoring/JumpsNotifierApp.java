package telran.pulse.monitoring;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import telran.pulse.monitoring.dto.DoctorPatientData;
import telran.pulse.monitoring.dto.SensorJump;

import java.util.function.Consumer;

@SpringBootApplication
public class JumpsNotifierApp {

    public static void main(String[] args) {
        SpringApplication.run(JumpsNotifierApp.class, args);
    }
    
    Logger LOG = LoggerFactory.getLogger(JumpsNotifierApp.class);
    
    @Autowired
    JavaMailSender jms;
    
    @Autowired
    DataProviderClient client;
    
    @Value("${app.mail.subject:Critical pulse jump}")
    String subject;

    @Bean
    Consumer<SensorJump> criticalJumpsConsumer() {
        return this::jumpsProcessing;
    }

    private void jumpsProcessing(SensorJump sensorJump) {
        LOG.trace("received sensor id {} previous value {} - cur value {} = jump {}",
                sensorJump.sensorId,
                sensorJump.previousValue,
                sensorJump.currentValue,
                Math.abs(sensorJump.previousValue - sensorJump.currentValue));
        DoctorPatientData data = client.getData(sensorJump.sensorId);
        LOG.debug("data received is: {}", data);
        sendMail(data, sensorJump);
    }

	private void sendMail(DoctorPatientData data, SensorJump sensorJump) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(data.email);
		message.setSubject(subject);
		message.setText(String.format("Dear %s, \nYour patient %s had the critical pulse jump \n" + 
				"Previous pulse value: %d; current pulse value: %d\n",
				data.doctorName, data.patientName, sensorJump.previousValue, sensorJump.currentValue));
		jms.send(message);
		LOG.debug("Mail sent to {}", data.email);
	}
}