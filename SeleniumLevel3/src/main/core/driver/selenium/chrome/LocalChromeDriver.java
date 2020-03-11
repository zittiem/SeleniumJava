package driver.selenium.chrome;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.logigear.driver.DriverProperty;
import com.logigear.driver.manager.BaseDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LocalChromeDriver extends BaseDriver {

	public LocalChromeDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createWebDriver() {
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions ops = new ChromeOptions();

		ops.addArguments(property.getArguments());
		ops.merge(property.getCapabilities());
		
		webDriver = new ChromeDriver(ops);
	}
}
