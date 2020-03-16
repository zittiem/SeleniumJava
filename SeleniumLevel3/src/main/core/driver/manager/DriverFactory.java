package driver.manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import com.google.common.base.Throwables;

import driver.setting.DriverProperty;

public class DriverFactory {
	private static Logger logger = Logger.getLogger(DriverFactory.class);

	private static final String DRIVER_PACKAGE_NAME = "drivers.resource";
	private static final String DRIVER_CLASS_NAME = "%sDriver";
	private static final String METHOD_NAME = "create%sDriver";

	public static BaseDriver newInstance(DriverProperty property) {

		String className = String.format(DRIVER_CLASS_NAME, property.getDriverType().toString());
		String methodName = String.format(METHOD_NAME, property.getMode());

		try {
			Method method;
			String fullClassName = DRIVER_PACKAGE_NAME + "." + className;
			Class<?> clzz = Class.forName(fullClassName);
			Constructor<?> cons = clzz.getDeclaredConstructor(new Class[] { DriverProperty.class });
			Object obj = cons.newInstance(property);

			// Create Driver
			method = clzz.getDeclaredMethod(methodName);
			method.invoke(obj);
			return (BaseDriver) obj;
		} catch (Exception e) {
			logger.error(String.format("Cannot create new %s driver instance. %s", property.getDriverType().toString(), Throwables.getStackTraceAsString(e)));
			return null;
		}
	}
}
