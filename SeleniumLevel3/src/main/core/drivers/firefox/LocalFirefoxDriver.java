package drivers.firefox;

import org.openqa.selenium.firefox.FirefoxDriver;

import driver.setting.DriverProperty;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LocalFirefoxDriver extends BaseFirefoxDriver {

	public LocalFirefoxDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createDriver() {
		if (driverProperty.getDriverExecutable() != null && !driverProperty.getDriverExecutable().isEmpty())
			options.setBinary(driverProperty.getDriverExecutable());
		else
			WebDriverManager.firefoxdriver().setup();
		
		webDriver = new FirefoxDriver(options);
	}
}
