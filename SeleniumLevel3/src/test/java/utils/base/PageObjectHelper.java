package utils.base;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.reflect.TypeToken;
import com.logigear.driver.manager.Driver;

import utils.helper.JsonHelper;

public class PageObjectHelper {

	private static Logger oLog = Logger.getLogger(PageObjectHelper.class);
	private static Map<String, List<Control>> mPageControls = new HashMap<String, List<Control>>();
	private static String locatorFolder = System.getProperty("user.dir") + "/src/test/resources/locators/";

	////////////////////////////////////////////////////////////////////////////
	// Load locators of the specified page from the Json file whose location is
	//////////////////////////////////////////////////////////////////////////// specified
	//////////////////////////////////////////////////////////////////////////// in
	//////////////////////////////////////////////////////////////////////////// DataFolder
	// @ Parameter: sPage is name of the Page that needs to load locators
	// @ Parameter: sFolder is location to get the Json file
	///////////////////////////////////////////////////////////////////////////

	public static boolean loadPageControl(String sPage) {
		oLog.debug("Load page controls for  " + sPage);

		if (mPageControls.get(sPage) != null)
			return true;

		String sPath = String.format("%s%s.json", locatorFolder, sPage);
		if (!Files.exists(Paths.get(sPath))) {
			oLog.debug(String.format("%s.json file doesn't exist", sPage));
			return false;
		}
		try {
			Type oType = new TypeToken<List<Control>>() {
			}.getType();
			List<Control> lstControls = JsonHelper.getListData(sPath, oType);
			boolean t = (lstControls != null);
			if (t)
				mPageControls.put(sPage, lstControls);
			else
				oLog.debug(String.format("%s.json file is invalid syntax or empty", sPage));
			return t;
		} catch (Exception e) {
			oLog.debug(e);
		}
		return false;
	}

	////////////////////////////////////////////////////////////////////////////
	// get list of locator of the specified page from dictionary of loaded pages
	// @ Parameter: sPage is name of that needs to load locators
	///////////////////////////////////////////////////////////////////////////
	public static List<Control> getPageControls(String sPage) {
		if (mPageControls == null)
			return null;
		return mPageControls.get(sPage);
	}

	////////////////////////////////////////////////////////////////////////////
	// Create a page object with specified class type
	// @ Parameter: oType is type of created Page object
	///////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	public static <T> T $Page(Class<T> oType) {
		T oPage = null;
		try {
			try {
				String sPlatform = Driver.getDriverProperty().getPlatform();
				String sPageClass = String.format("%s.%s_%s", oType.getPackage().getName(), oType.getSimpleName(), sPlatform.toUpperCase());
				oLog.debug("NewInstance for " + sPageClass);

				oPage = (T) Class.forName(sPageClass).newInstance();
			} catch (Exception e) {
				oPage = (T) oType.newInstance();
			}
		} catch (Exception e) {
			oLog.error(e.getMessage());
		}
		return oPage;
	}

	public static void loadPageObject (Object testClass) {
		Field[] lstField = testClass.getClass().getDeclaredFields();
		
		for (Field field : lstField) {
			try {
				field.setAccessible(true);
				field.set(testClass, $Page(field.getType()));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
}
