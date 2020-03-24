package helper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonHelper {

	private static Logger logger = Logger.getLogger(JsonHelper.class);

	public static List<String> convertJsonToList(String json) throws Exception {
		try {
			logger.debug("JsonHelper: convertJsonToList");
			Type mapType = new TypeToken<List<String>>() {
			}.getType();
			Gson gson = new Gson();
			return gson.fromJson(json, mapType);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	public static <T> Map<String, T> convertJsonToMap(String json) throws Exception {
		try {
			logger.debug("JsonHelper: convertJsonToMap");
			Type mapType = new TypeToken<Map<String, T>>() {
			}.getType();
			Gson gson = new Gson();
			return gson.fromJson(json, mapType);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	public static DesiredCapabilities convertJsonToCapabilities(String json) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		Map<String, String> caps = JsonHelper.convertJsonToMap(json);
		if (caps != null) {
			Set<String> keys = caps.keySet();
			for (String key : keys) {
				capabilities.setCapability(key, caps.get(key));
			}
		}
		return capabilities;
	}
}
