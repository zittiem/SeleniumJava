package pages.vietjet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import driver.manager.DriverUtils;

public class HomePage {
	// Elements
	WebElement cbbOrigin = DriverUtils.findElement(By.id("select2-selectOrigin-container"));
	
	public HomePage()
	{
		
	}
	
	// Methods
	public void checkControlDisplay() {
		Assert.assertTrue(cbbOrigin.isDisplayed());
	}
	
	public void checkControlNotDisplay() {
		Assert.assertEquals(cbbOrigin.isDisplayed(), "False");
	}
}
