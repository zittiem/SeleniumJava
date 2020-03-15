package drivers.ie;

import org.openqa.selenium.ie.InternetExplorerOptions;

import driver.manager.BaseDriver;
import driver.setting.DriverProperty;

public abstract class BaseIEDriver extends BaseDriver {

	protected InternetExplorerOptions options;
	
	public BaseIEDriver(DriverProperty property) {
		super(property);
		loadOptions();
	}

	private void loadOptions()
	{
		options = new InternetExplorerOptions();

		options.addCommandSwitches(driverProperty.getArgumentsAsArray());
		options.merge(driverProperty.getCapabilities());
	}
}
