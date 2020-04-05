package element.base.web;

import org.javatuples.Pair;
import org.openqa.selenium.By;

import element.setting.FindBy;
import helper.LocatorHelper;

public interface ILocator {
	
	By getLocator();
	
	By getParentLocator();
	
	public default By getByLocator(FindBy by, String value) {
		switch (by) {
		case id:
			return By.id(value);
		case name:
			return By.name(value);
		case css:
			return By.cssSelector(value);
		case linkText:
			return By.linkText(value);
		case text:
			return By.xpath(String.format("//*[text()='%s']", value));
		case partialText:
			return By.xpath(String.format("//*[contains(text(), '%s')]", value));
		case xpath:
			return By.xpath(value);
		default:
			return By.xpath(value);
		}
	}
	
	public default By getByLocator(Pair<FindBy, String> locator) {
		return getByLocator(locator.getValue0(), locator.getValue1());
	}
	
	public default By getByLocator(String locator) {
		Pair<FindBy, String> pairLocator = LocatorHelper.getPairLocator(locator);
		return getByLocator(pairLocator.getValue0(), pairLocator.getValue1());
	}
}
