package drivers.ie;

import org.openqa.selenium.remote.RemoteWebDriver;

import driver.setting.DriverProperty;

public class RemoteIEDriver extends BaseIEDriver {

	public RemoteIEDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createDriver() {
		webDriver = new RemoteWebDriver(driverProperty.getRemoteUrl(), options);
	}
}
