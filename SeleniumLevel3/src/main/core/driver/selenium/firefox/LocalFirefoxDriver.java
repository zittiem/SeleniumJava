package driver.selenium.firefox;

//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//
//import com.logigear.driver.DriverProperty;
//import com.logigear.driver.manager.BaseDriver;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//
//public class LocalFirefoxDriver extends BaseDriver {
//
//	public LocalFirefoxDriver(DriverProperty property) {
//		super(property);
//	}
//
//	@Override
//	public void createWebDriver() {
//		WebDriverManager.firefoxdriver().setup();
//		FirefoxOptions ops = new FirefoxOptions();
//		
//		ops.addArguments(property.getArguments());
//		ops.merge(property.getCapabilities());
//		
//		webDriver = new FirefoxDriver(ops);
//	}
//}
