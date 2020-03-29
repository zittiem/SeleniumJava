package utils.helper;

import java.nio.file.Paths;

import org.apache.log4j.Logger;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import helper.JsonHelper;
import helper.Utilities;

public class DataHelper {
	private static Logger logger = Logger.getLogger(DataHelper.class);
	private JsonObject _jsonObject;
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
	
	public JsonObject getJsonObject() {
		return _jsonObject;
	}

	public <T> T getDataObject(Class<T> clazz) {
		JsonElement content = _jsonObject.get(clazz.getSimpleName());
		return JsonHelper.convertJsonToObject(content.toString(), clazz);
	}
	
	public <T> T getDataObject(Class<T> clazz, String key) {
		JsonElement content = _jsonObject.get(key);
		if (content == null)
			content = _jsonObject.get("default");
		
		if (content != null)
		{
			return JsonHelper.convertJsonToObject(content.toString(), clazz);
		}
		else return null;
	}
}
