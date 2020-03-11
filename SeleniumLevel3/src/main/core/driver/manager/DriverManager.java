package driver.manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Throwables;
import com.logigear.driver.DriverProperty;
import com.logigear.driver.RunningMode;
import com.logigear.helper.BrowserSettingHelper;

public class DriverManager {
	private static Logger logger = Logger.getLogger(DriverManager.class);
	private static ThreadLocal<Map<String, BaseDriver>> DRIVER = new ThreadLocal<Map<String, BaseDriver>>();
	private static ThreadLocal<DriverProperty> tmpProperty = new ThreadLocal<>();
	private static String curKey = "default";

	private static boolean isKeyExist(String key) {
		return DRIVER.get().containsKey(key);
	}

	private static BaseDriver getBaseDriver() {
		if (!isKeyExist(curKey)) {
			logger.error(String.format("Driver with key '%s' is not found", curKey));
			return null;
		}

		return DRIVER.get().get(curKey);
	}

	protected static WebDriver getDriver() {

		if (!isKeyExist(curKey)) {
			logger.error(String.format("Driver with key '%s' is not found", curKey));
			return null;
		}
		// return the default WebDriver
		return getBaseDriver().webDriver;
	}

	public static DriverProperty getDriverProperty() {
		return getBaseDriver().property;
	}

	public static void config(String propertyFile, String platform, String sectionName, String testCaseName) {
		if (DRIVER.get() == null)
			DRIVER.set(new HashMap<String, BaseDriver>());

		DriverProperty property = BrowserSettingHelper.getDriverProperty(propertyFile, sectionName, testCaseName);
		property.setPlatform(platform);
		tmpProperty.set(property);
	}

	public static void initDriver(String key) {

		BaseDriver driver = DriverFactory.newInstance(tmpProperty.get());

		if (isKeyExist(key)) {
			try {
				Driver.getDriver().getTitle();
			} catch (NoSuchSessionException ex) {
				DRIVER.get().put(key, driver);
			}
		} else {
			DRIVER.get().put(key, driver);
		}
	}

	public static void initDriver() {
		initDriver("default");
	}

	public static void setWaitForAjax(boolean isWait) {
		getBaseDriver().setWaitForAjax(isWait);
	}

	public static boolean isWaitForAjax() {
		return getBaseDriver().isWaitForAjax;
	}

	public static void setTimeOut(int timeoutSec) {
		getBaseDriver().setTimeOut(timeoutSec);
		getDriver().manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
	}

	public static int getTimeOut() {
		return getBaseDriver().getTimeOut();
	}

	public static void setPageLoadTimeOut(int timeoutSec) {
		getBaseDriver().setPageLoadTimeOut(timeoutSec);
		getDriver().manage().timeouts().pageLoadTimeout(timeoutSec, TimeUnit.SECONDS);
	}

	public static int getPageLoadTimeOut() {
		return getBaseDriver().getPageLoadTimeOut();
	}

	public static void switchToDriver(String key) {
		curKey = key;
	}

	public static void switchToDefaultDriver() {
		curKey = "default";
	}

}

class DriverFactory {
	private static Logger logger = Logger.getLogger(DriverFactory.class);	
	
	private static final String BROWSER_PACKAGE_NAME = "com.logigear.driver.%s.%s";
	private static final String BROWSER_CLASS_NAME = "%s%sDriver";

	public static BaseDriver newInstance(DriverProperty property) {

		RunningMode mode = property.getMode();
		String packageName = String.format(BROWSER_PACKAGE_NAME, property.getProvider().toString().toLowerCase(),property.getDriverType().toString().toLowerCase());
		String className = String.format(BROWSER_CLASS_NAME, mode, property.getDriverType().toString());

		try {
			Method method;
			String fullClassName = packageName + "." + className;
			Class<?> clzz = Class.forName(fullClassName);
			Constructor<?> cons = clzz.getDeclaredConstructor(new Class[] { DriverProperty.class });
			Object obj = cons.newInstance(property);

			// Create Web Driver
			method = clzz.getDeclaredMethod("createWebDriver");
			method.invoke(obj);
			return (BaseDriver) obj;
		} catch (Exception e) {
			logger.error("Could not create new Driver instance. " + Throwables.getStackTraceAsString(e));
			return null;
		}
	}
}
