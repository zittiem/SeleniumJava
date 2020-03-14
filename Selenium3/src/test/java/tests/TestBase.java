package tests;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Parameters;

import com.logigear.driver.manager.Driver;

import utils.base.PageObjectHelper;
import utils.common.Constants;
import utils.config.ModuleFactory;

@Guice(modules = ModuleFactory.class)
public class TestBase {

	@Parameters({ "browser", "autoLogBug", "platform" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String browser, boolean autoLogBug, String platform, Method method, ITestContext context)
			throws Throwable {
		
		context.setAttribute("autoLogBug", autoLogBug);
		Driver.config(Constants.BROWSER_SETTING_FILE, platform, browser, method.getName());
		Driver.initDriver();
		Driver.setWaitForAjax(false);
		loadDriverConfig(browser);
		PageObjectHelper.loadPageObject(this);
	}

	
	@AfterMethod(alwaysRun = true)
	public void cleanUp(ITestResult result) {
		// Driver.quit(result.isSuccess());
		Driver.quit();
	}
	
	public void loadAndroidNativeAppConfig() {
		Driver.setTimeOut(Constants.SHORT_TIME);
	}

	public void loadWebBrowserConfig() {
		Driver.setPageLoadTimeOut(Constants.LONG_TIME);
		Driver.setTimeOut(Constants.SHORT_TIME);
		Driver.maximizeBrowser();
		Driver.navigate(Constants.URL);
	}

	public void loadDriverConfig(String browser) {
		if (browser.equals("android.native")) {
			loadAndroidNativeAppConfig();
		} else {
			loadWebBrowserConfig();
		}
	}
	
	public void launchTaylorMorrisonPage() {
		Driver.setPageLoadTimeOut(Constants.LONG_TIME);
		Driver.setTimeOut(Constants.SHORT_TIME);
		Driver.maximizeBrowser();
		Driver.navigate(Constants.URL);
	}
}

