package driver.manager;

import org.openqa.selenium.WebDriver;

import com.logigear.driver.DriverProperty;

public abstract class BaseDriver {
	
	protected DriverProperty property;
	protected WebDriver webDriver;
	protected boolean isWaitForAjax;
	protected int timeOut = 30;
	private int pageLoadTimeOut = 60;
	protected int shortTimeOut = 15;
	
	public BaseDriver(DriverProperty property) {
		this.property = property;
	}
	
	public void setWaitForAjax(boolean isWait) {
		isWaitForAjax = isWait;
	}
	
	public boolean isWaitForAjax() {
		return isWaitForAjax;
	}
	
	protected int getTimeOut() {
		return timeOut;
	}

	protected void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	public abstract void createWebDriver();

	public int getPageLoadTimeOut() {
		return pageLoadTimeOut;
	}

	public void setPageLoadTimeOut(int pageLoadTimeOut) {
		this.pageLoadTimeOut = pageLoadTimeOut;
	}
}
