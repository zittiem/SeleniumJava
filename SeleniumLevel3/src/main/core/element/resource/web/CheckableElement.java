package element.resource.web;

import org.javatuples.Pair;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import element.base.BaseElement;
import element.setting.FindBy;

public class CheckableElement extends BaseElement {
	private static Logger logger = Logger.getLogger(CheckableElement.class);

	public CheckableElement(By locator) {
		super(locator);
	}
	
	public CheckableElement(String locator) {
		super(locator);
	}
	
	public CheckableElement(BaseElement parentElement, String locator) {
		super(parentElement, locator);
	}
	
	public CheckableElement(String locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public CheckableElement(BaseElement parentElement, String locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public CheckableElement(Pair<FindBy, String> locator) {
		super(locator);
	}
	
	public CheckableElement(BaseElement parentElement, Pair<FindBy, String> locator) {
		super(parentElement, locator);
	}
	
	public CheckableElement(Pair<FindBy, String> locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public CheckableElement(BaseElement parentElement, Pair<FindBy, String> locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public CheckableElement(FindBy by, String value) {
		super(by, value);
	}
	
	public CheckableElement(BaseElement parentElement, FindBy by, String value) {
		super(parentElement, by, value);
	}

	public CheckableElement(FindBy by, String value, Object... arguments) {
		super(by, value, arguments);
	}
	
	public CheckableElement(BaseElement parentElement, FindBy by, String value, Object... arguments) {
		super(parentElement, by, value, arguments);
	}
	
	public CheckableElement Dynamic(Object... arguments)
	{
		super.Dynamic(arguments);
		return this;
	}
	
	public void check() {
		logger.debug(String.format("Check ON %s", getLocator().toString()));
		if (!isSelected())
			click();
	}
	
	public void uncheck() {
		logger.debug(String.format("Check OFF %s", getLocator().toString()));
		if (isSelected())
			click();
	}
	
	public void setState(boolean value) {
		logger.debug(String.format("Set state '%s' for %s", value, getLocator().toString()));
		if (value) {
			check();
		} else {
			uncheck();
		}
	}

	public void setStateForAll(boolean value) {
		logger.debug(String.format("Set state '%s' for all elements %s", value, getLocator().toString()));
		getElements().stream().filter(element -> element.isSelected() != value).forEach(element -> element.click());
	}
	
	public boolean isChecked() {
		return isSelected();
	}
}
