package crud.tests;

import static org.testng.Assert.*;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import crud.pages.ProductBasePage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CrudOperationsTests {

	private WebDriver driver;
	private ProductBasePage productBasePage;
	private final String appUrl = 
			"https://demos.telerik.com/aspnet-ajax/grid/examples/data-editing/manual-crud-operations/defaultcs.aspx";
	private final static int MAX_LATENCY_SEC = 10;
	
	@BeforeSuite
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(MAX_LATENCY_SEC));
		driver.get(appUrl);
		driver.findElement(By.linkText("Manual CRUD Operations"));
		productBasePage = new ProductBasePage(driver);
	}
	
	@Test
	public void addProductTest() {
		// TODO
	}
	
	@Test
	public void updateProductTest() {
		// TODO
	}
	
	@Test
	public void deleteProductTest() {
		// TODO
	}
	
	@AfterSuite
	public void tearDown() {
		driver.quit();
	}
}
