package element.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface IFinder {
	
	WebElement getElement();
	
	WebElement getChildElement(By locator);
	
	List<WebElement> getElements();
	
	List<WebElement> getChildElements(By locator);
}
