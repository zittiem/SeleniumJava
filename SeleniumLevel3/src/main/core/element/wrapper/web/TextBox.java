package element.wrapper.web;

import org.javatuples.Pair;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.setting.ElementStatus;
import element.setting.FindBy;
import helper.Constant;

public class TextBox extends Element {
	private static Logger logger = Logger.getLogger(TextBox.class);

	public TextBox(By locator) {
		super(locator);
	}

	public TextBox(String locator) {
		super(locator);
	}

	public TextBox(Element parentElement, String locator) {
		super(parentElement, locator);
	}

	public TextBox(String locator, Object... arguments) {
		super(locator, arguments);
	}

	public TextBox(Element parentElement, String locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public TextBox(Pair<FindBy, String> locator) {
		super(locator);
	}

	public TextBox(Element parentElement, Pair<FindBy, String> locator) {
		super(parentElement, locator);
	}

	public TextBox(Pair<FindBy, String> locator, Object... arguments) {
		super(locator, arguments);
	}

	public TextBox(Element parentElement, Pair<FindBy, String> locator, Object... arguments) {
		super(parentElement, locator, arguments);
	}

	public TextBox(FindBy by, String value) {
		super(by, value);
	}

	public TextBox(Element parentElement, FindBy by, String value) {
		super(parentElement, by, value);
	}

	public TextBox(FindBy by, String value, Object... arguments) {
		super(by, value, arguments);
	}

	public TextBox(Element parentElement, FindBy by, String value, Object... arguments) {
		super(parentElement, by, value, arguments);
	}

	public TextBox Dynamic(Object... arguments) {
		super.Dynamic(arguments);
		return this;
	}
	
	public List<TextBox> getWrapperTextboxes() {
		return getWrapperElements(TextBox.class);
	}

	/**
	 * @author Dung.Vu: Clear element's value before entering the new one.
	 * @param value
	 */
	public void enter(Object value) {
		if (value != null) {
			clear();
			sendKeys(value.toString());
		}
	}

	/**
	 * @author Vu.Luu: set element's value.
	 * @param value
	 */
	public void setValue(Object value) {
		if (value != null) {
			logger.debug(String.format("Set value '%s' to %s", value, getLocator().toString()));
			int tries = 0;
			while (tries < Constant.ElementRetryLimit) {
				tries++;
				try {
					String js = String.format("arguments[0].value='%s';", value);
					waitForCondition(ElementStatus.PRESENT, Constant.ElementWaitingTime, true);
					DriverUtils.executeJavaScript(js, getElement());
					return;
				} catch (StaleElementReferenceException staleEx) {
					if (tries == Constant.ElementRetryLimit)
						throw staleEx;
					logger.warn(String.format("Try to set value for control %s again", getLocator().toString()));
				} catch (Exception e) {
					logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
							e.getMessage()));
					throw e;
				}
			}
		}
	}

	/**
	 * @author Vu.Luu: clear element's value.
	 */
	public void clear() {
		logger.debug(String.format("Clear %s", getLocator().toString()));
		int tries = 0;
		while (tries < Constant.ElementRetryLimit) {
			tries++;
			try {
				waitForCondition(ElementStatus.PRESENT, Constant.ElementWaitingTime, true);
				getElement().clear();
				return;
			} catch (StaleElementReferenceException staleEx) {
				if (tries == Constant.ElementRetryLimit)
					throw staleEx;
				logger.warn(String.format("Try to clear value for control %s again", getLocator().toString()));
			} catch (Exception e) {
				logger.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
						e.getMessage()));
				throw e;
			}
		}
	}
}
