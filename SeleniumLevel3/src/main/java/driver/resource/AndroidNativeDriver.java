package driver.resource;

import driver.resource.base.BaseDriver;
import driver.setting.DriverProperty;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AndroidNativeDriver extends BaseDriver {

	public AndroidNativeDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createLocalDriver() {
		webDriver = new AndroidDriver<MobileElement>(driverProperty.getCapabilities());
	}
	
	@Override
	public void createRemoteDriver() {
		webDriver = new AndroidDriver<MobileElement>(driverProperty.getRemoteUrl(), driverProperty.getCapabilities());
	}
}
