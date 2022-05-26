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
	
	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get(appUrl);
		productBasePage = new ProductBasePage(driver);	
	}
	
	@Test
	public void updateLongNameProductTest() {
		Product newProduct = Utilities.getRandomProduct();
		newProduct.setProdName("veeeeeeeeeeery looooooooooong product name");
		LOG.info("Update product with long name {}", newProduct);
		updateWithBadDataTest(newProduct);
	}
	
	@Test
	public void updateNegativeQuantityTest() {
		Product newProduct = Utilities.getRandomProduct();
		newProduct.setQuantity(-1);
		LOG.info("Update product with negative quantity {}", newProduct);
		updateWithBadDataTest(newProduct);
	}
	
	@Test
	public void updateEmptyPriceTest() {
		Product newProduct = Utilities.getRandomProduct();
		newProduct.setPrice(null);
		LOG.info("Update product with empty price {}", newProduct);
		updateWithBadDataTest(newProduct);
	}
	
	@Test
	public void updateBigPriceTest() {
		Product newProduct = Utilities.getRandomProduct();
		newProduct.setPrice(Utilities.MAX_PRICE + 1);
		LOG.info("Update product with too big price {}", newProduct);
		updateWithBadDataTest(newProduct);
	}
	
	private void updateWithBadDataTest(Product newProduct) {
		int recCount = productBasePage.getRecordsCount();
		Product oldProduct = productBasePage.getProduct(recCount);
		productBasePage.updateProduct(recCount, newProduct);
		
		productBasePage.closeAlertMessage();
		Product actualProduct = productBasePage.getProduct(recCount);
		assertEquals(actualProduct, oldProduct);
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
