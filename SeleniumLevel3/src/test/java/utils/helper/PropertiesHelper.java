package utils.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {
	
	private static Properties pProfile;

	private static Properties loadPropertiesFromClasspath(String propFileName) {
	
		InputStream input = null;
		try {
			input = PropertiesHelper.class.getClassLoader().getResourceAsStream(propFileName);
			if (input == null) {
				System.out.println("Sorry, unable to find " + propFileName);
				return null;
			}

			// load a properties file from class path, inside static method
			pProfile.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return pProfile;
	}


	public static String getPropValue(String key) {
		pProfile = loadPropertiesFromClasspath("profile.properties");
		return pProfile.getProperty(key);
	}

}
