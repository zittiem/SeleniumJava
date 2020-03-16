package tests;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import driver.manager.DriverUtils;
import utils.common.Constants;

public class TestBaseVJ {

	@Parameters({ "driverConfig", "platform" })
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String driverConfig, String platform)
			throws Throwable {
		DriverUtils.loadDriverProperty(Constants.DRIVER_SETTING_FILE, platform, driverConfig);
		DriverUtils.initDriver();
		launchVietJet();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp(ITestResult result) {
		DriverUtils.quitAll();
	}
	
	public void launchVietJet() {
		DriverUtils.maximizeBrowser();
		DriverUtils.navigate(Constants.vietJetURL);
	}
}

