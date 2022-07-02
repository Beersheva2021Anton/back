package telran.pulse.monitoring.entities;

import javax.persistence.*;

@Entity
@Table(name="patient")
public class Patient {

	@Id
	int id;
	String name;
	
	public Patient() {}

	public Patient(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
