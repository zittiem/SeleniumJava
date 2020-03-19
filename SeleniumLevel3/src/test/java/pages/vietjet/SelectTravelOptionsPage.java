package pages.vietjet;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import driver.manager.DriverUtils;

public class SelectTravelOptionsPage {
		// Elements
		List<WebElement> depTicketList = DriverUtils.findElements(By.xpath("//tr[contains(@id, 'gridTravelOptDep')]"));
		List<WebElement> retTicketList = DriverUtils.findElements(By.xpath("//tr[contains(@id, 'gridTravelOptRet')]"));
		
		public SelectTravelOptionsPage()
		{
			
		}
		
		// Methods

}
