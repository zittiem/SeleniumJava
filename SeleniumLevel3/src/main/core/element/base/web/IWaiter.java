package element.base.web;

import java.util.function.Function;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import driver.manager.DriverManager;
import element.setting.ElementStatus;

public interface IWaiter extends IFinder {
	public static Logger logger = Logger.getLogger(IWaiter.class);
	
	public default void waitForCondition(ElementStatus condition, int timeOut, boolean throwable, Object... args) {
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOut);
			
			switch (condition) {
			case PRESENT:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							getElement();
						} catch (NoSuchElementException e) {
							return false;
						}
						return true;
					}
				});
				break;
			case NOT_PRESENT:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							getElement();
						} catch (NoSuchElementException e) {
							return true;
						}
						return false;
					}
				});
				break;
			case DISPLAYED:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return getElement().isDisplayed();
						} catch (NoSuchElementException e) {
							return false;
						}
						catch (StaleElementReferenceException e) {
							return false;
						}
					}
				});
				break;
			case NOT_DISPLAYED:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return !(getElement().isDisplayed());
						} catch (NoSuchElementException e) {
							return true;
						}
						catch (StaleElementReferenceException e) {
							return true;
						}
					}
				});
				break;
			case CLICKABLE:
			case ENABLED:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return getElement().isDisplayed() && getElement().isEnabled();
						} catch (NoSuchElementException e) {
							return false;
						}
						catch (StaleElementReferenceException e) {
							return false;
						}
					}
				});
				break;
			case NOT_CLICKABLE:
			case DISABLED:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return !(getElement().isDisplayed() && getElement().isEnabled());
						} catch (NoSuchElementException e) {
							return false;
						}
						catch (StaleElementReferenceException e) {
							return false;
						}
					}
				});
				break;
			case SELECTED:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return getElement().isSelected();
						} catch (NoSuchElementException e) {
							return false;
						}
						catch (StaleElementReferenceException e) {
							return false;
						}
					}
				});
				break;
			case NOT_SELECTED:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return !(getElement().isSelected());
						} catch (NoSuchElementException e) {
							return false;
						}
						catch (StaleElementReferenceException e) {
							return false;
						}
					}
				});
				break;
			case TEXT_TO_BE:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return getElement().getText().equals(args[0].toString());
						} catch (NoSuchElementException e) {
							return false;
						}
						catch (StaleElementReferenceException e) {
							return false;
						}
					}
				});
				break;
			case TEXT_CHANGED:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return !(getElement().getText().equals(args[0].toString()));
						} catch (NoSuchElementException e) {
							return false;
						}
						catch (StaleElementReferenceException e) {
							return false;
						}
					}
				});
				break;
			case ATTRIBUTE_TO_BE:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return getElement().getAttribute(args[0].toString()).equals(args[1].toString());
						} catch (NoSuchElementException e) {
							return false;
						}
						catch (StaleElementReferenceException e) {
							return false;
						}
					}
				});
				break;
			case ATTRIBUTE_CHANGED:
				wait.until(new Function<WebDriver, Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						try {
							return !(getElement().getAttribute(args[0].toString()).equals(args[1].toString()));
						} catch (NoSuchElementException e) {
							return false;
						}
						catch (StaleElementReferenceException e) {
							return false;
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
	
	public default void waitForTextToBe(String text, int timeOut) {
		waitForCondition(ElementStatus.TEXT_TO_BE, timeOut, true, text);
	}
	
	public default void waitForTextChanged(String originText, int timeOut) {
		waitForCondition(ElementStatus.TEXT_CHANGED, timeOut, true, originText);
	}
	
	public default void waitForAttributeToBe(String attributeName, String value, int timeOut) {
		waitForCondition(ElementStatus.ATTRIBUTE_TO_BE, timeOut, true, attributeName, value);
	}
	
	public default void waitForAttributeChanged(String attributeName, String originValue, int timeOut) {
		waitForCondition(ElementStatus.ATTRIBUTE_CHANGED, timeOut, true, attributeName, originValue);
	}

}
