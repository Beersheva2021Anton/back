package crud.tests;

import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.*;
import org.testng.annotations.*;
import crud.models.Product;
import crud.pages.ProductBasePage;
import crud.utils.Utilities;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CrudOperationsTests {

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
	public void addProductTest() {
		int recCount1 = productBasePage.getRecordsCount();
		Product product = Utilities.getRandomProduct();		
		LOG.info("Add product {}", product);
		int recCount2 = productBasePage.addProduct(product);
		
		assertEquals(recCount2, recCount1 + 1);
		productBasePage.refreshPage(); // to check product saved in DB (not only in UI)
		assertEquals(recCount2, recCount1 + 1);
	}
	
	@Test
	public void updateProductTest() {
		int recCount = productBasePage.getRecordsCount();
		Product newProduct = Utilities.getRandomProduct();
		LOG.info("Update product {}", newProduct);
		productBasePage.updateProduct(recCount, newProduct);
		
		Product actualProduct = productBasePage.getProduct(recCount);
		assertEquals(actualProduct, newProduct);
		productBasePage.refreshPage();
		actualProduct = productBasePage.getProduct(recCount);
		assertEquals(actualProduct, newProduct);
	}
	
	@Test
	public void deleteProductTest() {
		int recCount1 = productBasePage.getRecordsCount();		
		LOG.info("Delete product");
		int recCount2 = productBasePage.deleteProduct(recCount1);
		
		assertEquals(recCount2, recCount1 - 1);
		productBasePage.refreshPage();
		assertEquals(recCount2, recCount1 - 1);
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
