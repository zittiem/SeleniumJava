package utils.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;

public class PropertiesHelper {
	/*
	 * Properties mode
	 */
	private static Properties oConf, oProfile, oDefault, oProps;

	private static Properties propsForName(String propFileName) {
		InputStream inputStream = null;
		try {
			System.out.println("Loading properties : " + propFileName);
			inputStream = PropertiesHelper.class.getClassLoader()
					.getResourceAsStream(propFileName);

			if (inputStream != null) {
				Properties prop = new Properties();
				prop.load(inputStream);
				return prop;
			} else {
				// throw new FileNotFoundException("property file '" +
				// propFileName + "' not found in the classpath" );
				System.err.println(propFileName + " not found !");
				return null;
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	private static void _initProps() {
		if (oConf == null) {
			oConf = propsForName("conf.properties");
			oDefault = propsForName("profiles/default.properties");

			// Profile is passed by arguments via Jenkins
			String profile = System.getProperty("profile");

			if (profile != null)
				oProfile = propsForName("profiles/" + profile + ".properties");

			String props = System.getProperty("properties");
			if (props != null) {
				oProps = new Properties();
				try {
					oProps.load(new StringReader(props));
				} catch (IOException e) {
				}
			}
		}
	}

	public static String getPropValue(String key) {
		return getPropValue(key, null);
	}

	public static String getPropValue(String key, String defaultValue) {
		_initProps();

		if (System.getProperty(key) != null)
			return System.getProperty(key);

		if (oProps != null && oProps.containsKey(key))
			return oProps.getProperty(key);

		if (oProfile != null && oProfile.containsKey(key))
			return oProfile.getProperty(key);

		if (oDefault != null && oDefault.containsKey(key))
			return oDefault.getProperty(key);

		if (oConf.containsKey(key))
			return oConf.getProperty(key);
		return defaultValue;
	}

}
