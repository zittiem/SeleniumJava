package driver.resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import driver.resource.base.BaseDriver;
import driver.setting.DriverProperty;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FirefoxDriver extends BaseDriver {

	private FirefoxOptions options;
	
	public FirefoxDriver(DriverProperty property) {
		super(property);
		loadOptions();
	}
	
	private void loadOptions()
	{
		options = new FirefoxOptions();
		if (driverProperty.getArguments() != null)
			options.addArguments(driverProperty.getArguments());
		if (driverProperty.getUserProfilePreference() != null)
		{
			FirefoxProfile profile = new FirefoxProfile();
			driverProperty.getUserProfilePreference().forEach((key, value) -> profile.setPreference(key, value.toString()));
			options.setProfile(profile);
		}
		options.merge(driverProperty.getCapabilities());
	}

	@Override
	public void createLocalDriver() {
		if (StringUtils.isNotBlank(driverProperty.getDriverExecutable()))
			System.setProperty("webdriver.gecko.driver", driverProperty.getDriverExecutable());
		else
			WebDriverManager.firefoxdriver().setup();
		
		webDriver = new org.openqa.selenium.firefox.FirefoxDriver(options);
	}

	@Override
	public void createRemoteDriver() {
		webDriver = new RemoteWebDriver(driverProperty.getRemoteUrl(), options);
	}
}
