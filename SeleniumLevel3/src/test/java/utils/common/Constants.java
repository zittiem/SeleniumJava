package utils.common;

import utils.helper.PropertiesHelper;

public class Constants {
	
	// Data folder controls json
	public static final String DATA_FOLDER = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\";

	public static final String DRIVER_SETTING_FILE = "src/test/resources/driver.setting.properties";
	public static final String LOCATOR_FOLDER_PATH = "src/test/resources/locator/";

	// Timeout variables
	public static final int LONG_TIME = 60;
	public static final int SHORT_TIME = 30;
	public static final int SLEEP_TIME = 3;
	public static final int LOADING_TIME = 2;
	
	//Setting for swipe screen on mobile
	public static final int VERTICAL_PERCENTAGE_START_POINT = 50;
	public static final int HORIZONTAL_PERCENTAGE_START_POINT = 50;
	
	// URL 
		public static final String vietJetUS_URL = PropertiesHelper.getPropValue("vietjetUS.url");
		public static final String vietJetVI_URL = PropertiesHelper.getPropValue("vietjetVI.url");
		public static final String agoda_URL = PropertiesHelper.getPropValue("agoda.url");
		public static final String logigearEmail_URL = PropertiesHelper.getPropValue("lgemail.url");}
