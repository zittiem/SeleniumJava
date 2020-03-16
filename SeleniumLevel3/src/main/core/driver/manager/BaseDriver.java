package driver.manager;

import org.openqa.selenium.WebDriver;

import driver.setting.DriverProperty;

public abstract class BaseDriver {
	
	protected WebDriver webDriver;
	protected DriverProperty driverProperty;
	
	public BaseDriver(DriverProperty property) {
		this.driverProperty = property;
	}
	
	public abstract void createLocalDriver();
	
	public abstract void createRemoteDriver();
}
