package tests;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import driver.manager.DriverManager;
import driver.manager.DriverUtils;
import utils.common.Constants;

public class TestBase {

	@Parameters({ "driverConfig", "platform", "appName" })
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String driverConfig, String platform, String appName)
			throws Throwable {
		DriverManager.loadDriverProperty(Constants.DRIVER_SETTING_FILE, platform, driverConfig);
		DriverManager.initDriver();
		launchApp(appName);
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp(ITestResult result) {
		
		DriverUtils.quit();
	}
	
	public void launchApp(String appName) {
		DriverUtils.maximizeBrowser();
		switch(appName) {
		case "VietJet":
			DriverUtils.navigate(Constants.vietJetURL);
			break;
		case "Agoda":
			DriverUtils.navigate(Constants.agodaURL);
			break;
		case "LGEmail":
			DriverUtils.navigate(Constants.logigearEmailURL);
			break;
		}
	}
}

