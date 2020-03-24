package element.base.mobile;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.javatuples.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import driver.manager.DriverUtils;
import element.setting.FindBy;
import element.setting.SwipeDirection;
import helper.LocatorHelper;
import io.appium.java_client.MobileElement;

public class MobileNativeElement implements IMobileFinder, IMobileWaiter, IMobileAction, IMobileInfo {
	private static Logger logger = Logger.getLogger(MobileNativeElement.class);

	private By byLocator;
	private Pair<FindBy, String> pairLocator;
	private MobileNativeElement parentElement;

	public MobileNativeElement(By locator) {
		this.byLocator = locator;
	}
	
	public MobileNativeElement(String locator) {
		this.byLocator = getByLocator(locator);
		this.pairLocator = LocatorHelper.getPairLocator(locator);
	}
	
	public MobileNativeElement(MobileNativeElement parentElement, String locator) {
		this.byLocator = getByLocator(locator);
		this.pairLocator = LocatorHelper.getPairLocator(locator);
		this.parentElement = parentElement;
	}
	
	public MobileNativeElement(String locator, Object... arguments) {
		this.byLocator = getByLocator(String.format(locator, arguments));
		this.pairLocator = LocatorHelper.getPairLocator(locator);
	}
	
	public MobileNativeElement(MobileNativeElement parentElement, String locator, Object... arguments) {
		this.byLocator = getByLocator(String.format(locator, arguments));
		this.pairLocator = LocatorHelper.getPairLocator(locator);
		this.parentElement = parentElement;
	}

	public MobileNativeElement(Pair<FindBy, String> locator) {
		this.byLocator = getByLocator(locator);
		this.pairLocator = locator;
	}
	
	public MobileNativeElement(MobileNativeElement parentElement, Pair<FindBy, String> locator) {
		this.byLocator = getByLocator(locator);
		this.pairLocator = locator;
		this.parentElement = parentElement;
	}
	
	public MobileNativeElement(Pair<FindBy, String> locator, Object... arguments) {
		this.byLocator = getByLocator(locator.getValue0(), String.format(locator.getValue1(), arguments));
		this.pairLocator = locator;
	}
	
	public MobileNativeElement(MobileNativeElement parentElement, Pair<FindBy, String> locator, Object... arguments) {
		this.byLocator = getByLocator(locator.getValue0(), String.format(locator.getValue1(), arguments));
		this.pairLocator = locator;
		this.parentElement = parentElement;
	}

	public MobileNativeElement(FindBy by, String value) {
		this.byLocator = getByLocator(by, value);
		this.pairLocator = new Pair<FindBy, String>(by, value);
	}
	
	public MobileNativeElement(MobileNativeElement parentElement, FindBy by, String value) {
		this.byLocator = getByLocator(by, value);
		this.pairLocator = new Pair<FindBy, String>(by, value);
		this.parentElement = parentElement;
	}

	public MobileNativeElement(FindBy by, String value, Object... arguments) {
		this.byLocator = getByLocator(by, String.format(value, arguments));
		this.pairLocator = new Pair<FindBy, String>(by, value);
	}
	
	public MobileNativeElement(MobileNativeElement parentElement, FindBy by, String value, Object... arguments) {
		this.byLocator = getByLocator(by, String.format(value, arguments));
		this.pairLocator = new Pair<FindBy, String>(by, value);
		this.parentElement = parentElement;
	}
	
	public MobileNativeElement Dynamic(Object... arguments)
	{
		if (this.pairLocator != null)
			this.byLocator = getByLocator(this.pairLocator.getValue0(), String.format(this.pairLocator.getValue1(), arguments));
		return this;
	}
	
	// ---------------------- Locator ---------------------------- //
	@Override
	public By getLocator() {
		return this.byLocator;
	}
	
	// ---------------------- Finder ---------------------------- //
	@Override
	public MobileElement getElement() {
		if (parentElement != null)
			return parentElement.getChildElement(getLocator());
		return (MobileElement) DriverUtils.findElement(getLocator());
	}

	@Override
	public MobileElement getChildElement(By locator) {
		return getElement().findElement(locator);
	}
	
	@Override
	public MobileElement getChildElement(Pair<FindBy, String> locator) {
		return getElement().findElement(getByLocator(locator));
	}
	
	@Override
	public MobileElement getChildElement(FindBy by, String value) {
		return getElement().findElement(getByLocator(by, value));
	}

	@Override
	public MobileElement getChildElement(String locator) {
		return getElement().findElement(getByLocator(locator));
	}

	@Override
	public List<MobileElement> getElements() {
		if (parentElement != null)
			return parentElement.getChildElements(getLocator());
		return DriverUtils.findElements(getLocator())
						  .stream()
						  .map(element -> (MobileElement) element)
						  .collect(Collectors.toList());
	}

	@Override
	public List<MobileElement> getChildElements(By locator) {
		return getElement().findElements(locator);
	}
	
	@Override
	public List<MobileElement> getChildElements(Pair<FindBy, String> locator) {
		return getElement().findElements(getByLocator(locator));
	}
	
	@Override
	public List<MobileElement> getChildElements(FindBy by, String value) {
		return getElement().findElements(getByLocator(by, value));
	}

	@Override
	public List<MobileElement> getChildElements(String locator) {
		return getElement().findElements(getByLocator(locator));
	}

	// ---------------------- Action ---------------------------- //
	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clickByJS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clickByAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doubleClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendKeys(CharSequence... keysToEnter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToElement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrollIntoView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrollIntoViewBottom() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void tap() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void longTap(int timeInSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dobuleTap() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipe(SwipeDirection direction, int range) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zoom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zoom(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		
	}

	// ---------------------- Status ---------------------------- //
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDisplayed(int timeOutInSeconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int timeOutInSeconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSelected(int timeOutInSeconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCssValue(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttribute(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getCenter() {
		// TODO Auto-generated method stub
		return null;
	}
}
