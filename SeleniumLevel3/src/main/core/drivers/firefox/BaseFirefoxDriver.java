package drivers.firefox;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import driver.manager.BaseDriver;
import driver.setting.DriverProperty;

public abstract class BaseFirefoxDriver extends BaseDriver {

	protected FirefoxOptions options;
	
	public BaseFirefoxDriver(DriverProperty property) {
		super(property);
		loadOptions();
	}

	private void loadOptions()
	{
		options = new FirefoxOptions();

		options.addArguments(driverProperty.getArguments());
		FirefoxProfile profile = new FirefoxProfile();
		driverProperty.getUserProfilePreference().forEach((key, value) -> profile.setPreference(key, value.toString()));
		options.setProfile(profile);
		options.merge(driverProperty.getCapabilities());
	}
}
