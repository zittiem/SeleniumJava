package element.resource.mobile;

import org.javatuples.Pair;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import element.mobile.BaseMobileElement;
import element.setting.FindBy;

public class MobileElement extends BaseMobileElement {
	private static Logger logger = Logger.getLogger(MobileElement.class);

	public MobileElement(By locator) {
		super(locator);
	}
	
	public MobileElement(String locator) {
		super(locator);
	}
	
	public MobileElement(BaseMobileElement parentElement, String locator) {
		super(parentElement, locator);
	}
	
	public MobileElement(String locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public MobileElement(BaseMobileElement parentElement, String locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public MobileElement(Pair<FindBy, String> locator) {
		super(locator);
	}
	
	public MobileElement(BaseMobileElement parentElement, Pair<FindBy, String> locator) {
		super(parentElement, locator);
	}
	
	public MobileElement(Pair<FindBy, String> locator, Object... arguments) {
		super(locator, arguments);
	}
	
	public MobileElement(BaseMobileElement parentElement, Pair<FindBy, String> locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public MobileElement(FindBy by, String value) {
		super(by, value);
	}
	
	public MobileElement(BaseMobileElement parentElement, FindBy by, String value) {
		super(parentElement, by, value);
	}

	public MobileElement(FindBy by, String value, Object... arguments) {
		super(by, value, arguments);
	}
	
	public MobileElement(BaseMobileElement parentElement, FindBy by, String value, Object... arguments) {
		super(parentElement, by, value, arguments);
	}
	
	public MobileElement Dynamic(Object... arguments)
	{
		super.Dynamic(arguments);
		return this;
	}
}
