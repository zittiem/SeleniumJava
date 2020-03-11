package driver.selenium.chrome;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.logigear.driver.DriverProperty;
import com.logigear.driver.manager.BaseDriver;

public class RemoteChromeDriver extends BaseDriver {

	public RemoteChromeDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createWebDriver() {
		ChromeOptions ops = new ChromeOptions();

		ops.addArguments(property.getArguments());
		ops.merge(property.getCapabilities());

		webDriver = new RemoteWebDriver(property.getRemoteUrl(), ops);
	}
}
