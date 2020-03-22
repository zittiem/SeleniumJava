package element.resource.web;

import org.javatuples.Pair;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import element.base.BaseElement;
import element.setting.FindBy;

public class Element extends BaseElement {
	private static Logger logger = Logger.getLogger(Element.class);

	public Element(By locator) {
		super(locator);
	}
	
	public Element(String locator) {
		super(locator);
	}
	
	public Element(BaseElement parentElement, String locator) {
		super(parentElement, locator);
	}
	
	public Element(String locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public Element(BaseElement parentElement, String locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public Element(Pair<FindBy, String> locator) {
		super(locator);
	}
	
	public Element(BaseElement parentElement, Pair<FindBy, String> locator) {
		super(parentElement, locator);
	}
	
	public Element(Pair<FindBy, String> locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public Element(BaseElement parentElement, Pair<FindBy, String> locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public Element(FindBy by, String value) {
		super(by, value);
	}
	
	public Element(BaseElement parentElement, FindBy by, String value) {
		super(parentElement, by, value);
	}

	public Element(FindBy by, String value, Object... arguments) {
		super(by, value, arguments);
	}
	
	public Element(BaseElement parentElement, FindBy by, String value, Object... arguments) {
		super(parentElement, by, value, arguments);
	}
	
	public Element Dynamic(Object... arguments)
	{
		super.Dynamic(arguments);
		return this;
	}
}
