package element.base;

import org.openqa.selenium.By;

import element.setting.FindElementBy;

public interface ILocator {
	
	By getLocator();
	
	/**
	 * @author Dung.Vu: Support to Find element by () and provided values.
	 * @param by : property name
	 * @param    value: String
	 */
	public default By getByLocator(String by, String value) {
		switch (by) {
		case "css":
			return By.cssSelector(value);
		case "id":
			return By.id(value);
		case "link":
			return By.linkText(value);
		case "xpath":
			return By.xpath(value);
		case "text":
			return By.xpath(String.format("//*[contains(text(), '%s')]", value));
		case "name":
			return By.name(value);
		default:
			return By.xpath(value);
		}
	}

	/**
	 * @author Dung.Vu: Support to Find element by () and provided values.
	 * @param by : ElementStatus by
	 * @param    value: String
	 */
	public default By getByLocator(FindElementBy by, String value) {
		return getByLocator(by.getValue(), value);
	}
}
