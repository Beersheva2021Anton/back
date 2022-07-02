package telran.pulse.monitoring.entities;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class SensorListRedis {

	@Id
	int id;
	
	ArrayList<Integer> values = new ArrayList<>();
	
	public ArrayList<Integer> getValues() {
		return values;
	}

	public SensorListRedis(int id) {
		this.id = id;
	}
}
