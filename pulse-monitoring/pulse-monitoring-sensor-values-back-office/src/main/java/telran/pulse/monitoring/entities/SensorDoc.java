package telran.pulse.monitoring.entities;

import java.time.*;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "avg_values")
public class SensorDoc {

	private int sensorId;
	private int value;
	private LocalDateTime dateTime;
	
	public int getSensorId() {
		return sensorId;
	}
	public int getValue() {
		return value;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
}
