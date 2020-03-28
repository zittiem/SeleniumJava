package element.wrapper.web;

import org.javatuples.Pair;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import element.base.web.Element;
import element.setting.FindBy;

public class Button extends Element {
	private static Logger logger = Logger.getLogger(Button.class);

	public Button(By locator) {
		super(locator);
	}
	
	public Button(String locator) {
		super(locator);
	}
	
	public Button(Element parentElement, String locator) {
		super(parentElement, locator);
	}
	
	public Button(String locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public Button(Element parentElement, String locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public Button(Pair<FindBy, String> locator) {
		super(locator);
	}
	
	public Button(Element parentElement, Pair<FindBy, String> locator) {
		super(parentElement, locator);
	}
	
	public Button(Pair<FindBy, String> locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public Button(Element parentElement, Pair<FindBy, String> locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public Button(FindBy by, String value) {
		super(by, value);
	}
	
	public Button(Element parentElement, FindBy by, String value) {
		super(parentElement, by, value);
	}

	public Button(FindBy by, String value, Object... arguments) {
		super(by, value, arguments);
	}
	
	public Button(Element parentElement, FindBy by, String value, Object... arguments) {
		super(parentElement, by, value, arguments);
	}
	
	public Button generateDynamic(Object... arguments)
	{
		super.generateDynamic(arguments);
		return this;
	}
	
	public List<Button> getWrapperButtons() {
		return getWrapperElements(Button.class);
	}
}
