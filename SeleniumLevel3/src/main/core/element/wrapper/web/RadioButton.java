package element.wrapper.web;

import org.javatuples.Pair;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import element.base.web.Element;
import element.setting.FindBy;

public class RadioButton extends Element {
	private static Logger logger = Logger.getLogger(RadioButton.class);

	public RadioButton(By locator) {
		super(locator);
	}
	
	public RadioButton(String locator) {
		super(locator);
	}
	
	public RadioButton(Element parentElement, String locator) {
		super(parentElement, locator);
	}
	
	public RadioButton(String locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public RadioButton(Element parentElement, String locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public RadioButton(Pair<FindBy, String> locator) {
		super(locator);
	}
	
	public RadioButton(Element parentElement, Pair<FindBy, String> locator) {
		super(parentElement, locator);
	}
	
	public RadioButton(Pair<FindBy, String> locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public RadioButton(Element parentElement, Pair<FindBy, String> locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public RadioButton(FindBy by, String value) {
		super(by, value);
	}
	
	public RadioButton(Element parentElement, FindBy by, String value) {
		super(parentElement, by, value);
	}

	public RadioButton(FindBy by, String value, Object... arguments) {
		super(by, value, arguments);
	}
	
	public RadioButton(Element parentElement, FindBy by, String value, Object... arguments) {
		super(parentElement, by, value, arguments);
	}
	
	public RadioButton Dynamic(Object... arguments)
	{
		super.Dynamic(arguments);
		return this;
	}
	
	public List<RadioButton> getWrapperRadioButtons() {
		return getWrapperElements(RadioButton.class);
	}
	
	public void select() {
		logger.debug(String.format("Select %s", getLocator().toString()));
		if (!isSelected())
			click();
	}
}
