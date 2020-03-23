package element.base;

import java.util.function.Function;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import driver.manager.DriverManager;
import driver.manager.DriverUtils;
import element.setting.ElementStatus;

public interface IWaiter extends ILocator {
	public static Logger logger = Logger.getLogger(IWaiter.class);
	
	public default void waitForCondition(ElementStatus condition, int timeOut, boolean throwable) {
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOut);
			
			switch (condition) {
			case PRESENT:
				wait.until(ExpectedConditions.presenceOfElementLocated(getLocator()));
				break;
			case NOT_PRESENT:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							DriverUtils.findElement(getLocator());
						} catch (NoSuchElementException e) {
							return true;
						}
						return false;
					}
				});
				break;
			case DISPLAYED:
				wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator()));
				break;
			case NOT_DISPLAYED:
				wait.until(ExpectedConditions.invisibilityOfElementLocated(getLocator()));
				break;
			case CLICKABLE:
			case ENABLED:
				wait.until(ExpectedConditions.elementToBeClickable(getLocator()));
				break;
			case NOT_CLICKABLE:
			case DISABLED:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return !(DriverUtils.findElement(getLocator()).isEnabled());
						} catch (NoSuchElementException e) {
							return true;
						}
						catch (StaleElementReferenceException e) {
							return true;
						}
					}
				});
				break;
			case SELECTED:
				wait.until(ExpectedConditions.elementToBeSelected(getLocator()));
				break;
			case NOT_SELECTED:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return !(DriverUtils.findElement(getLocator()).isSelected());
						} catch (NoSuchElementException e) {
							return true;
						}
						catch (StaleElementReferenceException e) {
							return true;
						}
					}
				});
				break;
			default:
				break;
			}
		} catch (Exception error) {
			logger.error(String.format("Exception! - Error when wait for element %s '%s': %s", 
					condition.toString().toLowerCase(), getLocator().toString(), error.getMessage()));
			if (throwable)
			{
				if (error instanceof TimeoutException)
				{
					throw new TimeoutException(String.format("Timeout when wait for element '%s' is '%s' within %s seconds", 
							getLocator().toString(), condition.toString().toLowerCase(), timeOut));
				}
				else throw error;
			}
		}
	}
	
	public default void waitForPresent(int timeOut) {
		waitForCondition(ElementStatus.PRESENT, timeOut, true);
	}
	
	public default void waitForNotPresent(int timeOut) {
		waitForCondition(ElementStatus.NOT_PRESENT, timeOut, true);
	}

	public default void waitForClickable(int timeOut) {
		waitForCondition(ElementStatus.CLICKABLE, timeOut, true);
	}
	
	public default void waitForNotClickable(int timeOut) {
		waitForCondition(ElementStatus.NOT_CLICKABLE, timeOut, true);
	}

	public default void waitForDisplayed(int timeOut) {
		waitForCondition(ElementStatus.DISPLAYED, timeOut, true);
	}
	
	public default void waitForNotDisplayed(int timeOut) {
		waitForCondition(ElementStatus.NOT_DISPLAYED, timeOut, true);
	}
	
	public default void waitForEnabled(int timeOut) {
		waitForCondition(ElementStatus.ENABLED, timeOut, true);
	}
	
	public default void waitForDisabled(int timeOut) {
		waitForCondition(ElementStatus.DISABLED, timeOut, true);
	}
	
	public default void waitForSelected(int timeOut) {
		waitForCondition(ElementStatus.SELECTED, timeOut, true);
	}
	
	public default void waitForNotSelected(int timeOut) {
		waitForCondition(ElementStatus.NOT_SELECTED, timeOut, true);
	}

}
