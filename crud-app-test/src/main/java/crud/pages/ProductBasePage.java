package crud.pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import crud.models.Product;

public class ProductBasePage {

	private final static int MAX_DELAY_SEC = 10;
	private final static int POLLING_MILLIS = 500;
	private WebDriver driver;
	private Wait<WebDriver> commonWait;

	public ProductBasePage(WebDriver driver) {
		this.driver = driver;
		commonWait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(MAX_DELAY_SEC))
				.pollingEvery(Duration.ofMillis(POLLING_MILLIS));
	}

	private By addNewRecordButton = By.id("ctl00_ContentPlaceholder1_RadGrid1_ctl00_ctl02_ctl00_AddNewRecordButton");
	private By refreshButton = By.id("ctl00_ContentPlaceholder1_RadGrid1_ctl00_ctl02_ctl00_RefreshButton");
	private By lastPageButton = By.cssSelector("button[class='t-button rgActionButton rgPageLast']");
	private By recordsCounter = By
			.xpath("//*[@id=\"ctl00_ContentPlaceholder1_RadGrid1_ctl00\"]/tfoot/tr/td/div/div[5]/strong[1]");

	private By prodNameEdit = By.id("ctl00_ContentPlaceholder1_RadGrid1_ctl00_ctl02_ctl03_TB_ProductName");
	private By prodQuantityEdit = By.id("ctl00_ContentPlaceholder1_RadGrid1_ctl00_ctl02_ctl03_TB_UnitsInStock");
	private By prodPriceEdit = By.id("ctl00_ContentPlaceholder1_RadGrid1_ctl00_ctl02_ctl03_TB_UnitPrice");
	private By insertButton = By.id("ctl00_ContentPlaceholder1_RadGrid1_ctl00_ctl02_ctl03_PerformInsertButton");

	private By delConfirmButton = By.xpath("/html/body/main/form/div[1]/div[2]/div/div[2]/button[1]");

	private By spinner = By
			.id("ctl00_ContentPlaceholder1_RadAjaxLoadingPanel1ctl00_ContentPlaceholder1_RadWindowManager1");

	public int addProduct(Product product) {
		commonWait.until(ExpectedConditions.elementToBeClickable(addNewRecordButton))
			.click();
		fillMainForm(product);
		return getRecordsCount();
	}

	private void fillMainForm(Product product) {
		commonWait.until(ExpectedConditions.elementToBeClickable(prodNameEdit))
			.sendKeys(product.getProdName());
		commonWait.until(ExpectedConditions.elementToBeClickable(prodQuantityEdit))
			.sendKeys(product.getQuantity().toString());
		commonWait.until(ExpectedConditions.elementToBeClickable(prodPriceEdit))
			.sendKeys(product.getPrice().toString());
		commonWait.until(ExpectedConditions.elementToBeClickable(insertButton))
			.click();
		waitForTableActive();
	}

	private void fillCustomForm(Product product) {

	}

	private void waitForTableActive() {
		while (true) {
			try {
				commonWait.until(ExpectedConditions.presenceOfElementLocated(spinner));				
			} catch (NoSuchElementException e) {
				break;
			}
		}
	}

	public void updateProduct(int prodId, Product product) {
		
	}

	public int deleteProduct(int prodId) {
		return getRecordsCount();
	}

	public Product getProduct(int prodId) throws IllegalArgumentException {
		return null;
	}

	public void toLastPage() {
		commonWait.until(ExpectedConditions.visibilityOfElementLocated(lastPageButton))
			.click();
		waitForTableActive();
	}

	public void refreshPage() {
		commonWait.until(ExpectedConditions.visibilityOfElementLocated(refreshButton))
			.click();
		waitForTableActive();
	}
	
	public void closeAlertMessage() {
		commonWait.until(ExpectedConditions.elementToBeClickable(delConfirmButton));
		waitForTableActive();
	}

	public int getRecordsCount() {
		String strValue = commonWait.until(ExpectedConditions.visibilityOfElementLocated(recordsCounter))
			.getText();
		return Integer.parseInt(strValue);
	}
}
