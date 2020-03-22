package element.resource.web;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.Select;

import element.base.BaseElement;
import element.setting.ElementStatus;
import element.setting.FindBy;
import helper.Constant;

public class DropdownElement extends BaseElement {
	private static Logger logger = Logger.getLogger(DropdownElement.class);

	public DropdownElement(By locator) {
		super(locator);
	}
	
	public DropdownElement(String locator) {
		super(locator);
	}
	
	public DropdownElement(BaseElement parentElement, String locator) {
		super(parentElement, locator);
	}
	
	public DropdownElement(String locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public DropdownElement(BaseElement parentElement, String locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public DropdownElement(Pair<FindBy, String> locator) {
		super(locator);
	}
	
	public DropdownElement(BaseElement parentElement, Pair<FindBy, String> locator) {
		super(parentElement, locator);
	}
	
	public DropdownElement(Pair<FindBy, String> locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public DropdownElement(BaseElement parentElement, Pair<FindBy, String> locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public DropdownElement(FindBy by, String value) {
		super(by, value);
	}
	
	public DropdownElement(BaseElement parentElement, FindBy by, String value) {
		super(parentElement, by, value);
	}

	public DropdownElement(FindBy by, String value, Object... arguments) {
		super(by, value, arguments);
	}
	
	public DropdownElement(BaseElement parentElement, FindBy by, String value, Object... arguments) {
		super(parentElement, by, value, arguments);
	}
	
	public DropdownElement Dynamic(Object... arguments)
	{
		super.Dynamic(arguments);
		return this;
	}
	
	/**
	 * @author Dung.Vu
	 * @return select element
	 */
	protected Select selection(int timeOutInSeconds) {
		waitForCondition(ElementStatus.PRESENT, timeOutInSeconds, true);
		return new Select(getElement());
	}
	
	/**
	 * @author Dung.vu: Select Drop-down-list by text
	 * @param text
	 */
	public void selectByText(String text) {
		logger.debug(String.format("Select by text '%s' for %s", text, getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	selection(Constant.ElementWaitingTime).selectByVisibleText(text);
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to select by text for control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	/**
	 * @author Dung.vu: Select Drop-down-list by value
	 * @param value
	 */
	public void selectByValue(String value) {
		logger.debug(String.format("Select by value '%s' for %s", value, getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	selection(Constant.ElementWaitingTime).selectByValue(value);
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to select by value for control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}
	
	/**
	 * @author Dung.vu: Select Drop-down-list by index
	 * @param index
	 */
	public void selectByIndex(int index) {
		logger.debug(String.format("Select by index '%s' for %s", index, getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	selection(Constant.ElementWaitingTime).selectByIndex(index);
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to select by index for control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}

	/**
	 * @author Dung.vu: Select Drop-down-list by partial text
	 * @param value
	 */
	public void selectByPartText(String partialText) {
		
		logger.debug(String.format("Select by partial text '%s' for %s", partialText, getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	getOptions().parallelStream()
				.filter(option -> getAttribute("textContent").toLowerCase().contains(partialText.toLowerCase()))
				.findFirst().ifPresent(option -> selectByText(getAttribute("textContent")));
		    	return;
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to select by partialText for control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
	}
	
	/**
	 * @author Dung.Vu: Get list of Drop-down-list options
	 * @param timeOut: in second
	 * @return list options
	 */
	public List<String> getOptions() {
		logger.debug(String.format("Get Options of %s", getLocator().toString()));
		List<String> options = new ArrayList<String>();
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	options = selection(Constant.ElementWaitingTime).getOptions()
		    					  			.stream()
		    					  			.map(element -> element.getText())
		    					  			.collect(Collectors.toList());
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to get options for control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
		return options;
	}

	/**
	 * @author Dung.Vu: Get the selected item value
	 * @param timeOut
	 * @return Selected option
	 */
	public String getSelectedOption() {
		logger.debug(String.format("Get selected option of %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
		    tries++;
		    try {
		    	return selection(Constant.ElementWaitingTime).getFirstSelectedOption().getText();
		    } catch (StaleElementReferenceException staleEx) {
		    	if (tries == Constant.ElementRetryLimit)
		    		throw staleEx;
		    	logger.warn(String.format("Try to get selected option for control %s again", getLocator().toString()));
		    } catch (Exception e) {
		    	logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
		    	throw e;
		    }
		}
		return null;
	}
}
