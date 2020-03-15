package drivers.ie;

import org.openqa.selenium.ie.InternetExplorerDriver;

import driver.setting.DriverProperty;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LocalIEDriver extends BaseIEDriver {

	public LocalIEDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createDriver() {
		if (driverProperty.getDriverExecutable() != null && !driverProperty.getDriverExecutable().isEmpty())
			System.setProperty("webdriver.ie.driver", driverProperty.getDriverExecutable());
		else
			WebDriverManager.iedriver().arch32().setup();
		
		webDriver = new InternetExplorerDriver(options);
	}
}
