package drivers.firefox;

import org.openqa.selenium.remote.RemoteWebDriver;

import driver.setting.DriverProperty;
	
public class RemoteFirefoxDriver extends BaseFirefoxDriver {

	public RemoteFirefoxDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createDriver() {
		webDriver = new RemoteWebDriver(driverProperty.getRemoteUrl(), options);
	}
}
