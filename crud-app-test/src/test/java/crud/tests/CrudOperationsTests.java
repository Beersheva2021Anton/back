package crud.tests;

import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.slf4j.*;
import org.testng.annotations.*;
import crud.configuration.AppConfig;
import crud.models.Product;
import crud.pages.ProductBasePage;
import crud.utils.Utilities;

public class CrudOperationsTests {

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
	public void addProductTest() {
		int recCount1 = productBasePage.getRecordsCount();
		addRandomProduct();
		int recCount2 = productBasePage.getRecordsCount();
		
		assertEquals(recCount2, recCount1 + 1);
		productBasePage.refreshPage(); // to check product saved in DB (not only in UI)
		assertEquals(recCount2, recCount1 + 1);
	}
	
	@Test
	public void updateProductTest() {
		int prodId = addRandomProduct();
		Product newProduct = Utilities.getRandomProduct();
		LOG.info("Update product with id {} and data {}", prodId, newProduct);
		productBasePage.updateProduct(prodId, newProduct);
		
		Product actualProduct = productBasePage.getProduct(prodId);
		assertEquals(actualProduct, newProduct);
		productBasePage.refreshPage();
		actualProduct = productBasePage.getProduct(prodId);
		assertEquals(actualProduct, newProduct);
	}
	
	@Test
	public void deleteProductTest() {
		int prodId = addRandomProduct();
		int recCount1 = productBasePage.getRecordsCount();		
		LOG.info("Delete product with id {}", prodId);
		productBasePage.deleteProduct(prodId);
		int recCount2 = productBasePage.getRecordsCount();
		
		assertEquals(recCount2, recCount1 - 1);
		productBasePage.refreshPage();
		assertEquals(recCount2, recCount1 - 1);
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
