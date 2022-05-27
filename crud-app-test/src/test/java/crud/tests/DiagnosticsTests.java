package crud.tests;

import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.slf4j.*;
import org.testng.annotations.*;
import crud.configuration.AppConfig;
import crud.models.Product;
import crud.pages.ProductBasePage;
import crud.utils.Utilities;

public class DiagnosticsTests {
	
	private WebDriver driver;
	private ProductBasePage productBasePage;	
	private Logger LOG = LoggerFactory.getLogger(CrudOperationsTests.class);
	
	@BeforeMethod
	public void setUp() {
		AppConfig config = AppConfig.getConfiguration();
		driver = config.webDriver();
		driver.manage().window().maximize();
		driver.get(config.appUrl());
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
		
		productBasePage.closeAlertMessage(); // alert is always expected
		Product actualProduct = productBasePage.getProduct(prodId);
		assertEquals(actualProduct, oldProduct); // check data not changed after alert
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
