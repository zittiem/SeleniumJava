package utils.helper;

import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import driver.manager.DriverManager;
import driver.setting.Platform;
import helper.JsonHelper;
import helper.Utilities;

public class DataHelper extends JsonHelper {
	private static Logger logger = Logger.getLogger(DataHelper.class);
	private JsonObject _jsonObject;
	private final String _defaultPlatform = Platform.ANY.toString().toLowerCase();
	private final String _defaultDriver = "default";
	private final String _fileExtension = ".json";

	public DataHelper(String locatorFolderPath, String fileName) {
		loadData(Paths.get(locatorFolderPath, fileName + _fileExtension).normalize().toString());
	}

	public DataHelper(String filePath) {
		loadData(filePath);
	}

	private void loadData(String filePath) {
		try {
			String jsonContent = Utilities.readFileFromFilePath(filePath);
			_jsonObject = new JsonParser().parse(jsonContent).getAsJsonObject();
		} catch (Exception e) {
			logger.error(String.format("Exception! - Error when load Data from path %s", filePath, e.getMessage()));
		}
	}

	private String getKey(String platform, String driver) {
		return String.format("%s.%s", platform, driver);
	}

	public static <T> T convertJsonToObject(String json, Class<T> clazz) {
		try {
			logger.debug("JsonHelper: convertJsonToObject");
			Gson gson = new Gson();
			return gson.fromJson(json, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public static String convertObjectToJson(Object obj) {
		return new Gson().toJson(obj);
	}

	public static JSONObject convertToJSONObject(Object obj) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		try {
			jsonObject = (JSONObject) parser.parse(convertObjectToJson(obj));
		} catch (ParseException e) {
			jsonObject = null;
			e.printStackTrace();
		}
		return jsonObject;
	}

	@SuppressWarnings("finally")
	public <T> T mapDataToObject(Class<T> clazz) {
		String currentPlatform;
		String currentDriver;
		JsonElement content = null;
		try {
			currentPlatform = DriverManager.getDriverProperty().getPlatform().toString().toLowerCase();
			currentDriver = DriverManager.getDriverProperty().getDriverType().toString().toLowerCase();
			// Get property from platform-driver key
			content = _jsonObject.get(getKey(currentPlatform, currentDriver));
			if (content != null) {
				return convertJsonToObject(content.toString(), clazz);
			}
			// Get property from any-driver key
			if (content == null) {
				content = _jsonObject.get(getKey(_defaultPlatform, currentDriver));
				if (content != null) {
					return convertJsonToObject(content.toString(), clazz);
				}
			}
			// Get property from platform-default key
			if (content == null) {
				content = _jsonObject.get(getKey(currentPlatform, _defaultDriver));
				if (content != null) {
					return convertJsonToObject(content.toString(), clazz);
				}
			}
			// Get property from any-default key
			if (content == null) {
				content = _jsonObject.get(getKey(_defaultPlatform, _defaultDriver));
				if (content != null) {
					return convertJsonToObject(content.toString(), clazz);
				}
			}
			if (content == null) {
				throw new Exception(String.format("'%s' Object could not be mapped for '%s'", clazz,
						getKey(currentPlatform, currentDriver)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return convertJsonToObject(content.toString(), clazz);
		}
	}
}
