package telran.pulse.monitoring.entities;

import java.time.*;

import org.springframework.data.mongodb.core.mapping.Document;

import telran.pulse.monitoring.dto.Sensor;

@Document(collection = "avg_values")
public class SensorDoc {

	private int sensorId;
	private int value;
	private LocalDateTime dateTime;
	
	public static SensorDoc build(Sensor sensor) {
		
		var sensorDoc = new SensorDoc();
		sensorDoc.sensorId = sensor.id;
		sensorDoc.value = sensor.value;
		sensorDoc.dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(sensor.timestamp), 
				ZoneId.systemDefault());
		return sensorDoc;
	}
}
