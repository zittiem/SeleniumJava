package driver.manager;

import org.openqa.selenium.WebDriver;

public abstract class BaseDriver {
	
	protected WebDriver webDriver;
	protected int timeOut = 30;
	private int pageLoadTimeOut = 60;
	protected int shortTimeOut = 15;
	
	protected int getTimeOut() {
		return timeOut;
	}

	protected void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getPageLoadTimeOut() {
		return pageLoadTimeOut;
	}

	public void setPageLoadTimeOut(int pageLoadTimeOut) {
		this.pageLoadTimeOut = pageLoadTimeOut;
	}
	
	public abstract void createWebDriver();
}
