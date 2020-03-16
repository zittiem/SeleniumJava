package pages.VietJet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import driver.manager.DriverUtils;

public class HomePage {
	// Elements
	WebElement cbbOrigin = DriverUtils.findElement(By.id("select2-selectOrigin-container"));
	
	public HomePage()
	{
		
	}
	
	// Methods
	public void selectOrigin(String origin) {
		Select select = new Select(cbbOrigin);
        select.deselectAll();
        select.selectByVisibleText(origin);
	}
}
