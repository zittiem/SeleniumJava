package helper;

import java.io.FileReader;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import driver.setting.DriverProperty;

public class DriverSettingHelper {

	public static DriverProperty getDriverProperty(String file, String sectionName) {
		DriverProperty property = new DriverProperty();
		
		try {
			Ini ini = new Ini(new FileReader(file));
			Section section = ini.get(sectionName);
			if (section == null) {
				throw new Exception(String.format("Cannot find '%s' section in file '%s'", sectionName, file));
			}
			
			String mode = section.get("mode");
			String driverType = section.get("driver");
			String pageTimeOut = section.get("pageTimeOut");
			String elementTimeOut = section.get("elementTimeOut");
			String driverExecutable = section.get("executable");
			String remoteUrl = section.get("remoteUrl");
			String arguments = section.get("arguments");
			String userPrefs = section.get("userProfilePreference");
			String capabilities = section.get("capabilities");
			
			property.setMode(mode);
			property.setDriverType(driverType);
			property.setPageTimeOut(pageTimeOut);
			property.setElementTimeOut(elementTimeOut);
			property.setDriverExecutable(driverExecutable);
			property.setRemoteUrl(remoteUrl);
			property.setArguments(arguments);
			property.setUserProfilePreference(userPrefs);
			property.setCapabilities(capabilities);
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return property;
	}
}
