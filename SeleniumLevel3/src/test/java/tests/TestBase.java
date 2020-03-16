package tests;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import driver.manager.DriverManager;
import driver.manager.DriverUtils;
import driver.setting.DriverType;
import utils.common.Constants;

public class TestBase {

	@Parameters({ "driverConfig", "platform" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String driverConfig, String platform, Method method, ITestContext context)
			throws Throwable {
		
		DriverManager.loadDriverProperty(Constants.DRIVER_SETTING_FILE, platform, driverConfig);
		DriverManager.initDriver();
		loadDriverConfig(DriverManager.getDriverProperty().getDriverType());
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp(ITestResult result) {
		DriverUtils.quitAll();
	}
	
	public void loadWebBrowserConfig() {
		DriverUtils.maximizeBrowser();
		DriverUtils.navigate(Constants.URL);
	}

	public void loadDriverConfig(DriverType driver) {
		if (driver != DriverType.AndroidNative)
			loadWebBrowserConfig();
	}
}

