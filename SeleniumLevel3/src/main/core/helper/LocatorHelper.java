package helper;

import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.javatuples.Pair;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import driver.manager.DriverManager;
import driver.setting.Platform;
import element.setting.FindBy;

public class LocatorHelper {
	private static Logger logger = Logger.getLogger(LocatorHelper.class);

	private JsonObject _jsonObject;
	private final String _defaultPlatform = Platform.ANY.toString().toLowerCase();
	private final String _defaultDriver = "default";
	private final String _fileExtension = ".json";

	public LocatorHelper(String locatorFolderPath, String fileName) {
		loadLocators(Paths.get(locatorFolderPath, fileName + _fileExtension).normalize().toString());
	}
	
	@SuppressWarnings("rawtypes")
	public LocatorHelper(String locatorFolderPath, Class clazz) {
		loadLocators(Paths.get(locatorFolderPath, clazz.getSimpleName() + _fileExtension).normalize().toString());
	}
	
	public LocatorHelper(String filePath) {
		loadLocators(filePath);
	}
	
	private void loadLocators(String filePath) {
		try {
			String jsonContent = Utilities.readFileFromFilePath(filePath);
			_jsonObject = new JsonParser().parse(jsonContent).getAsJsonObject();
		} catch (Exception e) {
			logger.error(String.format("Exception! - Error when load locators from path %s", filePath,
					e.getMessage()));
		}
	}
	
	private String getKey(String platform, String driver)
	{
		return String.format("%s.%s", platform, driver);
	}

	@SuppressWarnings("finally")
	public Pair<FindBy, String> getLocator(String elementName) {
		String currentPlatform;
		String currentDriver;
		FindBy locatorType = FindBy.xpath;
		String locatorValue = "";
		JsonElement content = null;
		try {
			currentPlatform = DriverManager.getDriverProperty().getPlatform().toString().toLowerCase();
			currentDriver = DriverManager.getDriverProperty().getDriverType().toString().toLowerCase();
			
			// Get locator from platform-driver key
			content = _jsonObject.get(getKey(currentPlatform, currentDriver));
			if (content != null)
			{
				content = content.getAsJsonObject().get(elementName);
			}
			
			// Get locator from any-driver key
			if (content == null)
			{
				content = _jsonObject.get(getKey(_defaultPlatform, currentDriver));
				if (content != null)
				{
					content = content.getAsJsonObject().get(elementName);
				}
			}
			
			// Get locator from platform-default key
			if (content == null)
			{
				content = _jsonObject.get(getKey(currentPlatform, _defaultDriver));
				if (content != null)
				{
					content = content.getAsJsonObject().get(elementName);
				}
			}
			
			// Get locator from any-default key
			if (content == null)
			{
				content = _jsonObject.get(getKey(_defaultPlatform, _defaultDriver));
				if (content != null)
				{
					content = content.getAsJsonObject().get(elementName);
				}
			}
			
			if (content == null)
				throw new Exception(String.format("'%s' key was not found for '%s'", elementName, getKey(currentPlatform, currentDriver)));

			locatorType = FindBy.fromString(getLocatorType(content.getAsString()));
			locatorValue = getLocatorValue(content.getAsString());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return new Pair<FindBy, String>(locatorType, locatorValue);
		}
	}
	
	public static String getLocatorType(String locator) {
		return locator.replaceAll("([\\w\\s]*)=.*", "$1").trim();
	}
	
	public static String getLocatorValue(String locator) {
		return locator.replaceAll("[\\w\\s]*=(.*)", "$1").trim();
	}
	
	public static Pair<FindBy, String> getPairLocator(String locator) {
		FindBy locatorType = FindBy.fromString(getLocatorType(locator));
		String locatorValue = getLocatorValue(locator);
		return new Pair<FindBy, String>(locatorType, locatorValue);
	}
}
