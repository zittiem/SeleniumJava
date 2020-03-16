package driver.resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import driver.manager.BaseDriver;
import driver.setting.DriverProperty;
import io.github.bonigarcia.wdm.WebDriverManager;

public class IEDriver extends BaseDriver {

	protected InternetExplorerOptions options;
	
	public IEDriver(DriverProperty property) {
		super(property);
		loadOptions();
	}

	private void loadOptions()
	{
		options = new InternetExplorerOptions();
		if (driverProperty.getArguments() != null)
			options.addCommandSwitches(driverProperty.getArgumentsAsArray());
		options.merge(driverProperty.getCapabilities());
	}

	@Override
	public void createLocalDriver() {
		if (StringUtils.isNotBlank(driverProperty.getDriverExecutable()))
			System.setProperty("webdriver.ie.driver", driverProperty.getDriverExecutable());
		else
			WebDriverManager.iedriver().arch32().setup();
		
		webDriver = new InternetExplorerDriver(options);
	}

	@Override
	public void createRemoteDriver() {
		webDriver = new RemoteWebDriver(driverProperty.getRemoteUrl(), options);
	}
}
