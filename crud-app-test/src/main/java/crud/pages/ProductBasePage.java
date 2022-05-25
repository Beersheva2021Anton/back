package crud.pages;

import org.openqa.selenium.*;
import crud.models.Product;

public class ProductBasePage {

	private WebDriver driver;
	
	public ProductBasePage(WebDriver driver) {
		this.driver = driver;
	}
	
	private By addNewRecordButton = By.linkText("Add new record");
	private By refreshButton = By.xpath("//*[@id=\"ctl00_ContentPlaceholder1_RadGrid1_ctl00_ctl02_ctl00_RefreshButton\"]");
	private By lastPageButton = By.cssSelector("button[class='t-button rgActionButton rgPageLast']");
	
	public int addProduct(Product product) {
		return 0;
	}
	
	private void fillForm(Product product) {
		
	}
	
	public int updateProduct(String prodName, Product product) {
		return 0;
	}
	
	public int deleteProduct(String prodName) {
		return 0;
	}
	
	public Product getProduct(String prodName) {
		return null;
	}
	
	private boolean toLastPage() {
		return false;
	}
}
