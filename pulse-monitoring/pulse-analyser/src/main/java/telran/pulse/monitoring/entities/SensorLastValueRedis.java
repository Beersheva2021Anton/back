package telran.pulse.monitoring.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class SensorLastValueRedis {
	
	@Id
	int id;	
	int lastValue;

	public SensorLastValueRedis(int id) {
		this.id = id;
	}
	
	public int getLastValue() {
		return lastValue;
	}
	
	public void setCurrentValue(int value) {
		this.lastValue = value;
	}
}
