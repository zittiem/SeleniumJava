package driver.selenium.chrome;

import org.openqa.selenium.chrome.ChromeDriver;
import driver.manager.BaseDriver;

public class LocalChromeDriver extends BaseDriver {

	@Override
	public void createWebDriver() {
		webDriver = new ChromeDriver();
	}
}
