package element.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.google.common.base.Stopwatch;

import driver.setting.FindElementBy;
import element.base.BaseElement;
import helper.Constant;

public class SelectElement extends BaseElement {
	private static Logger cLOG = Logger.getLogger(BaseElement.class);

	public SelectElement(String xpath) {
		super(By.xpath(xpath));
	}

	/**
	 * @author Dung.Vu: Find element byType and provided values.
	 * @param by : FindElementBy list
	 * @param    value: String
	 */
	public SelectElement(FindElementBy by, String value) {
		super(by, value);
	}

	/**
	 * @author Dung.Vu
	 * @return select element
	 */
	protected Select selection(int timeOut) {
		return new Select(waitForDisplay(timeOut));
	}

	/**
	 * @author Dung.Vu: Select Drop-down-list by value in the special timeout
	 * @param       timeOut: in second
	 * @param value
	 */
	private void selectByValue(String value, int timeOut) {
		Stopwatch sw = Stopwatch.createStarted();
		try {
			selection(timeOut).selectByValue(value);
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) <= (long) timeOut) {
				cLOG.warn(String.format("Try to select the vale of the control %s again", getLocator().toString()));
				selectByValue(value, timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
	}

	/**
	 * @author Dung.vu: Select Drop-down-list by value in the default timeout
	 * @param value
	 */
	public void selectByValue(String value) {
		selectByValue(value, Constant.ElementWaitingTime);
	}

	/**
	 * @author Dung.vu: Select Drop-down-list by partial text
	 * @param value
	 */
	public void selectByPartText(String partialText) {
		getOptions().parallelStream()
				.filter(option -> getAttribute("textContent").toLowerCase().contains(partialText.toLowerCase()))
				.findFirst().ifPresent(option -> selectByText(getAttribute("textContent")));
	}

	/**
	 * @author Dung.vu: Select VietJet Calendar by partial text
	 * @param date
	 */
	public void selectVJCalendar(String date) {
		getOptions().parallelStream()
				.filter(option -> getAttribute("textContent").toLowerCase().contains(date.toLowerCase()))
				.findFirst().ifPresent(option -> selectByText(getAttribute("textContent")));
	}
	
	
	
	/**
	 * @author Dung.Vu: Get list of Drop-down-list options in specific time
	 * @param timeOut: in second
	 * @return list options
	 */
	private List<String> getOptions(int timeOut) {
		List<String> options = new ArrayList<String>();
		Stopwatch sw = Stopwatch.createStarted();
		try {
			List<WebElement> elementsList = selection(timeOut).getOptions();
			for (WebElement element : elementsList) {
				options.add(element.getText());
			}
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) <= (long) timeOut) {
				cLOG.warn(String.format("Try to get options of the control %s again", getLocator().toString()));
				getOptions(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return options;
	}

	/**
	 * @author Dung.Vu: Get list of Drop-down-list options in default time
	 * @return list options
	 */
	public List<String> getOptions() {
		return getOptions(Constant.ElementWaitingTime);
	}

	/**
	 * @author Dung.Vu: Get the selected item value in specific timeout
	 * @param timeOut
	 * @return Selected option
	 */
	private String getSelectedOption(int timeOut) {
		String selected = null;
		Stopwatch sw = Stopwatch.createStarted();
		try {
			selected = selection(timeOut).getFirstSelectedOption().getText();
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) <= (long) timeOut) {
				cLOG.warn(String.format("Try to get Selected Option of the control %s again", getLocator().toString()));
				getSelectedOption(timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
		return selected;
	}

	/**
	 * @author Dung.Vu: Get the selected item value in default timeout
	 * @return Selected option
	 */
	public String getSelectedOption() {
		return getSelectedOption(Constant.ElementWaitingTime);
	}

	/**
	 * @author Dung.Vu: Select Drop-down-list by text in the special timeout
	 * @param timeOut
	 * @param text
	 */
	private void selectByText(String text, int timeOut) {
		Stopwatch sw = Stopwatch.createStarted();
		try {
			selection(timeOut).selectByVisibleText(text);
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) <= (long) timeOut) {
				cLOG.warn(String.format("Try to get by text of the control %s again", getLocator().toString()));
				selectByText(text, timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
	}

	/**
	 * @author Dung.Vu: Select Drop-down-list by text in the default timeout
	 * @param text
	 */
	public void selectByText(String text) {
		selectByText(text, Constant.ElementWaitingTime);
	}

	/**
	 * @author Dung.Vu: select by index with specific timeout
	 * @param timeOut
	 * @param index
	 */
	public void selectByIndex(int index, int timeOut) {
		Stopwatch sw = Stopwatch.createStarted();
		try {
			selection(timeOut).selectByIndex(index);
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) <= (long) timeOut) {
				cLOG.warn(String.format("Try to select by index of the control %s again", getLocator().toString()));
				selectByIndex(index, timeOut - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with control '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
	}

	/**
	 * @author Dung.Vu: Select by index in default timeout
	 * @param index
	 */
	public void selectByIndex(int index) {
		selectByIndex(index, Constant.ElementWaitingTime);
	}
}
