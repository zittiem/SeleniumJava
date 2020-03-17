package driver.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import driver.setting.DriverProperty;
import helper.DriverSettingHelper;

public class DriverManager {
	private static Logger logger = Logger.getLogger(DriverManager.class);
	private static ThreadLocal<Map<String, BaseDriver>> DRIVER = new ThreadLocal<Map<String, BaseDriver>>();
	private static ThreadLocal<List<String>> KEYS = new ThreadLocal<List<String>>();
	private static ThreadLocal<String> DEFAULT_KEY = new ThreadLocal<String>();
	private static ThreadLocal<String> CURRENT_KEY = new ThreadLocal<String>();
	private static ThreadLocal<DriverProperty> CACHE_DIVER_PROPERTY = new ThreadLocal<DriverProperty>();
	
	
	private static BaseDriver getBaseDriver() {
		if (!DRIVER.get().containsKey(CURRENT_KEY.get())) {
			logger.error(String.format("Driver with key '%s' is not found", CURRENT_KEY.get()));
			return null;
		}

		return DRIVER.get().get(CURRENT_KEY.get());
	}
	
	private static String generateDriverKey(String prefix)
	{
		String key;
		int number = 1;
		while(true)
		{
			key = prefix + "-" + number;
			if (!DRIVER.get().containsKey(key))
				return key;
			number++;
		}
	}
	
	protected static Map<String, BaseDriver> getDriverMap() {
		return DRIVER.get();
	}
	
	protected static void removeDriver()
	{
		DRIVER.get().remove(CURRENT_KEY.get());
		KEYS.get().remove(CURRENT_KEY.get());
		int size = KEYS.get().size();
		if (size > 0)
		{
			CURRENT_KEY.set(KEYS.get().get(size - 1));
			if (!DRIVER.get().containsKey(DEFAULT_KEY.get()))
			{
				DEFAULT_KEY.set(KEYS.get().get(0));
			}
		}
	}
	
	public static String getCurrentDriverKey() {
		return CURRENT_KEY.get();
	}
	
	public static String getDefaultDriverKey() {
		return DEFAULT_KEY.get();
	}
	
	public static WebDriver getDriver() {
		return getBaseDriver().webDriver;
	}

	public static DriverProperty getDriverProperty() {
		return getBaseDriver().driverProperty;
	}
	
	public static DriverProperty getDriverProperty(String key) {
		return DRIVER.get().get(key).driverProperty;
	}

	public static void loadDriverProperty(String propertyFile, String platform, String sectionName) throws Exception {
		DriverProperty property = DriverSettingHelper.getDriverProperty(propertyFile, sectionName);
		property.setPlatform(platform);
		CACHE_DIVER_PROPERTY.set(property);
	}
	
	public static void initDriver() {
		if (DRIVER.get() == null)
		{
			DRIVER.set(new HashMap<String, BaseDriver>());
			KEYS.set(new ArrayList<String>());
		}
		
		String key = generateDriverKey(CACHE_DIVER_PROPERTY.get().getDriverType().toString());
		createDriver(key, CACHE_DIVER_PROPERTY.get());
		DEFAULT_KEY.set(key);
	}

	public static void createDriver() {
		String key = generateDriverKey(CACHE_DIVER_PROPERTY.get().getDriverType().toString());
		createDriver(key, CACHE_DIVER_PROPERTY.get());
	}
	
	public static void createDriver(String key) {
		createDriver(key, CACHE_DIVER_PROPERTY.get());
	}
	
	public static void createDriver(DriverProperty property) {
		String key = generateDriverKey(property.getDriverType().toString());
		createDriver(key, property);
	}
	
	public static void createDriver(String key, DriverProperty property) {
		BaseDriver driver = DriverFactory.newInstance(property);
		driver.webDriver.manage().timeouts().pageLoadTimeout(property.getPageTimeOut(), TimeUnit.SECONDS);
		driver.webDriver.manage().timeouts().implicitlyWait(property.getElementTimeOut(), TimeUnit.SECONDS);
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
}
