package element.resource.web;

import org.javatuples.Pair;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import driver.manager.DriverUtils;
import element.base.BaseElement;
import element.setting.ElementStatus;
import element.setting.FindBy;
import helper.Constant;

public class EditableElement extends BaseElement {
	private static Logger logger = Logger.getLogger(EditableElement.class);

	public EditableElement(By locator) {
		super(locator);
	}
	
	public EditableElement(String locator) {
		super(locator);
	}
	
	public EditableElement(BaseElement parentElement, String locator) {
		super(parentElement, locator);
	}
	
	public EditableElement(String locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public EditableElement(BaseElement parentElement, String locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public EditableElement(Pair<FindBy, String> locator) {
		super(locator);
	}
	
	public EditableElement(BaseElement parentElement, Pair<FindBy, String> locator) {
		super(parentElement, locator);
	}
	
	public EditableElement(Pair<FindBy, String> locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public EditableElement(BaseElement parentElement, Pair<FindBy, String> locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public EditableElement(FindBy by, String value) {
		super(by, value);
	}
	
	public EditableElement(BaseElement parentElement, FindBy by, String value) {
		super(parentElement, by, value);
	}

	public EditableElement(FindBy by, String value, Object... arguments) {
		super(by, value, arguments);
	}
	
	public EditableElement(BaseElement parentElement, FindBy by, String value, Object... arguments) {
		super(parentElement, by, value, arguments);
	}
	
	public EditableElement Dynamic(Object... arguments)
	{
		super.Dynamic(arguments);
		return this;
	}
	
	/**
	 * @author Dung.Vu: Clear element's value before entering the new one.
	 * @param value
	 */
	public void enter(Object value) {
		clear();
		sendKeys(value.toString());
	}
	
	/**
	 * @author Vu.Luu: set element's value.
	 * @param value
	 */
	public void setValue(Object value) {
		logger.debug(String.format("Set value '%s' to %s", value, getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	String js = String.format("arguments[0].value='%s';", value);
		    	waitForCondition(ElementStatus.PRESENT, Constant.ElementWaitingTime, true);
				DriverUtils.executeJavaScript(js, getElement());
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to set value for control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}
	
	/**
	 * @author Vu.Luu: clear element's value.
	 */
	public void clear() {
		logger.debug(String.format("Clear %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	waitForCondition(ElementStatus.PRESENT, Constant.ElementWaitingTime, true);
		    	getElement().clear();
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to clear value for control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}
}
