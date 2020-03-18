package driver.manager;

import org.openqa.selenium.By;

import driver.setting.FindElementBy;
import helper.Constant;

public class Element extends BaseElement {

	/**
	 * @author Dung.Vu: Find element by input xpath values.
	 * @param by : FindElementBy list
	 * @param    xpath: String
	 */
	public Element(String xpath) {
		super(By.xpath(xpath));
	}

	/**
	 * @author Dung.Vu: Find element by and provided values.
	 * @param by : FindElementBy list
	 * @param    value: String
	 */
	public Element(String by, String value) {
		super(by, value);
	}

	/**
	 * @author Dung.Vu: Find element by and provided values.
	 * @param by : FindElementBy list
	 * @param    value: String
	 */
	public Element(FindElementBy by, String value) {
		super(by, value);
	}

	/**
	 * @author Dung.Vu: Get Dynamic Element.
	 * @param by : FindElementBy list
	 * @param    value: String
	 */
	public Element getElement(FindElementBy by, Object... text) {
		return new Element(by, String.format(super.getLocator().toString(), text));
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
	 * @author Dung.Vu: Set Check-box ON/OFF.
	 * @param value : True/False
	 */
	public void setCheckbox(Boolean value) {
		if (value = true) {
			while (!isSelected()) {
				click();
			}
		} else {
			while (isSelected()) {
				click();
			}
		}
	}

	/**
	 * @author Dung.Vu: Set Radio-Button ON
	 */
	public void setRarioButton() {
		while (!isSelected()) {
			click();
		}
	}

	/**
	 * @author Dung.Vu: Move mouse to element
	 * @param timeOut
	 */
	public void moveToElement() {
		moveToElement(Constant.ElementWaitingTime);
	}
}
