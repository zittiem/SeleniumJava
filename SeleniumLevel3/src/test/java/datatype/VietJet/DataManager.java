package datatype.VietJet;

import com.google.gson.JsonObject;

public class DataManager {
	
	public static ThreadLocal<SharedData> SHARED_DATA = new ThreadLocal<SharedData>();
	
	public class SharedData
	{
		public JsonObject jsonObject;
		public String appName;
		public String language;
		public String url;
		public String date_format;
		public String datetime_format;
		public String simple_date_format;
		
		public String getMember(String memberName)
		{
			return jsonObject.get(memberName).getAsString();
		}
	}
}
