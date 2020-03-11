package driver.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Driver extends DriverManager {
	private static Logger logger = Logger.getLogger(Driver.class);
	
	public static TargetLocator switchTo() {
		return getDriver().switchTo();
	}
	
	public static String getCurrentUrl() {
		return getDriver().getCurrentUrl();
	}
	
	public static WebElement findElement(By by) {
		return getDriver().findElement(by);
	}
	
	public static List<WebElement> findElements(By by) {
		return getDriver().findElements(by);
	}
	
	public static String getSessionId() {
		String sessionId = null;
		try {
			sessionId = ((RemoteWebDriver) getDriver()).getSessionId().toString();
		} catch (Exception ex)
		{
			logger.error("An error occurred when getting session Id " + ex.getMessage());
		}
		return sessionId;
	}

	public static void navigate(String url) {
		logger.debug("Navigate to " + url);
		try {
			getDriver().get(url);
		} catch (Exception e) {
			logger.error("An error occurred when nagivating " + e.getMessage());
		}
	}
	
	public static void maximizeBrowser() {
		try {
			logger.debug("Maximize browser");
			getDriver().manage().window().maximize();
		} catch (Exception e) {
			logger.error("An error occurred when maximizing browser" + e.getMessage());
		}
	}

	public static Object execJavaScript(String script, Object... objs) {
		return ((JavascriptExecutor) getDriver()).executeScript(script, objs);
	}
	
	public static void waitForAjaxJQueryProcess() {
		logger.debug("Wait for ajax complete");
		WebDriverWait wait = new WebDriverWait(getDriver(), getTimeOut());
		try {
			wait.until(new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					Boolean ajaxIsComplete = (Boolean) (execJavaScript("return Ext.Ajax.isLoading() == false;"));
					return ajaxIsComplete;
				}
			});
		} catch (Exception e) {
			logger.error("An error occurred when waitForAjaxJQueryProcess" + e.getMessage());
		}
	}

	public static void close() {
		try {
			logger.debug("Close browser");
			getDriver().close();

		} catch (Exception e) {
			logger.error("An error occurred when closing browser" + e.getMessage());
		}
	}
	
	public static void quit() {
		try {
			logger.debug("Quit browser");
			getDriver().quit();

		} catch (Exception e) {
			logger.error("An error occurred when quiting browser" + e.getMessage());
		}
	}
	
	public static WebDriver getWebDriver() {
		return getDriver();
	}
	
	public static String captureScreenshot(String filename, String filepath) {
		logger.info("Capture screenshot");
		String path = "";
		try {
			// Taking the screen using TakesScreenshot Class
			File objScreenCaptureFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

			// Storing the image in the local system.
			File dest = new File(
					System.getProperty("user.dir") + File.separator + filepath + File.separator + filename + ".png");
			FileUtils.copyFile(objScreenCaptureFile, dest);
			path = dest.getAbsolutePath();
		} catch (Exception e) {
			logger.error("An error occurred when capturing screen shot: " + e.getMessage());
		}
		return path;
	}

	public static void delay(double timeInSecond) {
		try {
			Thread.sleep((long)(timeInSecond * 1000));
		} catch (Exception e) {
			logger.error("An error occurred when delay: " + e.getMessage());
		}
	}

	public static List<String> getWindowHandles()
	{
		return new ArrayList<String>(getDriver().getWindowHandles());
	}
	
	public static String getWindowHandle()
	{
		return getDriver().getWindowHandle();
	}
	
	public static void switchTo(String windowHandle) {
		getDriver().switchTo().window(windowHandle);
	}
	
	public static void openNewTab() {
		execJavaScript("window.open('about:blank','_blank');");
	}
		
	public static String getRemoteCapability(String key) {
		return ((RemoteWebDriver)getDriver()).getCapabilities().getCapability(key).toString();
	}
}
