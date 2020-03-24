package element.base;

import java.util.List;

import org.javatuples.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import element.setting.FindBy;

public interface IFinder {
	
	WebElement getElement();
	
	WebElement getChildElement(By locator);
	
	WebElement getChildElement(Pair<FindBy, String> locator);
	
	WebElement getChildElement(FindBy by, String value);
	
	WebElement getChildElement(String locator);
	
	List<WebElement> getElements();
	
	List<WebElement> getChildElements(By locator);
	
	List<WebElement> getChildElements(Pair<FindBy, String> locator);
	
	List<WebElement> getChildElements(FindBy by, String value);
	
	List<WebElement> getChildElements(String locator);
}
