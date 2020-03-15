package driver.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import driver.setting.DriverProperty;
import helper.DriverSettingHelper;

public class DriverUtils {
	private static Logger logger = Logger.getLogger(DriverUtils.class);
	private static ThreadLocal<Map<String, BaseDriver>> DRIVER = new ThreadLocal<Map<String, BaseDriver>>();
	private static ThreadLocal<List<String>> KEYS = new ThreadLocal<List<String>>();
	private static ThreadLocal<String> DEFAULT_KEY = new ThreadLocal<String>();
	private static ThreadLocal<String> CURRENT_KEY = new ThreadLocal<String>();
	private static ThreadLocal<DriverProperty> CACHE_DIVER_PROPERTY = new ThreadLocal<DriverProperty>();
	
	private static boolean isKeyExists(String key)
	{
		return DRIVER.get().containsKey(key);
	}
	
	private static BaseDriver getBaseDriver() {
		if (!isKeyExists(CURRENT_KEY.get())) {
			logger.error(String.format("Driver with key '%s' is not found", CURRENT_KEY.get()));
			return null;
		}

		return DRIVER.get().get(CURRENT_KEY.get());
	}

	protected static WebDriver getDriver() {
		return getBaseDriver().webDriver;
	}

	public static DriverProperty getDriverProperty() {
		return getBaseDriver().driverProperty;
	}

	public static void loadDriverProperty(String propertyFile, String platform, String sectionName) throws Exception {
		DriverProperty property = DriverSettingHelper.getDriverProperty(propertyFile, sectionName);
		property.setPlatform(platform);
		CACHE_DIVER_PROPERTY.set(property);
	}
	
	public static void initDriver() {
		String key = String.format("%s-%d", CACHE_DIVER_PROPERTY.get().getDriverType().toString(), 1);
		createDriver(key, CACHE_DIVER_PROPERTY.get());
		DEFAULT_KEY.set(key);
	}

	public static void createDriver() {
		String key = String.format("%s-%d", CACHE_DIVER_PROPERTY.get().getDriverType().toString(), DRIVER.get().size() + 1);
		createDriver(key, CACHE_DIVER_PROPERTY.get());
	}
	
	public static void createDriver(String key) {
		createDriver(key, CACHE_DIVER_PROPERTY.get());
	}
	
	public static void createDriver(DriverProperty property) {
		String key = String.format("%s-%d", property.getDriverType().toString(), DRIVER.get().size() + 1);
		createDriver(key, property);
	}
	
	public static void createDriver(String key, DriverProperty property) {
		if (DRIVER.get() == null)
			DRIVER.set(new HashMap<String, BaseDriver>());
		
		BaseDriver driver = DriverFactory.newInstance(property);
		driver.webDriver.manage().timeouts().implicitlyWait(property.getTimeOut(), TimeUnit.SECONDS);
		DRIVER.get().put(key, driver);
		CURRENT_KEY.set(key);
		KEYS.get().add(key);
	}

	public static void switchToDriver(String key) {
		CURRENT_KEY.set(key);
	}

	public static void switchToDefaultDriver() {
		CURRENT_KEY.set(DEFAULT_KEY.get());
	}
	
	private static void removeDriver()
	{
		DRIVER.get().remove(CURRENT_KEY.get());
		KEYS.get().remove(CURRENT_KEY.get());
		int size = KEYS.get().size();
		if (size > 0)
		{
			CURRENT_KEY.set(KEYS.get().get(size - 1));
			if (!isKeyExists(DEFAULT_KEY.get()))
			{
				DEFAULT_KEY.set(KEYS.get().get(0));
			}
		}
	}
	
	/* -------------------------- SURFACE -------------------------- */
	
	public static void navigate(String url) {
		logger.debug("Navigate to " + url);
		try {
			getDriver().get(url);
		} catch (Exception e) {
			logger.error("An error occurred when nagivating: " + e.getMessage());
		}
	}
	
	public static String getPageSource() {
		return getDriver().getPageSource();
	}
	
	public static String getTitle(){
		return getDriver().getTitle();
	}
	
	public static String getWindowHandle() {
		return getDriver().getWindowHandle();
	}
	
	public static ArrayList<String> getWindowHandles() {
		return new ArrayList<String>(getDriver().getWindowHandles());
	}
	
	public static WebElement findElement(By by) {
		return getDriver().findElement(by);
	}
	
	public static List<WebElement> findElements(By by) {
		return getDriver().findElements(by);
	}
	
	public static void switchToFrame(WebElement frameElement) {
		try {
			logger.debug("Switch frame using web element");
			getDriver().switchTo().frame(frameElement);

		} catch (Exception e) {
			logger.error("An error occurred when switching frame by web element: " + e.getMessage());
		}
	}
	
	public static void switchToFrame(int frameIndex) {
		try {
			logger.debug("Switch frame using frame index");
			getDriver().switchTo().frame(frameIndex);

		} catch (Exception e) {
			logger.error("An error occurred when switching frame by index: " + e.getMessage());
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
			if (windowCount == 1)
			{
				removeDriver();
			}

		} catch (Exception e) {
			logger.error("An error occurred when closing browser:" + e.getMessage());
		}
	}
	
	public static void quit() {
		try {
			logger.debug("Quit browser");
			getDriver().quit();
			removeDriver();

		} catch (Exception e) {
			logger.error("An error occurred when quiting browser: " + e.getMessage());
		}
	}
	
	public static void quitAll() {
		try {
			logger.debug("Quit all browsers");
			for (Map.Entry<String, BaseDriver> item : DRIVER.get().entrySet())
			{
				item.getValue().webDriver.quit();
			}

		} catch (Exception e) {
			logger.error("An error occurred when quiting browser: " + e.getMessage());
		}
	}
	
	public static String takeScreenShot(String filename, String filepath) throws Exception{
		String path = "";
		try {	
			//Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot = ((TakesScreenshot) getDriver());
	
			//Call getScreenshotAs method to create image file
			File SrcFile= scrShot.getScreenshotAs(OutputType.FILE);
	
			//Move image file to new destination
			File DestFile = new File(
				System.getProperty("user.dir") + File.separator + filepath + File.separator + filename + ".png");
	
			//Copy file at destination
			FileUtils.copyFile(SrcFile, DestFile);
			path = DestFile.getAbsolutePath();
		} catch (Exception e) {
			logger.error("An error occurred when capturing screen shot: " + e.getMessage());
		}
	return path;
	}

}
