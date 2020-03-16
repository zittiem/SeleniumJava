package pages.VietJet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import driver.manager.Driver;


public class HomePage {
	// Elements
	WebElement cbbOrigin = Driver.findElement(By.id("select2-selectOrigin-container"));
	
	// Methods
	public void selectOrigin(String origin) {
		Select select = new Select(cbbOrigin);
        select.deselectAll();
        select.selectByVisibleText(origin);
	}
}
