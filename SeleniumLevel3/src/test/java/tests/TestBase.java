package tests;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import datatype.VietJet.LanguageType;
import driver.manager.DriverManager;
import driver.manager.DriverUtils;
import utils.constants.Constants;
import utils.helper.Logger;

public class TestBase {

	@Parameters({ "driverConfig", "platform" })
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String driverConfig, String platform)
			throws Throwable {
		DriverManager.loadDriverProperty(Constants.DRIVER_SETTING_FILE, platform, driverConfig);
		DriverManager.initDriver();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp(ITestResult result) {
		Logger.info("Post Condition: Clean up.");
		//DriverUtils.quit();
	}
	
	public void launchApp(String appName, LanguageType lang) {
		DriverUtils.maximizeBrowser();
		appName = appName + lang.getCode();
		
		switch(appName) {
		case "VietJetUS":
			DriverUtils.navigate(Constants.vietJetEN_URL);
			break;
		case "VietJetVI":
			DriverUtils.navigate(Constants.vietJetVI_URL);
			break;
		case "AgodaUS":
			DriverUtils.navigate(Constants.agoda_URL);
			break;
		case "LGEmailUS":
			DriverUtils.navigate(Constants.logigearEmail_URL);
			break;
		}
	}
}