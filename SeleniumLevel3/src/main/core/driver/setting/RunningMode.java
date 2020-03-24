package driver.setting;

import java.util.Arrays;

public enum RunningMode {
	Local, Remote;

	public static RunningMode fromString(String type) throws Exception {
		for (RunningMode e : RunningMode.values()) {
			if (e.toString().equalsIgnoreCase(type))
				return e;
		}
		throw new Exception(String.format("Running mode '%s' is not supported. Please use supported running mode: %s", type,
				Arrays.toString(RunningMode.values())));
    }
}
