import java.time.LocalDate;

import telran.validation.constraint.*;

public class Employee {
	
	public Employee(long id, String name, String email, LocalDate birthDate, LocalDate jobFinishDate, 
			Address address) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.jobFinishDate = jobFinishDate;
		this.address = address;
	}
	
	@Max(999999)
	@Min(100000)
	long id;
	
	@NotEmpty
	String name;
	
	@Email
	String email;
	
	@Past
	LocalDate birthDate;
	
	@Future
	LocalDate jobFinishDate;
	
	@Valid
	Address address;
}
