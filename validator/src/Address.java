import telran.validation.constraint.*;

public class Address {
	
	public Address(String city, String street, int apartment) {
		this.city = city;
		this.street = street;
		this.apartment = apartment;
	}
	
	@NotEmpty
	String city;
	
	@NotNull
	String street;

	@Min(1)
	int apartment;
}
