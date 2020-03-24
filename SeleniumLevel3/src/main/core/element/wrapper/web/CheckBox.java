package element.wrapper.web;

import org.javatuples.Pair;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import element.base.web.Element;
import element.setting.FindBy;

public class CheckBox extends Element {
	private static Logger logger = Logger.getLogger(CheckBox.class);

	public CheckBox(By locator) {
		super(locator);
	}
	
	public CheckBox(String locator) {
		super(locator);
	}
	
	public CheckBox(Element parentElement, String locator) {
		super(parentElement, locator);
	}
	
	public CheckBox(String locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public CheckBox(Element parentElement, String locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public CheckBox(Pair<FindBy, String> locator) {
		super(locator);
	}
	
	public CheckBox(Element parentElement, Pair<FindBy, String> locator) {
		super(parentElement, locator);
	}
	
	public CheckBox(Pair<FindBy, String> locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public CheckBox(Element parentElement, Pair<FindBy, String> locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public CheckBox(FindBy by, String value) {
		super(by, value);
	}
	
	public CheckBox(Element parentElement, FindBy by, String value) {
		super(parentElement, by, value);
	}

	public CheckBox(FindBy by, String value, Object... arguments) {
		super(by, value, arguments);
	}
	
	public CheckBox(Element parentElement, FindBy by, String value, Object... arguments) {
		super(parentElement, by, value, arguments);
	}
	
	public CheckBox Dynamic(Object... arguments)
	{
		super.Dynamic(arguments);
		return this;
	}
	
	public List<CheckBox> getWrapperCheckboxes() {
		return getWrapperElements(CheckBox.class);
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
