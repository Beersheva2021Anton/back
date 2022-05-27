package crud.pages;

import java.time.Duration;
import java.util.List;
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

	private By acceptCookiesButton = By.xpath("//*[@id='onetrust-accept-btn-handler']");
	private By addNewRecordButton = By.id("ctl00_ContentPlaceholder1_RadGrid1_ctl00_ctl02_ctl00_AddNewRecordButton");
	private By refreshButton = By.id("ctl00_ContentPlaceholder1_RadGrid1_ctl00_ctl02_ctl00_RefreshButton");
	private By lastPageButton = By.cssSelector("button[class='t-button rgActionButton rgPageLast']");
	private By recordsCounter = By.xpath("//div[@class='NextPrevAndNumeric']/div[5]/strong[1]");

	private By prodNameEdit = By.xpath("//div[@class='rgEditForm']/table/tbody/tr[1]/td[2]/input");
	private By prodQuantityEdit = By.xpath("//div[@class='rgEditForm']/table/tbody/tr[2]/td[2]/input");
	private By prodPriceEdit = By.xpath("//div[@class='rgEditForm']/table/tbody/tr[3]/td[2]/input");
	private By insertButton = By.xpath("//button[@class='t-button rgActionButton rgUpdate']");

	private By delConfirmButton = By.xpath("//div[@class='rwDialog rwConfirmDialog']/div[2]/button[1]");
	private By alertConfirmButton = By.xpath("//div[@class='rwDialog rwAlertDialog']/div[2]/button[1]");
	
	private By spinner = By.id(
			"ctl00_ContentPlaceholder1_RadAjaxLoadingPanel1ctl00_ContentPlaceholder1_RadWindowManager1");

	private By tableRow = By.xpath("//table[@class='rgMasterTable rfdOptionList']/tbody/tr");
	private By hideNinjaButton = By.id("collapse-btn");
	
	public void acceptCookies() {
		while (true) {
			try {
				commonWait.until(ExpectedConditions.visibilityOfElementLocated(acceptCookiesButton))
					.click();
				break;
			} catch(ElementClickInterceptedException e) {
				// do nothing
			}
		}		
	}
	
	public int addProduct(Product product) {
		commonWait.until(ExpectedConditions.elementToBeClickable(addNewRecordButton))
			.click();
		fillProductForm(product);
		return getLastProdId();
	}

	private void fillProductForm(Product product) {
		WebElement nameEdit = commonWait.until(ExpectedConditions.elementToBeClickable(prodNameEdit));
		nameEdit.clear(); // for update
		nameEdit.sendKeys(product.getProdName());
		WebElement quantityEdit = commonWait.until(ExpectedConditions.elementToBeClickable(prodQuantityEdit));
		quantityEdit.clear();
		if (product.getQuantity() != null) {
			quantityEdit.sendKeys(product.getQuantity().toString());
		}		
		WebElement priceEdit = commonWait.until(ExpectedConditions.elementToBeClickable(prodPriceEdit));
		priceEdit.clear();
		if (product.getPrice() != null) {
			priceEdit.sendKeys(product.getPrice().toString());
		}		
		commonWait.until(ExpectedConditions.elementToBeClickable(insertButton))
			.click();
		waitForTableActive();
	}	

	public void updateProduct(int prodId, Product product) {
		toLastPage(); // assumption, that we work with last added products
		String rowId = getRowIdByProdId(prodId);
		commonWait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//*[@id='" + rowId + "']/td[1]/button")))
			.click();
		fillProductForm(product);
	}

	public void deleteProduct(int prodId) {
		toLastPage();
		String rowId = getRowIdByProdId(prodId);
		hideNinjaBanner();
		commonWait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//*[@id='" + rowId + "']/td[6]/button")))
			.click();
		confirmDeletion();
	}

	public Product getProduct(int prodId) {
		toLastPage();
		String rowId = getRowIdByProdId(prodId);
		String nameStr = commonWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//*[@id='" + rowId + "']/td[3]")))
			.getText();
		String quantityStr = commonWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//*[@id='" + rowId + "']/td[4]")))
			.getText();
		String priceStr = commonWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//*[@id='" + rowId + "']/td[5]")))
			.getText().replaceAll("[(,$)]", "");
		return new Product(nameStr, Integer.parseInt(quantityStr), Double.parseDouble(priceStr));
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

	public void toLastPage() {
		commonWait.until(ExpectedConditions.elementToBeClickable(lastPageButton))
			.click();
		waitForTableActive();
	}

	public void refreshPage() {
		hideNinjaBanner();	
		commonWait.until(ExpectedConditions.elementToBeClickable(refreshButton))
			.click();
		waitForTableActive();
	}
	
	private void confirmDeletion() {
		hideNinjaBanner();
		commonWait.until(ExpectedConditions.elementToBeClickable(delConfirmButton))
			.click();
		waitForTableActive();
	}
	
	public void closeAlertMessage() {
		hideNinjaBanner();
		commonWait.until(ExpectedConditions.elementToBeClickable(alertConfirmButton))
			.click();
	}

	public int getRecordsCount() {
		String strValue = commonWait.until(ExpectedConditions.visibilityOfElementLocated(recordsCounter))
			.getText();
		return Integer.parseInt(strValue);
	}
	
	private int getLastProdId() {
		toLastPage();
		List<WebElement> rows = 
				commonWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRow));
		String strValue = 
				commonWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//*[@id='ctl00_ContentPlaceholder1_RadGrid1_ctl00__" + (rows.size() - 1) + "']/td[2]")))
				.getText();
		return Integer.parseInt(strValue);
	}
	
	private String getRowIdByProdId(int prodId) {
		List<WebElement> rows = 
				commonWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRow));
		for (WebElement row : rows) {
			String strValue = row.findElement(By.xpath(".//td[2]"))
				.getText();
			if (Integer.parseInt(strValue) == prodId) {
				return row.getAttribute("id");
			}
		}
		throw new NoSuchElementException("No record with id: " + prodId);
	}
	
	private void hideNinjaBanner() {
		try {
			commonWait.until(ExpectedConditions.presenceOfElementLocated(hideNinjaButton))
				.click();
		} catch (NoSuchElementException e) {
			// do nothing
		}	
	}
}
