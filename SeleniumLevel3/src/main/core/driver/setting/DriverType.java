package driver.setting;

import java.util.Arrays;

public enum DriverType {
	Chrome, Firefox, IE, AndroidNative;

	public static DriverType fromString(String type) throws Exception {
        try {
			return DriverType.valueOf(type);
		} catch (Exception e) {
			throw new Exception(String.format("Driver type '%s' is not supported. Please use supported driver types: %s", type,
					Arrays.toString(DriverType.values())));
		}
    }
}
