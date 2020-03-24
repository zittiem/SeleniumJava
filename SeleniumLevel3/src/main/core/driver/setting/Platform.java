package driver.setting;

import java.util.Arrays;

public enum Platform {
	ANY, WINDOWS, MAC, LINUX, ANDROID, IOS;

	public static Platform fromString(String type) throws Exception {
		for (Platform e : Platform.values()) {
			if (e.toString().equalsIgnoreCase(type))
				return e;
		}
		throw new Exception(String.format("Platform '%s' is not supported. Please use supported platform: %s", type,
				Arrays.toString(Platform.values())));
    }
}
