package driver.manager;

import java.io.IOException;
import org.javatuples.Pair;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import helper.Constant;
import helper.Utilities;
public class Locator {

	String _jsonString;
	String _platformBrowser;
	public Locator(String fileName){
		try {
			_jsonString = Utilities.readFileFromFilePath(Constant.LocatorFolderPath + fileName + ".json");
		} catch (IOException e) {			
			e.printStackTrace();
		}
		_platformBrowser = DriverManager.getDriverProperty().getPlatform().toString().toLowerCase() + "-" 
						 + DriverManager.getDriverProperty().getDriverType().toString().toLowerCase();
	}
	
	@SuppressWarnings("finally")
	public Pair<String, String> getLocate(String elementName) {
		String locatorType = "";
		String locatorValue = "";
		JsonElement content = null;
		try {
			JsonObject jsonObject = new JsonParser().parse(_jsonString).getAsJsonObject();

			int i = 0;
			while (content == null && i < 2) {
				JsonElement locator = jsonObject.get(_platformBrowser);
				if (locator == null) 
					throw new Exception("Platform-Browser not found");
							
				content = locator.getAsJsonObject().get(elementName);
				_platformBrowser = "any-chrome";	
				i++;
			}
			
			if (content == null) 
				throw new Exception("Element name not found");
			
			locatorType = content.getAsString().split("=")[0];
			locatorValue = content.getAsString().substring(locatorType.length() + 1);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			return new Pair<String, String>(locatorType, locatorValue);
		}		
	}	
}
