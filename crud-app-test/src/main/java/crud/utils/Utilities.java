package crud.utils;

import java.text.DecimalFormat;
import java.util.Random;
import crud.models.Product;

public class Utilities {

	private static String[] prodNames = { "MyUniquePizza", "MyUniqueFalafel", "MyUniqueSushi" };
	public static final int MAX_QUANTITY = 32767;
	public static final double MAX_PRICE = 1000.0;
	private static DecimalFormat decimalFormat = new DecimalFormat("#.00");
	
	public static Product getRandomProduct() {		
		String prodName = prodNames[new Random().nextInt(prodNames.length)];
		int quantity = new Random().nextInt(MAX_QUANTITY);
		double price = Double.parseDouble(decimalFormat.format(Math.random() * MAX_PRICE));
		return new Product(prodName, quantity, price);
	}
}
