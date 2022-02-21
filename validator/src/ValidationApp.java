import java.time.*;

import telran.validation.Validator;

public class ValidationApp {

	public static void main(String[] args) {
		
		var address = new Address("Tel-Aviv", "", 12);
		var employee = new Employee(111222, "Vasya", "vasya@mail.co.il", LocalDate.of(2000, 10, 12), 
				LocalDate.of(2025, 2, 2), address);
		var res = Validator.validate(employee);
		res.forEach(err -> System.out.println(err));
	}
}
