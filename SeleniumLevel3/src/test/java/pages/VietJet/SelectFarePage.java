package pages.VietJet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import datatype.VietJet.Booking;
import datatype.VietJet.Enums;
import datatype.VietJet.FareItem;
import datatype.VietJet.Enums.FlightType;
import datatype.VietJet.Enums.LocationOption;
import datatype.VietJet.Enums.Month;
import driver.manager.DriverUtils;
import element.base.web.Element;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.Logger;
import utils.helper.ResourceHelper;

public class SelectFarePage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName, getClass().getSimpleName());

	//Element
	protected Element elesFare = new Element(locator.getLocator("elesFare"));
	protected Element elesFareID = new Element(locator.getLocator("elesFareID"));
	protected Element eleFareID = new Element(locator.getLocator("eleFareID"));
	protected Element eleSelectedMonthLbl = new Element(locator.getLocator("eleSelectedMonthLbl"));
	protected Element eleErrorCaption = new Element(locator.getLocator("eleErrorCaption"));
	protected Element eleNextLnk = new Element(locator.getLocator("eleNextLnk"));
	protected Element elePrevLnk = new Element(locator.getLocator("elePrevLnk"));
	protected Element eleContinueBtn = new Element(locator.getLocator("eleContinueBtn"));
	protected Element eleDepFromLbl = new Element(locator.getLocator("eleDepFromLbl"));
	protected Element eleDepToLbl = new Element(locator.getLocator("eleDepToLbl"));
	protected Element eleRetFromLbl = new Element(locator.getLocator("eleRetFromLbl"));
	protected Element eleRetToLbl = new Element(locator.getLocator("eleRetToLbl"));
	protected Element eleDisplayCurrencyLbl = new Element(locator.getLocator("eleDisplayCurrencyLbl"));
	protected Element eleNumberOfAdults = new Element(locator.getLocator("eleNumberOfAdults"));
	protected Element eleNumberOfChildren = new Element(locator.getLocator("eleNumberOfChildren"));
	protected Element eleNumberOfInfants = new Element(locator.getLocator("eleNumberOfInfants"));
	
	//Method
	public int convertFareFromStringToInt(String fare) {
		int intFare = Integer.parseInt(fare.replace(" VND", "").replace(",", ""));
		return intFare;
	}
	
	public Boolean isFightInfoCorrect(Booking actualBooking) {
		
		eleDepFromLbl.waitForDisplayed(30);
		
		System.out.print("eleDepFromLbl: " + eleDepFromLbl.getText() + " vs " + LocationOption.getValue(actualBooking.getDepartureFrom()) + " \n");
		System.out.print("eleDepToLbl: " + eleDepToLbl.getText() + " vs " + LocationOption.getValue(actualBooking.getDepartureTo()) + " \n");
		System.out.print("eleRetFromLbl: " + eleRetFromLbl.getText() + " vs " + LocationOption.getValue(actualBooking.getReturnFrom()) + " \n");
		System.out.print("eleRetToLbl: " + eleRetToLbl.getText() + " vs " + LocationOption.getValue(actualBooking.getReturnTo()) + " \n");
		System.out.print("eleDisplayCurrencyLbl: " + eleDisplayCurrencyLbl.generateDynamic(actualBooking.getCurrency()).isDisplayed() + " \n");
		System.out.print("eleNumberOfAdults: " + eleNumberOfAdults.getText() + " vs " + actualBooking.getNumberOfAdults() + " \n");
		System.out.print("eleNumberOfChildren: " + eleNumberOfChildren.getText() + " vs " + actualBooking.getNumberOfChildren() + " \n");
		System.out.print("eleNumberOfInfants: " + eleNumberOfInfants.getText() + " vs " + actualBooking.getNumberOfInfants() + " \n");
		
		return eleDepFromLbl.getText().contains(LocationOption.getValue(actualBooking.getDepartureFrom()))
				&& eleDepToLbl.getText().contains(LocationOption.getValue(actualBooking.getDepartureTo()))
				&& eleRetFromLbl.getText().contains(LocationOption.getValue(actualBooking.getReturnFrom()))
				&& eleRetToLbl.getText().contains(LocationOption.getValue(actualBooking.getReturnTo()))
				&& eleDisplayCurrencyLbl.generateDynamic(actualBooking.getCurrency()).isDisplayed()
				&& eleNumberOfAdults.getText().contains(Integer.toString(actualBooking.getNumberOfAdults()))
				&& eleNumberOfChildren.getText().contains(Integer.toString(actualBooking.getNumberOfChildren()))
				&& eleNumberOfInfants.getText().contains(Integer.toString(actualBooking.getNumberOfInfants()));
	}
	
	public String getPageTitle() {
		eleContinueBtn.waitForClickable(90);
		return DriverUtils.getTitle();
	}
	
	public void submitPage() {
		eleContinueBtn.moveToElement();
		eleContinueBtn.click();
	}

	public Date getDateFromFareId(String fareID) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");  
		String strFareDate = fareID.substring(17, 25);
		Date fareDate = formatter.parse(strFareDate);
		return fareDate;
	}
	
	public List<FareItem> getFareItemsInDefinedTimeFrame(Enums.FlightType fareItemType, Date minDate, Date maxDate) throws ParseException{
		List<FareItem> lstFareItem = new ArrayList<FareItem>();
		
		elesFare = elesFare.generateDynamic(fareItemType);
		elesFareID = elesFareID.generateDynamic(fareItemType);
	 	eleSelectedMonthLbl = eleSelectedMonthLbl.generateDynamic(fareItemType);
	 	eleNextLnk = eleNextLnk.generateDynamic(fareItemType);
	 	elePrevLnk = elePrevLnk.generateDynamic(fareItemType);
	 	eleErrorCaption = eleErrorCaption.generateDynamic(fareItemType);
	 	
	 	eleSelectedMonthLbl.waitForDisplayed(30);
	 	
		@SuppressWarnings("deprecation")
		int maxMonth = maxDate.getMonth();
		int selectedMonth = Enums.Month.getKey(eleSelectedMonthLbl.getText());
		
		System.out.print("maxMonth: " + maxMonth + " \n");
		System.out.print("selectedMonth: " + selectedMonth + " \n");
		
		while(selectedMonth <= maxMonth) {
			if(!eleErrorCaption.isDisplayed()) {
				
				List<Element> lstFareId = elesFareID.getWrapperElements();
				List<Element> lstFare = elesFare.getWrapperElements();
				
				for(int i=0; i<lstFareId.size(); i++) {
					String fareId = lstFareId.get(i).getAttribute("id");
					Date fareDate = getDateFromFareId(fareId);
					int fare = convertFareFromStringToInt(lstFare.get(i).getText());
					
					if(fareDate.compareTo(minDate)>=0 && fareDate.compareTo(maxDate)<=0) {
						FareItem fareItem = new FareItem(fareId, fareDate, fare);
						lstFareItem.add(fareItem);
					}
				}
			} 
			
			String strSelectedMonth = eleSelectedMonthLbl.getText();
			System.out.print("selectedMonth:" + selectedMonth + " \n");
			eleNextLnk.click();
			eleSelectedMonthLbl.waitForTextChanged(strSelectedMonth, 10);
			
			selectedMonth = Enums.Month.getKey(eleSelectedMonthLbl.getText());
			System.out.print("lst" + fareItemType + "FareItem.size():" + lstFareItem.size() + " \n");
		} 
		
		while(elePrevLnk.isDisplayed()) {
			String strSelectedMonth = eleSelectedMonthLbl.getText();
			elePrevLnk.click();
			eleSelectedMonthLbl.waitForTextChanged(strSelectedMonth, 10);
		}
		
		return lstFareItem;
	}
	
	public void selectFareItem(Enums.FlightType fareItemType, FareItem fareItem) {
		@SuppressWarnings("deprecation")
		int fareMonth = fareItem.getDate().getMonth();
		int selectedMonth = Month.getKey(eleSelectedMonthLbl.generateDynamic(fareItemType).getText());
		
		while(fareMonth > selectedMonth) {
			String strSelectedMonth = eleSelectedMonthLbl.getText();
			elePrevLnk.click();
			eleSelectedMonthLbl.waitForTextChanged(strSelectedMonth, 10);
			selectedMonth = Month.getKey(eleSelectedMonthLbl.generateDynamic(fareItemType).getText());
		}
		
		while(fareMonth < selectedMonth) {
			String strSelectedMonth = eleSelectedMonthLbl.getText();
			eleNextLnk.click();
			eleSelectedMonthLbl.waitForTextChanged(strSelectedMonth, 10);
			selectedMonth = Month.getKey(eleSelectedMonthLbl.generateDynamic(fareItemType).getText());
		}
		
		String dynamicFareId = fareItemType + fareItem.getID().substring(17, 25);
		eleFareID.generateDynamic(dynamicFareId).click();
	}

	public void selectReturnFlightCheapestFareInDefinedTimeFrame(Date minDate, Date maxDate) throws ParseException{
		List<FareItem> lstDepFareItem = getFareItemsInDefinedTimeFrame(FlightType.Dep, minDate, maxDate);
		List<FareItem> lstRetFareItem = getFareItemsInDefinedTimeFrame(FlightType.Ret, minDate, maxDate);
		
		int totalCheapestFare = 0;
		int finalTotalCheapeastFare = 0;
		Boolean finalTotalCheapestFareExist = false;
		FareItem finalDepFareItem = new FareItem();
		FareItem finalRetFareItem = new FareItem();
		
		// Filter combo cheapest fare for the return flight
		if(lstDepFareItem.size() != 0 || lstRetFareItem.size() != 0) {
			for(int i=0; i<lstDepFareItem.size(); i++) {
				Date depFareDate = lstDepFareItem.get(i).getDate();
				int depFare = lstDepFareItem.get(i).getFare();
				
				// Set return date (after depature date 3 days)
				Calendar c = Calendar.getInstance(); 
				c.setTime(depFareDate); 
				c.add(Calendar.DATE, 3);
				Date expRetFareDate = c.getTime();	
				
				// Filter combo cheapest fare for the return flight
				for(int j=0; j<lstRetFareItem.size(); j++) {
					Date retFareDate = lstRetFareItem.get(j).getDate();
					int retFare = lstRetFareItem.get(j).getFare();
					
					System.out.print("retFareDate:" + retFareDate + " \n");
					System.out.print("expRetFareDate:" + expRetFareDate + " \n");
					System.out.print("retFareDate.compareTo(expRetFareDate):" + retFareDate.compareTo(expRetFareDate) + " \n");
					
					if(retFareDate.compareTo(expRetFareDate) == 0) {
						if(finalTotalCheapestFareExist == false) {
							finalTotalCheapeastFare = depFare + retFare;
							finalDepFareItem = lstDepFareItem.get(i);
							finalRetFareItem = lstRetFareItem.get(j);
							finalTotalCheapestFareExist = true;
						} else {
							totalCheapestFare = depFare + retFare;
							if (finalTotalCheapeastFare > totalCheapestFare) {
								finalTotalCheapeastFare = totalCheapestFare;
								finalDepFareItem = lstDepFareItem.get(i);
								finalRetFareItem = lstRetFareItem.get(j);
							}
						}
						break;
					}
				}
			}	
		} else {
			Logger.warning("No ticket is available.");
		}
		
		System.out.print("finalDepFareItem:" + finalDepFareItem.getID() + " \n");
		System.out.print("finalRetFareItem:" + finalRetFareItem.getID() + " \n");
		System.out.print("finalTotalCheapeastFare:" + finalTotalCheapeastFare + " \n");
		
		// Select combo cheapest fare for the return flight
		selectFareItem(FlightType.Dep, finalDepFareItem);
		selectFareItem(FlightType.Ret, finalRetFareItem);
	}
}
