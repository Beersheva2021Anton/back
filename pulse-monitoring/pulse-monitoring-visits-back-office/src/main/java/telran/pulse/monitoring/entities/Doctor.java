package telran.pulse.monitoring.entities;
import javax.persistence.*;

@Entity
@Table(name="doctor")
public class Doctor {

	@Id
	String email;	
	String name;
	
	public Doctor() {}
	
	public Doctor(String email, String name) {
		this.email = email;
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}
}
