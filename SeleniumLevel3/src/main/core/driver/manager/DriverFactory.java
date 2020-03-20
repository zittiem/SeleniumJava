package driver.manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import com.google.common.base.Throwables;

import driver.resource.base.BaseDriver;
import driver.setting.DriverProperty;

public class DriverFactory {
	private static Logger logger = Logger.getLogger(DriverFactory.class);

	private static final String DRIVER_CLASS_FULLNAME = "driver.resource.%sDriver";
	private static final String METHOD_NAME = "create%sDriver";

	public static BaseDriver newInstance(DriverProperty property) {

		String classFullName = String.format(DRIVER_CLASS_FULLNAME, property.getDriverType().toString());
		String methodName = String.format(METHOD_NAME, property.getMode());

		try {
			Method method;
			Class<?> clzz = Class.forName(classFullName);
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
