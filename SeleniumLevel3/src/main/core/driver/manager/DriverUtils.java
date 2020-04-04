package driver.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import driver.resource.base.BaseDriver;

import org.openqa.selenium.JavascriptExecutor;

public class DriverUtils {
	private static Logger logger = Logger.getLogger(DriverUtils.class);

	/* -------------------------- SURFACE -------------------------- */

	public static WebDriver getDriver() {
		return DriverManager.getDriver();
	}

	public static String getURL() {
		return getDriver().getCurrentUrl();
	}

	public static String getTitle() {
		return getDriver().getTitle();
	}

	public static String getPageSource() {
		return getDriver().getPageSource();
	}

	public static String getWindowHandle() {
		return getDriver().getWindowHandle();
	}

	public static List<String> getWindowHandles() {
		return new ArrayList<String>(getDriver().getWindowHandles());
	}

	public static WebElement findElement(By by) {
		return getDriver().findElement(by);
	}

	public static List<WebElement> findElements(By by) {
		return getDriver().findElements(by);
	}

	public static Object executeJavaScript(String script, Object... objs) {
		logger.debug("Execute javascript " + script);
		return ((JavascriptExecutor) getDriver()).executeScript(script, objs);
	}

	public static void navigate(String url) {
		logger.debug("Navigate to " + url);
		try {
			getDriver().get(url);
		} catch (Exception e) {
			logger.error("An error occurred when nagivating: " + e.getMessage());
		}
	}

	public static void switchToFrame(WebElement frameElement) {
		try {
			logger.debug("Switch frame");
			getDriver().switchTo().frame(frameElement);

		} catch (Exception e) {
			logger.error("An error occurred when switching frame by web element: " + e.getMessage());
		}
	}

	public static void switchToFrame(By by) {
		try {
			logger.debug("Switch frame");
			getDriver().switchTo().frame(findElement(by));

		} catch (Exception e) {
			logger.error("An error occurred when switching frame by web element: " + e.getMessage());
		}
	}

	public static void switchTo(String windowHandle) {
		try {
			logger.debug("Switch window");
			getDriver().switchTo().window(windowHandle);

		} catch (Exception e) {
			logger.error("An error occurred when switching window: " + e.getMessage());
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

	public static void close() {
		try {
			logger.debug("Close browser");
			int windowCount = getWindowHandles().size();
			getDriver().close();
			if (windowCount == 1) {
				DriverManager.removeDriver();
			}

		} catch (Exception e) {
			logger.error("An error occurred when closing browser:" + e.getMessage());
		}
	}

	public static void quit() {
		try {
			logger.debug("Quit browser");
			getDriver().quit();
			DriverManager.removeDriver();

		} catch (Exception e) {
			logger.error("An error occurred when quiting browser: " + e.getMessage());
		}
	}

	public static void quitAll() {
		try {
			logger.debug("Quit all browsers");
			for (Map.Entry<String, BaseDriver> item : DriverManager.getDriverMap().entrySet()) {
				item.getValue().getWebDriver().quit();
			}

		} catch (Exception e) {
			logger.error("An error occurred when quiting all browsers: " + e.getMessage());
		}
	}

	public static void wait(double timeInSecond) {
		try {
			Thread.sleep((long) (timeInSecond * 1000));
		} catch (Exception e) {
			logger.error(String.format("An error occurred when wait %s seconds: %s", timeInSecond, e.getMessage()));
		}
	}

	public static String takeScreenShot(String filename, String filepath) throws Exception {
		String path = "";
		try {
			// Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot = ((TakesScreenshot) getDriver());

			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

			// Move image file to new destination
			File DestFile = new File(filepath + File.separator + filename + ".png");

			// Copy file at destination
			FileUtils.copyFile(SrcFile, DestFile);
			path = DestFile.getAbsolutePath();
		} catch (Exception e) {
			logger.error("An error occurred when capturing screen shot: " + e.getMessage());
		}
		return path;
	}
}
