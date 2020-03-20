package driver.resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import driver.resource.base.BaseDriver;
import driver.setting.DriverProperty;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriver extends BaseDriver {

	protected ChromeOptions options;
	
	public ChromeDriver(DriverProperty property) {
		super(property);
		loadOptions();
	}
	
	private void loadOptions()
	{
		options = new ChromeOptions();
		if (driverProperty.getArguments() != null)
			options.addArguments(driverProperty.getArguments());
		if (driverProperty.getUserProfilePreference() != null)
			options.setExperimentalOption("prefs", driverProperty.getUserProfilePreference());
		options.merge(driverProperty.getCapabilities());
	}

	@Override
	public void createLocalDriver() {
		if (StringUtils.isNotBlank(driverProperty.getDriverExecutable()))
			System.setProperty("webdriver.chrome.driver", driverProperty.getDriverExecutable());
		else
			WebDriverManager.chromedriver().setup();
		
		webDriver = new org.openqa.selenium.chrome.ChromeDriver(options);
	}
	
	@Override
	public void createRemoteDriver() {
		webDriver = new RemoteWebDriver(driverProperty.getRemoteUrl(), options);
	}
}
