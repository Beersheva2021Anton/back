package crud.tests;

import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import crud.models.Product;
import crud.pages.ProductBasePage;
import crud.utils.Utilities;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DiagnosticsTests {

	private final String appUrl = 
			"https://demos.telerik.com/aspnet-ajax/grid/examples/data-editing/manual-crud-operations/defaultcs.aspx";
	
	private WebDriver driver;
	private ProductBasePage productBasePage;	
	private Logger LOG = LoggerFactory.getLogger(CrudOperationsTests.class);
	
	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(appUrl);
		productBasePage = new ProductBasePage(driver);
		productBasePage.acceptCookies();
	}
	
	@Test
	public void updateLongNameProductTest() {
		Product newProduct = Utilities.getRandomProduct();
		newProduct.setProdName("veeeeeeeeeeery looooooooooong product name");
		LOG.info("Update product with long name {}", newProduct);
		updateWithBadDataTest(newProduct);
	}
	
	@Test
	public void updateBigQuantityTest() {
		Product newProduct = Utilities.getRandomProduct();
		newProduct.setQuantity(Utilities.MAX_QUANTITY + 1);
		LOG.info("Update product with too big quantity {}", newProduct);
		updateWithBadDataTest(newProduct);
	}
	
	@Test
	public void updateEmptyPriceTest() {
		Product newProduct = Utilities.getRandomProduct();
		newProduct.setQuantity(null);
		LOG.info("Update product with empty quantity {}", newProduct);
		updateWithBadDataTest(newProduct);
	}
	
	@Test
	public void updateBigPriceTest() {
		Product newProduct = Utilities.getRandomProduct();
		newProduct.setPrice(Utilities.MAX_PRICE + 1);
		LOG.info("Update product with too big price {}", newProduct);
		updateWithBadDataTest(newProduct);
	}
	
	@Test
	public void updateNegativePriceTest() {
		Product newProduct = Utilities.getRandomProduct();
		newProduct.setPrice(-1d);
		LOG.info("Update product with negative price {}", newProduct);
		updateWithBadDataTest(newProduct);
	}
	
	private void updateWithBadDataTest(Product newProduct) {
		int prodId = addRandomProduct();
		Product oldProduct = productBasePage.getProduct(prodId);
		productBasePage.updateProduct(prodId, newProduct);
		
		productBasePage.closeAlertMessage();
		Product actualProduct = productBasePage.getProduct(prodId);
		assertEquals(actualProduct, oldProduct);
	}
	
	private int addRandomProduct() {
		Product product = Utilities.getRandomProduct();		
		LOG.info("Add new product {}", product);
		return productBasePage.addProduct(product);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
