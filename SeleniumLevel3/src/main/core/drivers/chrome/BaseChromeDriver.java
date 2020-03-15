package drivers.chrome;

import org.openqa.selenium.chrome.ChromeOptions;

import driver.manager.BaseDriver;
import driver.setting.DriverProperty;

public abstract class BaseChromeDriver extends BaseDriver {

	protected ChromeOptions options;
	
	public BaseChromeDriver(DriverProperty property) {
		super(property);
		loadOptions();
	}

	private void loadOptions()
	{
		options = new ChromeOptions();

		options.addArguments(driverProperty.getArguments());
		options.setExperimentalOption("prefs", driverProperty.getUserProfilePreference());
		options.merge(driverProperty.getCapabilities());
	}
}
