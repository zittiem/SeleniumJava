package utils.helper;

import com.google.gson.JsonObject;

public class ResourceHelper {
	
	public static ThreadLocal<SharedData> SHARED_DATA = new ThreadLocal<SharedData>();
	
	public static String getResource(String resourceName)
	{
		return SHARED_DATA.get().jsonObject.get(resourceName).getAsString();
	}
	
	public class SharedData
	{
		public JsonObject jsonObject;
		public String appName;
		public String language;
		public String url;
		public String language_name;
		public String date_format;
		public String datetime_format;
		public String simple_date_format;
		public String month_year_format;
	}
}
