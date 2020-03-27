package pages.VietJet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedCondition;

import datatype.BookingInfo;
import datatype.FareItem;
import driver.manager.DriverUtils;
import element.base.web.Element;
import helper.LocatorHelper;
import utils.common.Constants;
import utils.helper.Logger;

public class SelectFarePage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH, getClass().getSimpleName());

	//Element
	protected Element eleDepFareLow = new Element(locator.getLocator("eleDepFareLow"));
	protected Element eleCheapestDepFare = new Element(locator.getLocator("eleCheapestDepFare"));
	protected Element eleMonthCheapestDepFare = new Element(locator.getLocator("eleMonthCheapestDepFare"));
	protected Element eleSelectedDepMonthLbl = new Element(locator.getLocator("eleSelectedDepMonthLbl"));
	protected Element eleDepErrorCaption = new Element(locator.getLocator("eleDepErrorCaption"));
	protected Element eleRetFareLow = new Element(locator.getLocator("eleRetFareLow"));
	protected Element eleCheapestRetFare = new Element(locator.getLocator("eleCheapestRetFare"));
	protected Element eleMonthCheapestRetFare = new Element(locator.getLocator("eleMonthCheapestRetFare"));
	protected Element eleSelectedRetMonthLbl = new Element(locator.getLocator("eleSelectedRetMonthLbl"));
	protected Element eleRetErrorCaption = new Element(locator.getLocator("eleRetErrorCaption"));
	protected Element eleDepNextLnk = new Element(locator.getLocator("eleDepNextLnk"));
	protected Element eleDepPrevLnk = new Element(locator.getLocator("eleDepPrevLnk"));
	protected Element eleRetNextLnk = new Element(locator.getLocator("eleRetNextLnk"));
	protected Element eleRetPrevLnk = new Element(locator.getLocator("eleRetPrevLnk"));
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
	
	public int waitForNumberOfDepFareLowItemChanged(int numberOfPreFareLowItem) {
		eleSelectedDepMonthLbl.waitForClickable(30);
		List<Element> lstDepFareLow = eleDepFareLow.getWrapperElements();
		while(lstDepFareLow.size() == numberOfPreFareLowItem) {
			lstDepFareLow = eleDepFareLow.getWrapperElements();
		}
		return lstDepFareLow.size();
	}
	
	public int waitForNumberOfRetFareLowItemChanged(int numberOfPreFareLowItem) {
		eleSelectedDepMonthLbl.waitForClickable(30);
		
		if(eleRetErrorCaption.isDisplayed()) {
			return 0;
		} else {
			
		List<Element> lstRetFareLow = eleRetFareLow.getWrapperElements();
		while(lstRetFareLow.size() == numberOfPreFareLowItem) {
			lstRetFareLow = eleRetFareLow.getWrapperElements();
		}
		return lstRetFareLow.size();
		}
	}
	
	public FareItem getCheapestDepatureFareInNextMonths(int numberOfMonth) throws ParseException {
		int monthCheapestFare;
		int finalCheapestFare = 0;
		int numberOfFareLowItem = 0;
		String monthCheapestFareID = null;
		String finalCheapestFareID = null;
		Boolean finalCheapestFareExist = false;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");  
		FareItem finalCheapestFareItem = new FareItem();
		
		//Define Max Date to select fare. Min Date is today, Max Date is the date after <numberOfMonth> months)
		Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.MONTH, numberOfMonth);
	    Date maxDate = cal.getTime();
		
		//Search the cheapest departure fare in defined time frame
		for(int index=0; index < numberOfMonth; index++) {
			System.out.print("for loop search" + index + " \n");
			System.out.print("eleDepErrorCaption.isDisplayed(): " + eleDepErrorCaption.isDisplayed() + " \n");
			
			if(!eleDepErrorCaption.isDisplayed()) {
				//Get current number of FareLowItem
				List<Element> lstDepFareLow = eleDepFareLow.getWrapperElements();
				numberOfFareLowItem = lstDepFareLow.size();
				
				System.out.print("lstDepFareLow:" + numberOfFareLowItem + " \n");
				
				if(finalCheapestFareExist == false) {
					finalCheapestFare = convertFareFromStringToInt(lstDepFareLow.get(0).getText());
					finalCheapestFareID = eleMonthCheapestDepFare.getAttribute("id");
					finalCheapestFareExist = true;
				}
				
				monthCheapestFare = convertFareFromStringToInt(lstDepFareLow.get(0).getText());
				monthCheapestFareID = eleMonthCheapestDepFare.getAttribute("id");
				String strFareDate = monthCheapestFareID.substring(17, 25);
				Date fareDate = formatter.parse(strFareDate);
				
				if(monthCheapestFare < finalCheapestFare && fareDate.compareTo(maxDate) <= 0) {
					finalCheapestFare = monthCheapestFare;
					finalCheapestFareID = monthCheapestFareID;
				}
			}
			
			String selectedMonth = eleSelectedDepMonthLbl.getText();
			System.out.print("selectedMonth:" + selectedMonth + " \n");
			eleDepNextLnk.click();
			
			String afterSelectedMonth = eleSelectedDepMonthLbl.getText();
			System.out.print("afterSelectedMonth:" + afterSelectedMonth + " \n");
			while(selectedMonth.equals(afterSelectedMonth)) {
				afterSelectedMonth = eleSelectedDepMonthLbl.getText();
			}
		}
		
		System.out.print("finalCheapestFareDepID: " + finalCheapestFareID + "/n");
		
		//View month that contain the cheapest return fare
		if(finalCheapestFareID != null) {
			for(int index=0; index < numberOfMonth; index++) {
				Element eleCheapestFare = eleCheapestDepFare.Dynamic(finalCheapestFareID);
				if(!eleCheapestFare.isDisplayed()) {
					eleDepPrevLnk.click();
					System.out.print("eleDepPrevLnk.click(): " + index + " \n");
				} else {
					index = numberOfMonth;
				}
			}
			
			finalCheapestFareItem.setFareItem(finalCheapestFareID, finalCheapestFare);
		}
		
		return finalCheapestFareItem;
	}
	
	public FareItem getCheapestReturnFareInNextMonths(Date depDate, int numberOfMonth) throws ParseException {
		int monthCheapestFare;
		int finalCheapestFare = 0;
		int numberOfFareLowItem = 0;
		String monthCheapestFareID = null;
		String finalCheapestFareID = null;
		Boolean finalCheapestFareExist = false;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");  
		FareItem finalCheapestFareItem = new FareItem();
		
		//Define Min Date and Max Date to select return fare. Min Date is the departure date, Max Date is the date after <numberOfMonth> months from now)   
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.MONTH, numberOfMonth);
	    Date maxDate = cal.getTime();
	    Date minDate = depDate;
		
		//Search the cheapest return fare in defined time frame
		for(int index=0; index < numberOfMonth; index++) {
			System.out.print("for loop search" + index + " \n");
			
			if(!eleRetErrorCaption.isDisplayed()) {
				//Get current number of FareLowItem
				List<Element> lstRetFareLow = eleRetFareLow.getWrapperElements();
				numberOfFareLowItem = lstRetFareLow.size();
				
				System.out.print("lstRetFareLow:" + numberOfFareLowItem + " \n");
				
				if(finalCheapestFareExist == false) {
					finalCheapestFare = convertFareFromStringToInt(lstRetFareLow.get(0).getText());
					finalCheapestFareID = eleMonthCheapestRetFare.Dynamic(1).getAttribute("id");
					finalCheapestFareExist = true;
				}
				
				for(int i = 0; i < numberOfFareLowItem; i++)
				{
					monthCheapestFare = convertFareFromStringToInt(lstRetFareLow.get(i).getText());
					monthCheapestFareID = eleMonthCheapestRetFare.Dynamic(i+1).getAttribute("id");
					String strFareDate = monthCheapestFareID.substring(17, 25);
					Date fareDate = formatter.parse(strFareDate);
					
					if(monthCheapestFare < finalCheapestFare && fareDate.compareTo(maxDate) <= 0 && fareDate.compareTo(minDate) >= 0) {
						finalCheapestFare = monthCheapestFare;
						finalCheapestFareID = monthCheapestFareID;
					}
				}
			}
			
			String selectedMonth = eleSelectedRetMonthLbl.getText();
			eleRetNextLnk.click();
			
			String afterSelectedMonth = eleSelectedRetMonthLbl.getText();
			while(selectedMonth.equals(afterSelectedMonth)) {
				
			}
		}
		
		System.out.print("finalCheapestFareRetID: " + finalCheapestFareID + "/n");
		
		//View month that contain the cheapest return fare
		if(finalCheapestFareID != null) {
			for(int index=0; index < numberOfMonth; index++) {
				Element eleCheapestFare = eleCheapestRetFare.Dynamic(finalCheapestFareID);
				if(!eleCheapestFare.isDisplayed()) {
					eleRetPrevLnk.click();
				} else {
					index = numberOfMonth;
				}
			}
			
			finalCheapestFareItem.setFareItem(finalCheapestFareID, finalCheapestFare);
		}
		
		return finalCheapestFareItem;
		
	}
	
	public BookingInfo selectCheapestFareForReturnFightFareInNextMonths(int numberOfMonth, BookingInfo booking) throws ParseException {
		FareItem depFareItem = getCheapestDepatureFareInNextMonths(numberOfMonth);
		FareItem retFareItem = getCheapestReturnFareInNextMonths(depFareItem.getDate(),numberOfMonth);
		eleDepFareLow.Dynamic(depFareItem.getID()).click();
		eleRetFareLow.Dynamic(retFareItem.getID()).click();
		eleContinueBtn.click();
		
		booking.setDepartDate(depFareItem.getDate());
		booking.setReturnDate(retFareItem.getDate());
		
		return booking;
	}
	
	public Boolean isFightInfoCorrect(BookingInfo actualBookingInfo) {
		
		eleDepFromLbl.waitForDisplayed(30);
		
		System.out.print("eleDepFromLbl: " + eleDepFromLbl.getText() + " vs " + actualBookingInfo.getOrigin().getValue() + " \n");
		System.out.print("eleDepToLbl: " + eleDepToLbl.getText() + " vs " + actualBookingInfo.getDestination().getValue() + " \n");
		System.out.print("eleRetFromLbl: " + eleRetFromLbl.getText() + " vs " + actualBookingInfo.getDestination().getValue() + " \n");
		System.out.print("eleRetToLbl: " + eleRetToLbl.getText() + " vs " + actualBookingInfo.getOrigin().getValue() + " \n");
		System.out.print("eleDisplayCurrencyLbl: " + eleDisplayCurrencyLbl.Dynamic(actualBookingInfo.getCurrency()).isDisplayed() + " \n");
		System.out.print("eleNumberOfAdults: " + eleNumberOfAdults.getText() + " vs " + actualBookingInfo.getNumberOfAdults() + " \n");
		System.out.print("eleNumberOfChildren: " + eleNumberOfChildren.getText() + " vs " + actualBookingInfo.getNumberOfChildens() + " \n");
		System.out.print("eleNumberOfInfants: " + eleNumberOfInfants.getText() + " vs " + actualBookingInfo.getNumberOfInfants() + " \n");
		
		return eleDepFromLbl.getText().contains(actualBookingInfo.getOrigin().getValue())
				&& eleDepToLbl.getText().contains(actualBookingInfo.getDestination().getValue())
				&& eleRetFromLbl.getText().contains(actualBookingInfo.getDestination().getValue())
				&& eleRetToLbl.getText().contains(actualBookingInfo.getOrigin().getValue())
				&& eleDisplayCurrencyLbl.Dynamic(actualBookingInfo.getCurrency()).isDisplayed()
				&& eleNumberOfAdults.getText().contains(Integer.toString(actualBookingInfo.getNumberOfAdults()))
				&& eleNumberOfChildren.getText().contains(Integer.toString(actualBookingInfo.getNumberOfChildens()))
				&& eleNumberOfInfants.getText().contains(Integer.toString(actualBookingInfo.getNumberOfInfants()));
	}
	
	public String getPageTitle() {
		eleContinueBtn.waitForClickable(30);
		return DriverUtils.getTitle();
	}
}
