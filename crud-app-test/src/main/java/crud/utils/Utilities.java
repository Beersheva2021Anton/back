package crud.utils;

import java.util.Random;
import crud.models.Product;

public class Utilities {

	private static String[] prodNames = { "MyUniquePizza", "MyUniqueFalafel", "MyUniqueSushi" };
	public static final int MAX_QUANTITY = 32767;
	public static final double MAX_PRICE = 32767.0;
	
	public static Product getRandomProduct() {		
		String prodName = prodNames[new Random().nextInt(prodNames.length)];
		int quantity = new Random().nextInt(MAX_QUANTITY);
		double price = new Random().nextDouble() * MAX_PRICE;
		return new Product(prodName, quantity, price);
	}
}
