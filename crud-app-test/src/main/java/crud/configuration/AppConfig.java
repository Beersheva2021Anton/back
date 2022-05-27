package crud.configuration;

import java.io.*;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AppConfig {

	private static final String CONFIG_FILE = "config.properties";
	private String appUrl;
	private WebDriver webDriver;
	private static AppConfig configuration;

	private AppConfig() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(CONFIG_FILE));
			appUrl = properties.getProperty("appUrl");
			String browser = properties.getProperty("browserType").toLowerCase();
			initializeDriver(browser);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static AppConfig getConfiguration() {
		if (configuration != null) {
			return configuration;
		}
		return new AppConfig();
	}
	
	private void initializeDriver(String browser) {
		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			webDriver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			webDriver = new FirefoxDriver();
			break;
		case "opera":
			WebDriverManager.operadriver().setup();
			webDriver = new OperaDriver();
			break;
		default:
			throw new IllegalArgumentException("Unknown browser type: " + browser);
		}
	}

	public String appUrl() {
		return appUrl;
	}

	public WebDriver webDriver() {
		return webDriver;
	}
}
