package driver.setting;

import java.util.Arrays;

public enum DriverType {
	Chrome, Firefox, IE, AndroidNative;

	public static DriverType fromString(String type) throws Exception {
        for (DriverType e : DriverType.values()) {
			if (e.toString().equalsIgnoreCase(type))
				return e;
		}
        throw new Exception(String.format("Driver type '%s' is not supported. Please use supported driver types: %s", type,
				Arrays.toString(DriverType.values())));
    }
}
