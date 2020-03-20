package element.base;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Stopwatch;

import driver.manager.DriverManager;
import driver.manager.DriverUtils;
import element.base.BaseElement;
import element.setting.ElementStatus;
import element.setting.FindElementBy;
import helper.Constant;

public class BaseElement implements IFinder, IWaiter, IAction, IStatus {
	private static Logger cLOG = Logger.getLogger(BaseElement.class);
	protected WebElement element = null;
	protected List<WebElement> elements = null;
	private By byLocator;

	public BaseElement(By locator) {
		this.byLocator = locator;
	}

	public BaseElement(String xPath) {
		this.byLocator = By.xpath(xPath);
	}

	public BaseElement(String by, String value) {
		this.byLocator = getByLocator(by, value);
	}

	public BaseElement(FindElementBy by, String value) {
		this.byLocator = getByLocator(by, value);
	}

	public BaseElement(FindElementBy by, String value, String text) {
		this.byLocator = getByLocator(by, String.format(value, text));
	}
	
	// ---------------------- Locator ---------------------------- //
	@Override
	public By getLocator() {
		return this.byLocator;
	}

	// ---------------------- Action ---------------------------- //
	@Override
	public void click() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForClickable(Constant.ElementWaitingTime).click();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to click control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}
	
	@Override
	public void click(int x, int y) {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	WebElement element = waitForClickable(Constant.ElementWaitingTime);
		    	new Actions(DriverUtils.getDriver()).moveToElement(element, x, y).click().build().perform();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to click(%s, %s) control %s again", x, y, getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void clickByJS() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	WebElement element = waitForClickable(Constant.ElementWaitingTime);
		    	DriverUtils.executeJavaScript("arguments[0].click();", element);
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to clickByJS control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void clickByAction() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	WebElement element = waitForClickable(Constant.ElementWaitingTime);
		    	new Actions(DriverUtils.getDriver()).click(element).build().perform();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to clickByAction control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}
	
	@Override
	public void doubleClick() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	WebElement element = waitForClickable(Constant.ElementWaitingTime);
		    	new Actions(DriverUtils.getDriver()).doubleClick(element).build().perform();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to doubleClick control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void sendKeys(CharSequence... keysToEnter) {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForPresent(Constant.ElementWaitingTime).sendKeys(keysToEnter);
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to sendKeys to control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void clear() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForPresent(Constant.ElementWaitingTime).clear();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to clear value for control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void submit() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForPresent(Constant.ElementWaitingTime).submit();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to submit control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void focus() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	WebElement element = waitForPresent(Constant.ElementWaitingTime);
		    	DriverUtils.executeJavaScript("arguments[0].focus();", element);
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to focus on control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void hover() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	String mouseHoverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		    	WebElement element = waitForPresent(Constant.ElementWaitingTime);
		    	DriverUtils.executeJavaScript(mouseHoverScript, element);
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to hover on control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}
	
	@Override
	public void moveToElement() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	WebElement element = waitForClickable(Constant.ElementWaitingTime);
		    	new Actions(DriverUtils.getDriver()).moveToElement(element).build().perform();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to move to control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void scrollIntoView() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	WebElement element = waitForPresent(Constant.ElementWaitingTime);
		    	DriverUtils.executeJavaScript("arguments[0].scrollIntoView(true);", element);
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to scroll to control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void scrollIntoViewBottom() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	WebElement element = waitForPresent(Constant.ElementWaitingTime);
		    	DriverUtils.executeJavaScript("arguments[0].scrollIntoView(false);", element);
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to scroll to bottom of control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	// ---------------------- Finder ---------------------------- //
	@Override
	public WebElement getElement() {
		return DriverUtils.findElement(getLocator());
	}

	@Override
	public WebElement getChildElement(By locator) {
		return getElement().findElement(getLocator());
	}

	@Override
	public List<WebElement> getElements() {
		return DriverUtils.findElements(getLocator());
	}

	@Override
	public List<WebElement> getChildElements(By locator) {
		return getElement().findElements(getLocator());
	}

	// ---------------------- Status ---------------------------- //
	@Override
	public boolean isDisplayed() {
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	return waitForPresent(Constant.ElementWaitingTime).isDisplayed();
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	cLOG.warn(String.format("Try to get Displayed status from control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
		return false;
	}

	@Override
	public boolean isDisplayed(int timeOutInSeconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int timeOutInSeconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSelected(int timeOutInSeconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCssValue(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttribute(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return null;
	}

	// ---------------------- Waiter ---------------------------- //

}
