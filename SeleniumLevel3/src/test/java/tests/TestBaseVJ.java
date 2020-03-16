package tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import driver.manager.Driver;
import utils.common.Constants;

public class TestBaseVJ {

	@Parameters({ "browser", "platform" })
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		Driver.initDriver();
		launchVietJet();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp(ITestResult result) {
		Driver.quit();
	}
	
	public void launchVietJet() {
		Driver.setPageLoadTimeOut(Constants.LONG_TIME);
		Driver.maximizeBrowser();
		Driver.navigate(Constants.vietJetURL);
	}
}

