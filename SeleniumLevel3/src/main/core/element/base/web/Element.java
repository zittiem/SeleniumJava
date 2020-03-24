package element.base.web;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.javatuples.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import driver.manager.DriverUtils;
import driver.setting.DriverProperty;
import element.base.web.Element;
import element.setting.ElementStatus;
import element.setting.FindBy;
import helper.Constant;
import helper.LocatorHelper;

public class Element implements IFinder, IWaiter, IAction, IInfo {
	private static Logger logger = Logger.getLogger(Element.class);

	private By byLocator;
	private Pair<FindBy, String> pairLocator;
	private Element parentElement;

	public Element(By locator) {
		this.byLocator = locator;
	}
	
	public Element(String locator) {
		this.byLocator = getByLocator(locator);
		this.pairLocator = LocatorHelper.getPairLocator(locator);
	}
	
	public Element(Element parentElement, String locator) {
		this.byLocator = getByLocator(locator);
		this.pairLocator = LocatorHelper.getPairLocator(locator);
		this.parentElement = parentElement;
	}
	
	public Element(String locator, Object... arguments) {
		this.byLocator = getByLocator(String.format(locator, arguments));
		this.pairLocator = LocatorHelper.getPairLocator(locator);
	}
	
	public Element(Element parentElement, String locator, Object... arguments) {
		this.byLocator = getByLocator(String.format(locator, arguments));
		this.pairLocator = LocatorHelper.getPairLocator(locator);
		this.parentElement = parentElement;
	}

	public Element(Pair<FindBy, String> locator) {
		this.byLocator = getByLocator(locator);
		this.pairLocator = locator;
	}
	
	public Element(Element parentElement, Pair<FindBy, String> locator) {
		this.byLocator = getByLocator(locator);
		this.pairLocator = locator;
		this.parentElement = parentElement;
	}
	
	public Element(Pair<FindBy, String> locator, Object... arguments) {
		this.byLocator = getByLocator(locator.getValue0(), String.format(locator.getValue1(), arguments));
		this.pairLocator = locator;
	}
	
	public Element(Element parentElement, Pair<FindBy, String> locator, Object... arguments) {
		this.byLocator = getByLocator(locator.getValue0(), String.format(locator.getValue1(), arguments));
		this.pairLocator = locator;
		this.parentElement = parentElement;
	}

	public Element(FindBy by, String value) {
		this.byLocator = getByLocator(by, value);
		this.pairLocator = new Pair<FindBy, String>(by, value);
	}
	
	public Element(Element parentElement, FindBy by, String value) {
		this.byLocator = getByLocator(by, value);
		this.pairLocator = new Pair<FindBy, String>(by, value);
		this.parentElement = parentElement;
	}

	public Element(FindBy by, String value, Object... arguments) {
		this.byLocator = getByLocator(by, String.format(value, arguments));
		this.pairLocator = new Pair<FindBy, String>(by, value);
	}
	
	public Element(Element parentElement, FindBy by, String value, Object... arguments) {
		this.byLocator = getByLocator(by, String.format(value, arguments));
		this.pairLocator = new Pair<FindBy, String>(by, value);
		this.parentElement = parentElement;
	}
	
	public Element Dynamic(Object... arguments)
	{
		if (this.pairLocator != null)
			this.byLocator = getByLocator(this.pairLocator.getValue0(), String.format(this.pairLocator.getValue1(), arguments));
		return this;
	}
	
	// ---------------------- Locator ---------------------------- //
	@Override
	public By getLocator() {
		return this.byLocator;
	}
	
	// ---------------------- Finder ---------------------------- //
	@Override
	public WebElement getElement() {
		if (parentElement != null)
			return parentElement.getChildElement(getLocator());
		return DriverUtils.findElement(getLocator());
	}

	@Override
	public WebElement getChildElement(By locator) {
		return getElement().findElement(locator);
	}
	
	@Override
	public WebElement getChildElement(Pair<FindBy, String> locator) {
		return getElement().findElement(getByLocator(locator));
	}
	
	@Override
	public WebElement getChildElement(FindBy by, String value) {
		return getElement().findElement(getByLocator(by, value));
	}

	@Override
	public WebElement getChildElement(String locator) {
		return getElement().findElement(getByLocator(locator));
	}

	@Override
	public List<WebElement> getElements() {
		if (parentElement != null)
			return parentElement.getChildElements(getLocator());
		return DriverUtils.findElements(getLocator());
	}

	@Override
	public List<WebElement> getChildElements(By locator) {
		return getElement().findElements(locator);
	}
	
	@Override
	public List<WebElement> getChildElements(Pair<FindBy, String> locator) {
		return getElement().findElements(getByLocator(locator));
	}
	
	@Override
	public List<WebElement> getChildElements(FindBy by, String value) {
		return getElement().findElements(getByLocator(by, value));
	}

	@Override
	public List<WebElement> getChildElements(String locator) {
		return getElement().findElements(getByLocator(locator));
	}

	// ---------------------- Action ---------------------------- //
	@Override
	public void click() {
		logger.debug(String.format("Click on %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForCondition(ElementStatus.CLICKABLE, Constant.ElementWaitingTime, true);
		    	getElement().click();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to click control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}
	
	@Override
	public void click(int x, int y) {
		logger.debug(String.format("Click at (%s, %s) on %s ", x, y, getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForCondition(ElementStatus.CLICKABLE, Constant.ElementWaitingTime, true);
		    	new Actions(DriverUtils.getDriver()).moveToElement(getElement(), x, y).click().build().perform();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to click(%s, %s) control %s again", x, y, getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void clickByJS() {
		logger.debug(String.format("Click by JS on %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	//waitForCondition(ElementStatus.CLICKABLE, Constant.ElementWaitingTime, true);
		    	DriverUtils.executeJavaScript("arguments[0].click();", getElement());
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to clickByJS control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void clickByAction() {
		logger.debug(String.format("Click by action on %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForCondition(ElementStatus.CLICKABLE, Constant.ElementWaitingTime, true);
		    	new Actions(DriverUtils.getDriver()).click(getElement()).build().perform();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to clickByAction control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}
	
	@Override
	public void doubleClick() {
		logger.debug(String.format("Double-click on %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForCondition(ElementStatus.CLICKABLE, Constant.ElementWaitingTime, true);
		    	new Actions(DriverUtils.getDriver()).doubleClick(getElement()).build().perform();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to doubleClick control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void sendKeys(CharSequence... keysToEnter) {
		logger.debug(String.format("Send keys '%s' to %s", keysToEnter, getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForCondition(ElementStatus.PRESENT, Constant.ElementWaitingTime, true);
		    	getElement().sendKeys(keysToEnter);
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to sendKeys to control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void submit() {
		logger.debug(String.format("Submit %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForCondition(ElementStatus.PRESENT, Constant.ElementWaitingTime, true);
		    	getElement().submit();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to submit control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void focus() {
		logger.debug(String.format("Focus on %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForCondition(ElementStatus.PRESENT, Constant.ElementWaitingTime, true);
		    	DriverUtils.executeJavaScript("arguments[0].focus();", getElement());
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to focus on control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void hover() {
		logger.debug(String.format("Hover on %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	String mouseHoverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		    	waitForCondition(ElementStatus.PRESENT, Constant.ElementWaitingTime, true);
		    	DriverUtils.executeJavaScript(mouseHoverScript, getElement());
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to hover on control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}
	
	@Override
	public void moveToElement() {
		logger.debug(String.format("Move to %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForCondition(ElementStatus.PRESENT, Constant.ElementWaitingTime, true);
		    	new Actions(DriverUtils.getDriver()).moveToElement(getElement()).build().perform();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to move to control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void scrollIntoView() {
		logger.debug(String.format("Scroll to %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForCondition(ElementStatus.PRESENT, Constant.ElementWaitingTime, true);
		    	DriverUtils.executeJavaScript("arguments[0].scrollIntoView(true);", getElement());
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to scroll to control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	@Override
	public void scrollIntoViewBottom() {
		logger.debug(String.format("Scroll to bottom of %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForCondition(ElementStatus.PRESENT, Constant.ElementWaitingTime, true);
		    	DriverUtils.executeJavaScript("arguments[0].scrollIntoView(false);", getElement());
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to scroll to bottom of control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	// ---------------------- Status ---------------------------- //
	@Override
	public boolean isDisplayed() {
		logger.debug(String.format("Check Displayed status of %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	getElement().isDisplayed();
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		return false;
		    	logger.warn(String.format("Try to get Displayed status from control %s again", getLocator().toString()));
		    } catch (NoSuchElementException noSuchEx) {
		    	return false;
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	return false;
		    }
		}
		return false;
	}

	@Override
	public boolean isDisplayed(int timeOutInSeconds) {
		logger.debug(String.format("Check Displayed status of %s in %s seconds", getLocator().toString(), timeOutInSeconds));
	    try {
	    	waitForCondition(ElementStatus.DISPLAYED, timeOutInSeconds, true);
	    	return true;
	    } catch (TimeoutException timeOutEx) {
	    	return false;
	    } catch (Exception e) {
	    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					e.getMessage()));
	    	return false;
	    }
	}

	@Override
	public boolean isEnabled() {
		logger.debug(String.format("Check Enabled status of %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	getElement().isEnabled();
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		return false;
		    	logger.warn(String.format("Try to get Enabled status from control %s again", getLocator().toString()));
		    } catch (NoSuchElementException noSuchEx) {
		    	return false;
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	return false;
		    }
		}
		return false;
	}

	@Override
	public boolean isEnabled(int timeOutInSeconds) {
		logger.debug(String.format("Check Enabled status of %s in %s seconds", getLocator().toString(), timeOutInSeconds));
		try {
	    	waitForCondition(ElementStatus.ENABLED, timeOutInSeconds, true);
	    	return true;
	    } catch (TimeoutException timeOutEx) {
	    	return false;
	    } catch (Exception e) {
	    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					e.getMessage()));
	    	return false;
	    }
	}

	@Override
	public boolean isSelected() {
		logger.debug(String.format("Check Selected status of %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	getElement().isSelected();
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		return false;
		    	logger.warn(String.format("Try to get Selected status from control %s again", getLocator().toString()));
		    } catch (NoSuchElementException noSuchEx) {
		    	return false;
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	return false;
		    }
		}
		return false;
	}

	@Override
	public boolean isSelected(int timeOutInSeconds) {
		logger.debug(String.format("Check Selected status of %s in %s seconds", getLocator().toString(), timeOutInSeconds));
		try {
	    	waitForCondition(ElementStatus.SELECTED, timeOutInSeconds, true);
	    	return true;
	    } catch (TimeoutException timeOutEx) {
	    	return false;
	    } catch (Exception e) {
	    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					e.getMessage()));
	    	return false;
	    }
	}

	@Override
	public String getCssValue(String propertyName) {
		logger.debug(String.format("Get Css value '%s' of %s", propertyName, getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	return getElement().getCssValue(propertyName);
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		return null;
		    	logger.warn(String.format("Try to get CSS value '%s' from control %s again", propertyName, getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	return null;
		    }
		}
		return null;
	}

	@Override
	public String getAttribute(String attributeName) {
		logger.debug(String.format("Get Attribute value '%s' of %s", attributeName, getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	return getElement().getAttribute(attributeName);
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		return null;
		    	logger.warn(String.format("Try to get Attribute '%s' from control %s again", attributeName, getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	return null;
		    }
		}
		return null;
	}

	@Override
	public String getText() {
		logger.debug(String.format("Get Text of %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	return getElement().getText();
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		return null;
		    	logger.warn(String.format("Try to get Text from control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	return null;
		    }
		}
		return null;
	}

	@Override
	public String getValue() {
		logger.debug(String.format("Get Value of %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	return getElement().getAttribute("value");
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		return null;
		    	logger.warn(String.format("Try to get Value from control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	return null;
		    }
		}
		return null;
	}

	@Override
	public String getTagName() {
		logger.debug(String.format("Get TagName of %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	return getElement().getTagName();
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		return null;
		    	logger.warn(String.format("Try to get TagName from control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	return null;
		    }
		}
		return null;
	}

	@Override
	public Point getLocation() {
		logger.debug(String.format("Get Location of %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	return getElement().getLocation();
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		return null;
		    	logger.warn(String.format("Try to get Location from control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	return null;
		    }
		}
		return null;
	}

	@Override
	public Dimension getSize() {
		logger.debug(String.format("Get Size of %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	return getElement().getSize();
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		return null;
		    	logger.warn(String.format("Try to get Size from control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	return null;
		    }
		}
		return null;
	}

	@Override
	public Rectangle getRect() {
		logger.debug(String.format("Get Rect of %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	return getElement().getRect();
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		return null;
		    	logger.warn(String.format("Try to get Rect from control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	return null;
		    }
		}
		return null;
	}

	public List<Element> getWrapperElements() {
		return getWrapperElements(Element.class);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getWrapperElements(Class<T> clazz) {
		List<T> elements = new ArrayList<>();
		try {
			if (!getLocator().toString().contains(FindBy.xpath.getValue()))
				throw new Exception("getWrapperElements: Only support for xpath locator");
			Constructor<?> cons = clazz.getDeclaredConstructor(new Class[] { FindBy.class, String.class });
			String xpath = getLocator().toString().substring(10);
			List<WebElement> list = getChildElements(getLocator());
			for (int i = 1; i <= list.size(); i++) {
				T element = (T)cons.newInstance(FindBy.xpath, "(" + xpath + ")[" + i + "]");
				elements.add(element);
			}
		} catch (Exception error) {
			logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
		}
		return elements;
	}

}
