package drivers.androidnative;

import driver.manager.BaseDriver;
import driver.setting.DriverProperty;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class RemoteAndroidNativeDriver extends BaseDriver {

	public RemoteAndroidNativeDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createDriver() {
		webDriver = new AndroidDriver<MobileElement>(driverProperty.getRemoteUrl(), driverProperty.getCapabilities());
	}
}
