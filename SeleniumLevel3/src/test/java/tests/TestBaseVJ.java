package tests;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import driver.manager.DriverManager;
import driver.manager.DriverUtils;
import utils.common.Constants;

public class TestBaseVJ {

	@Parameters({ "driverConfig", "platform", "url" })
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String driverConfig, String platform, String url)
			throws Throwable {
		DriverManager.loadDriverProperty(Constants.DRIVER_SETTING_FILE, platform, driverConfig);
		DriverManager.initDriver();
		launchVietJet(url);
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp(ITestResult result) {
		DriverUtils.quitAll();
	}
	
	public void launchVietJet(String url) {
		DriverUtils.maximizeBrowser();
		DriverUtils.navigate(url);
	}
}

