package tests;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import driver.manager.DriverManager;
import driver.manager.DriverUtils;
import utils.constants.Constants;
import utils.helper.Logger;

public abstract class TestBase {
	protected String appName;
	protected String language;
	
	@Parameters({ "driverConfig", "platform", "appName", "language" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String driverConfig, String platform, String appName, String language)
			throws Throwable {
		this.appName = appName;
		this.language = language;
		DriverManager.loadDriverProperty(Constants.DRIVER_SETTING_FILE, platform, driverConfig);
		DriverManager.initDriver();
		prepareAppData();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp(ITestResult result) {
		Logger.info("Post Condition: Clean up.");
		DriverUtils.quit();
	}
	
	protected abstract void prepareAppData();
	
	protected abstract void launchApp();
}