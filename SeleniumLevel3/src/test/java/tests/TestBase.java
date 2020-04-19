package tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import driver.manager.DriverManager;
import driver.manager.DriverUtils;
import utils.constant.Constants;
import utils.helper.DataHelper;
import utils.helper.ResourceHelper;
import utils.helper.Logger;
import utils.helper.ResourceHelper.SharedData;

public class TestBase {
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
		launchApp();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp(ITestResult result) {
		Logger.info("Post Condition: Clean up.");
		DriverUtils.quitAll();
	}
	
	private void prepareAppData() {
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "SharedData");
		SharedData shared = dataHelper.getDataObject(SharedData.class, this.language);
		shared.appName = this.appName;
		shared.language = this.language;
		ResourceHelper.SHARED_DATA.set(shared);
	}
	
	protected void launchApp() {
		DriverUtils.maximizeBrowser();
		DriverUtils.navigate(ResourceHelper.SHARED_DATA.get().url);
	}
}