package driver.manager;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Stopwatch;

import driver.manager.BaseElement;
import driver.setting.ElementStatus;
import driver.setting.FindElementBy;
import helper.Constant;

public class BaseElement {
	private static Logger cLOG = Logger.getLogger(BaseElement.class);
	protected WebElement element = null;
	protected List<WebElement> elements = null;
	private By byLocator;

	protected By getLocator() {
		return this.byLocator;
	}

	public BaseElement(By locator) {
		this.byLocator = locator;
	}

	public BaseElement(String xPath) {
		this.byLocator = By.xpath(xPath);
	}

	public BaseElement(String by, String value) {
		this.byLocator = getByLocator(by, value);
	}

	public BaseElement(FindElementBy by, String value) {
		this.byLocator = getByLocator(by, value);
	}

	public BaseElement(FindElementBy by, String value, String text) {
		this.byLocator = getByLocator(by, String.format(value, text));
	}

	/**
	 * @author Dung.Vu: Support to Find element by () and provided values.
	 * @param by : property name
	 * @param    value: String
	 */
	public By getByLocator(String by, String value) {
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
	public By getByLocator(FindElementBy by, String value) {
		return getByLocator(by.getValue(), value);
	}

	// ----------------------Section Handling timing ----------------------------

	public WebElement waitForCondition(ElementStatus condition, int timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOut);
			switch (condition.getValue()) {
			case "visibilityOf":
				element = wait.until(ExpectedConditions.visibilityOf(element));
				break;
			case "elementToBeClickable":
				element = wait.until(ExpectedConditions.elementToBeClickable(element));
				break;
			case "visibilityOfElementLocated":
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator()));
				break;
			case "presenceOfElementLocated":
				element = wait.until(ExpectedConditions.presenceOfElementLocated(getLocator()));
				break;
			default:
				element = wait.until(ExpectedConditions.visibilityOf(element));
				break;
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with element '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return element;
	}

	/**
	 * @author Dung.Vu: Wait for element exist in a specific time.
	 * @param timeOut -> In Second.
	 */
	public WebElement waitForPresent(int timeOut) {
		return waitForCondition(ElementStatus.PRESENT, timeOut);
	}

	/**
	 * @author Dung.Vu: Wait for element click-able in a specific time.
	 * @param timeOut -> In Second.
	 */
	public WebElement waitForClickable(int timeOut) {
		return waitForCondition(ElementStatus.CLICKABLE, timeOut);
	}

	/**
	 * @author Dung.Vu: Wait for element displayed in a specific time.
	 * @param timeOut
	 * @return element
	 */
	public WebElement waitForDisplay(int timeOut) {
		return waitForCondition(ElementStatus.DISPLAY, timeOut);
	}

	/**
	 * @author Dung.Vu: Wait for all elements are presented in specific time out
	 * @param timeOut
	 * @return List of Elements
	 */
	private List<WebElement> waitForAllElementsPresent(int timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOut);
			elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getLocator()));
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with element '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return elements;
	}

	/**
	 * @author Dung.Vu: Move mouse to element
	 * @param timeOut
	 */
	public void moveToElement(int timeOut) {
		Stopwatch sw = Stopwatch.createStarted();
		try {
			new Actions(DriverManager.getDriver()).moveToElement(waitForDisplay(timeOut)).build().perform();
		} catch (StaleElementReferenceException e) {
			if (sw.elapsed(TimeUnit.SECONDS) <= (long) timeOut) {
				cLOG.warn(String.format("Try to move to the control %s again", getLocator().toString()));
				moveToElement(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
	}

	/**
	 * @author Dung.Vu: Wait for all elements are presented in specific time out
	 * @return list of Elements
	 */
	public List<WebElement> getWebElements(int timeOut) {
		return waitForAllElementsPresent(timeOut);
	}
	

	// From super
	// -------------------------------------------------------------------------
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		throw new UnsupportedOperationException("The method is not implemented");
	}

	/**
	 * @author Dung.Vu: click on element after waiting for element is click-able in
	 *         specific timeout
	 * @param timeOut
	 */
	public void click(int timeOut) {
		Stopwatch sw = Stopwatch.createStarted();
		try {
			waitForDisplay(timeOut).click();
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to click control %s again", getLocator().toString()));
				click(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
	}

	public void click() {
		click(Constant.ElementWaitingTime);
	}

	public void submit() {
		throw new UnsupportedOperationException("The method is not implemented");
	}

	/**
	 * @author Dung.Vu: Send keys to element after waiting for element displayed in
	 *         specific timeout
	 * @param timeOut
	 * @param keysToSend
	 */
	public void sendKeys(int timeOut, CharSequence... keysToSend) {
		Stopwatch sw = Stopwatch.createStarted();
		try {
			waitForDisplay(timeOut).sendKeys(keysToSend);
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to send key to the control %s again", getLocator().toString()));
				sendKeys(timeOut - (int) sw.elapsed(TimeUnit.SECONDS), keysToSend);
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
	}

	public void sendKeys(CharSequence... keysToSend) {
		sendKeys(Constant.ElementWaitingTime, keysToSend);
	}

	/**
	 * @author Dung.Vu: Clear value after waiting for element presence.
	 * @param timeOutInSeconds
	 */
	public void clear(int timeOut) {
		Stopwatch sw = Stopwatch.createStarted();
		try {
			waitForDisplay(timeOut).clear();
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to clear data %s again", getLocator().toString()));
				clear(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
	}

	public void clear() {
		clear(Constant.ElementWaitingTime);
	}

	public String getTagName() {
		throw new UnsupportedOperationException("The method is not implemented");
	}

	/**
	 * @author Dung.Vu get element's attribute after waiting for element present in
	 *         specific time
	 * @param timeOut
	 * @param attributeName
	 * @return return attribute value
	 */
	private String getAttribute(String attributeName, int timeOut) {
		String attribute = null;
		Stopwatch sw = Stopwatch.createStarted();
		try {
			attribute = waitForDisplay(timeOut).getAttribute(attributeName);
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to get Attribute of the control %s again", getLocator().toString()));
				getAttribute(attributeName, timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return attribute;
	}

	public String getAttribute(String name) {
		return getAttribute(name, Constant.ElementWaitingTime);
	}

	/**
	 * @author Dung.Vu: Verify whether Element is selected or not after waiting for
	 *         element present in a specific timeout in seconds.
	 * @param timeOut
	 * @return True/False
	 */
	public boolean isSelected(int timeOut) {
		boolean isSelected = false;
		Stopwatch sw = Stopwatch.createStarted();
		try {
			isSelected = waitForDisplay(timeOut).isSelected();
		} catch (StaleElementReferenceException e) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to check the control is selected %s again", getLocator().toString()));
				isSelected(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return isSelected;
	}

	public boolean isSelected() {
		return isSelected(Constant.ElementWaitingTime);
	}

	/**
	 * @author Dung.Vu: Verify whether Element is enabled or not after waiting for
	 *         element present in a specific timeout in seconds.
	 * @param timeOut
	 * @return True if Element is enabled
	 */
	public boolean isEnabled(int timeOut) {
		boolean isEnabled = false;
		Stopwatch sw = Stopwatch.createStarted();
		try {
			isEnabled = waitForDisplay(timeOut).isEnabled();
		} catch (StaleElementReferenceException e) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to check the control is enabled %s again", getLocator().toString()));
				isEnabled(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return isEnabled;
	}

	public boolean isEnabled() {
		return isEnabled(Constant.ElementWaitingTime);
	}

	/**
	 * @author Dung.Vu: Get element's text after waiting for element displayed in
	 *         specific time
	 * @param timeOut
	 * @return
	 */
	public String getText(int timeOut) {
		String text = null;
		Stopwatch sw = Stopwatch.createStarted();
		try {
			text = waitForDisplay(timeOut).getText();
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to get text of the control %s again", getLocator().toString()));
				getText(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return text;
	}

	public String getText() {
		return getText(Constant.ElementWaitingTime);
	}

	public List<WebElement> findElements(By by) {
		return findElements(by);
	}

	public WebElement findElement(By by) {
		return findElement(by);
	}

	/**
	 * @author Dung.Vu: Verify weather Element is displayed or not in a specific
	 *         timeout.
	 * @param timeOut
	 * @return True/False
	 */
	public boolean isDisplayed(int timeOut) {
		boolean isDisplayed = false;
		Stopwatch sw = Stopwatch.createStarted();
		try {
			isDisplayed = waitForDisplay(timeOut).isDisplayed();
		} catch (StaleElementReferenceException e) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to check the control is displayed %s again", getLocator().toString()));
				return isDisplayed(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return isDisplayed;
	}

	public boolean isDisplayed() {
		return isDisplayed(Constant.ElementWaitingTime);
	}

	/**
	 * @author Dung.Vu: Get element's location after waiting for element displayed
	 *         on screen in a specific timeout in seconds.
	 * @param timeOut
	 * @return Location
	 */
	public Point getLocation(int timeOut) {
		Point location = null;
		Stopwatch sw = Stopwatch.createStarted();
		try {
			location = waitForDisplay(timeOut).getLocation();
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to get text of the control %s again", getLocator().toString()));
				getLocation(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return location;
	}

	public Point getLocation() {
		return getLocation(Constant.ElementWaitingTime);
	}

	/**
	 * @author Dung.Vu: Get element's size after Waiting for element displayed on
	 *         screen in a specific timeout in seconds.
	 * @param timeOut
	 * @return Dimension
	 */
	public Dimension getSize(int timeOut) {

		Dimension size = null;
		Stopwatch sw = Stopwatch.createStarted();
		try {
			size = waitForDisplay(timeOut).getSize();
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to get text of the control %s again", getLocator().toString()));
				getSize(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return size;
	}

	public Dimension getSize() {
		return getSize(Constant.ElementWaitingTime);
	}

	/**
	 * @author Dung.Vu: Get element's Rectangle after Waiting for element displayed
	 *         on screen in a specific timeout in seconds.
	 * @param timeOut
	 * @return Rectangle
	 */
	public Rectangle getRect(int timeOut) {
		Rectangle rect = null;
		Stopwatch sw = Stopwatch.createStarted();
		try {
			rect = waitForDisplay(timeOut).getRect();
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to get text of the control %s again", getLocator().toString()));
				getRect(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return rect;
	}

	public Rectangle getRect() {
		return getRect(Constant.ElementWaitingTime);
	}

	/**
	 * @author Dung.Vu: Get element's CSS value after waiting for element displayed
	 *         on screen in a specific timeout in seconds.
	 * @param timeOut
	 * @param propertyName -> css property name
	 * @return css value
	 */
	public String getCssValue(int timeOut, String propertyName) {
		String cssValue = null;
		Stopwatch sw = Stopwatch.createStarted();
		try {
			cssValue = waitForDisplay(timeOut).getCssValue(propertyName);
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOut) {
				cLOG.warn(String.format("Try to get text of the control %s again", getLocator().toString()));
				getCssValue(timeOut - (int) sw.elapsed(TimeUnit.SECONDS), propertyName);
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return cssValue;
	}

	public String getCssValue(String propertyName) {
		return getCssValue(Constant.ElementWaitingTime, propertyName);
	}

}
