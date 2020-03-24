package pages.VietJet;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import element.base.web.Element;
import driver.manager.DriverUtils;
import helper.LocatorHelper;
import utils.common.Constants;

public class SelectFarePage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH, getClass().getSimpleName());
	
	//Element
	List<WebElement> lstDepFare = DriverUtils.findElements(By.xpath("//table[@id='ctrValueViewerDepGrid']//p[@class='vvFare']")); 
	List<WebElement> lstRetFare = DriverUtils.findElements(By.xpath("//table[@id='ctrValueViewerRetGrid']//p[@class='vvFare']")); 
	Element lnkDepNext = new Element(locator.getLocator("rbxRoundTrip"));
	Element lnkDepPrev = new Element(locator.getLocator("rbxRoundTrip"));
	Element lnkRetNext = new Element(locator.getLocator("rbxRoundTrip"));
	Element lnkRetPrev = new Element(locator.getLocator("rbxRoundTrip"));
	Element lblSelectedMonth = new Element(locator.getLocator("rbxRoundTrip"));
	Element tdCheapestFare = new Element(locator.getLocator("rbxRoundTrip"));
	
	
	//Method
	public int convertFareFromStringToInt(String fare) {
		int intFare = Integer.parseInt(fare.replace(" VND", "").replace(",", ""));
		return intFare;
	}
	
	public void selectCheapestDepFareInNextMonths(int numberOfMonth) {
		int index = 1;
		int cheapestFare;
		String fareItem = "(//table[@id='ctrValueViewerDepGrid']//p[@class='vvFare'])[%s]";
		String currentFareItem = null;
		int currentFare;
		
		while (index < numberOfMonth) {
			lnkDepNext.click();
			if(lstDepFare.size()>0) {
				cheapestFare = convertFareFromStringToInt(lstDepFare.get(0).getText());
				for(int i=0; i<lstDepFare.size(); i++)
				{
					currentFare = convertFareFromStringToInt(lstDepFare.get(i).getText());
					if(currentFare < cheapestFare) {
						cheapestFare = currentFare;
						currentFareItem = String.format(fareItem, i+1);
					}
				}
			}
		}
		
		if(currentFareItem != null) {
			WebElement cheapestFareItem = DriverUtils.findElement(By.xpath(currentFareItem));
			while(!cheapestFareItem.isDisplayed()) {
				lnkDepPrev.click();
			}
			cheapestFareItem.click();
		}
	}
}
