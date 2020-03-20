package element.base;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;

public interface IStatus {
	
	boolean isDisplayed();
	
	boolean isDisplayed(int timeOutInSeconds);
	
	boolean isEnabled();
	
	boolean isEnabled(int timeOutInSeconds);
	
	boolean isSelected();
	
	boolean isSelected(int timeOutInSeconds);
	
	String getCssValue(String propertyName);
	
	String getAttribute(String attributeName);
	
	String getText();
	
	String getValue();
	
	String getTagName();
	
	Point getLocation();
	
	Dimension getSize();
	
	Rectangle getRect();
	
}
