package element.base.mobile;

import java.util.List;

import org.javatuples.Pair;
import org.openqa.selenium.By;

import element.setting.FindBy;
import io.appium.java_client.MobileElement;

public interface IMobileFinder extends IMobileLocator {
	
	MobileElement getElement();
	
	MobileElement getChildElement(By locator);
	
	MobileElement getChildElement(Pair<FindBy, String> locator);
	
	MobileElement getChildElement(FindBy by, String value);
	
	MobileElement getChildElement(String locator);
	
	List<MobileElement> getElements();
	
	List<MobileElement> getChildElements(By locator);
	
	List<MobileElement> getChildElements(Pair<FindBy, String> locator);
	
	List<MobileElement> getChildElements(FindBy by, String value);
	
	List<MobileElement> getChildElements(String locator);
}
