package driver.manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import com.google.common.base.Throwables;

import driver.setting.DriverProperty;

public class DriverFactory {
	private static Logger logger = Logger.getLogger(DriverFactory.class);

	private static final String DRIVER_PACKAGE_NAME = "drivers.%s";
	private static final String DRIVER_CLASS_NAME = "%s%sDriver";

	public static BaseDriver newInstance(DriverProperty property) {

		String packageName = String.format(DRIVER_PACKAGE_NAME, property.getDriverType().toString().toLowerCase());
		String className = String.format(DRIVER_CLASS_NAME, property.getMode(), property.getDriverType().toString());

		try {
			Method method;
			String fullClassName = packageName + "." + className;
			Class<?> clzz = Class.forName(fullClassName);
			Constructor<?> cons = clzz.getDeclaredConstructor(new Class[] { DriverProperty.class });
			Object obj = cons.newInstance(property);

			// Create Driver
			method = clzz.getDeclaredMethod("createDriver");
			method.invoke(obj);
			return (BaseDriver) obj;
		} catch (Exception e) {
			logger.error("Could not create new Driver instance. " + Throwables.getStackTraceAsString(e));
			return null;
		}
	}
}
