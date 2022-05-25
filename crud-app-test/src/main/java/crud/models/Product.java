package crud.models;

public class Product {

	private String prodName;
	private Integer quantity;
	private Double price;
	
	public Product(String prodName, Integer quantity, Double price) {
		this.prodName = prodName;
		this.quantity = quantity;
		this.price = price;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProdName() {
		return prodName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Double getPrice() {
		return price;
	}
}
