package element.base;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import driver.manager.DriverManager;
import element.setting.ElementStatus;

public interface IWaiter extends ILocator {
	static Logger cLOG = Logger.getLogger(IWaiter.class);
	
	public default WebElement waitForCondition(ElementStatus condition, int timeOut) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOut);
			
			switch (condition.getValue()) {
			case "visibilityOf":
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator()));
				break;
			case "elementToBeClickable":
				element = wait.until(ExpectedConditions.elementToBeClickable(getLocator()));
				break;
			case "visibilityOfElementLocated":
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator()));
				break;
			case "presenceOfElementLocated":
				element = wait.until(ExpectedConditions.presenceOfElementLocated(getLocator()));
				break;
			default:
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
	public default WebElement waitForPresent(int timeOut) {
		return waitForCondition(ElementStatus.PRESENT, timeOut);
	}

	/**
	 * @author Dung.Vu: Wait for element click-able in a specific time.
	 * @param timeOut -> In Second.
	 */
	public default WebElement waitForClickable(int timeOut) {
		return waitForCondition(ElementStatus.CLICKABLE, timeOut);
	}

	/**
	 * @author Dung.Vu: Wait for element displayed in a specific time.
	 * @param timeOut
	 * @return element
	 */
	public default WebElement waitForDisplay(int timeOut) {
		return waitForCondition(ElementStatus.DISPLAY, timeOut);
	}

	/**
	 * @author Dung.Vu: Wait for all elements are presented in specific time out
	 * @param timeOut
	 * @return List of Elements
	 */
	public default List<WebElement> waitForAllElementsPresent(int timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOut);
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getLocator()));
		} catch (Exception error) {
			cLOG.error(String.format("Exception! - Error with element '%s': %s", getLocator().toString(),
					error.getMessage()));
			throw error;
		}
	}
	
//	@SuppressWarnings("unchecked")
//	public default WebElement waitForCondition(ElementStatus condition, int timeOut) {
//		try {
//			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOut);
//			
//			Class<?> clzz = Class.forName("org.openqa.selenium.support.ui.ExpectedConditions");
//			Method method = clzz.getMethod(condition.getValue(), By.class);
//			
//			return wait.until((ExpectedCondition<WebElement>)method.invoke(null, getLocator()));
//		} 
//		catch (Exception error) {
//			cLOG.error(String.format("Exception! - Error with element '%s': %s", getLocator().toString(),
//					error.getMessage()));
//			if (error instanceof TimeoutException || error instanceof WebDriverException)
//			{
//				throw error;
//			}
//			else
//			{
//				error.printStackTrace();
//			}
//		}
//	}
}
