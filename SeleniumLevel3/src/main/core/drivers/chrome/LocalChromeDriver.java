package drivers.chrome;

import org.openqa.selenium.chrome.ChromeDriver;

import driver.setting.DriverProperty;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LocalChromeDriver extends BaseChromeDriver {

	public LocalChromeDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createDriver() {
		if (driverProperty.getDriverExecutable() != null && !driverProperty.getDriverExecutable().isEmpty())
			options.setBinary(driverProperty.getDriverExecutable());
		else
			WebDriverManager.chromedriver().setup();
		
		webDriver = new ChromeDriver(options);
	}
}
