package drivers.chrome;

import org.openqa.selenium.remote.RemoteWebDriver;

import driver.setting.DriverProperty;

public class RemoteChromeDriver extends BaseChromeDriver {

	public RemoteChromeDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createDriver() {
		webDriver = new RemoteWebDriver(driverProperty.getRemoteUrl(), options);
	}
}
