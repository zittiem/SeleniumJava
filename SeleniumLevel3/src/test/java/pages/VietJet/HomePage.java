package pages.VietJet;

import java.util.Date;

import datatype.FlightInfo;
import datatype.FlightInfo.FlightOption;
import datatype.FlightInfo.LocationOption;
import driver.manager.Element;
import driver.manager.Locator;
import driver.manager.SelectElement;
import driver.setting.FindElementBy;

public class HomePage {	
	Locator locator = new Locator(getClass().getSimpleName());
	
	// Elements
	protected Element rbxRoundTrip = new Element(locator.getLocate("rbxRoundTrip"));
	protected Element rbxRbOneWay = new Element(locator.getLocate("rbxRbOneWay"));	
	protected Element calDepartDate = new Element(locator.getLocate("calDepartDate"));
	protected Element calReturnDate = new Element(locator.getLocate("calReturnDate"));	
	protected Element txtNumberOfAdults = new Element(locator.getLocate("txtNumberOfAdults"));
	protected Element chxInfare = new Element(locator.getLocate("chxInfare"));
	protected Element txtPromoCode = new Element(locator.getLocate("txtPromoCode"));
	protected Element txtNumberOfChildrens = new Element(locator.getLocate("txtNumberOfChildrens"));
	protected Element txtNumberOfInfants = new Element(locator.getLocate("txtNumberOfInfants"));
	protected Element btnSearch = new Element(locator.getLocate("btnSearch"));
	protected SelectElement cbxOrigin = new SelectElement(locator.getLocate("cbxOrigin"));	
	protected SelectElement cbxCurrency = new SelectElement(locator.getLocate("cbxCurrency"));
	protected SelectElement cbxDestination = new SelectElement(locator.getLocate("cbxDestination"));
	
	// Select Location
	protected Element txtSearch = new Element(locator.getLocate("txtSearch"));
	protected Element liLocationItem = new Element(locator.getLocate("liLocationItem"));
	
	// Select Date
	protected Element linkDatePickerNavigation = new Element(locator.getLocate("linkDatePickerNavigation"));
	protected Element lblCurrentYear = new Element(locator.getLocate("lblCurrentYear"));	
	protected Element celDay = new Element(locator.getLocate("celDay"));
	protected SelectElement cbxMonth = new SelectElement(locator.getLocate("cbxMonth"));
	
	// Methods
	private void selectYear(int year) {
		int currentYear = Integer.parseInt(lblCurrentYear.getText());
		String vector = "";
		if (currentYear < year) {
			vector = "";
		} else if (currentYear > year) {
			vector = "";
		}
		while (currentYear != year) {
			lblCurrentYear.getElement(FindElementBy.xpath, vector).click();
			currentYear = Integer.parseInt(lblCurrentYear.getText());
		}
	}

	private void selectMonth(int month) {
		cbxMonth.selectByValue(Integer.toString(month - 1));
	}

	private void selectDay(int day) {
		celDay.getElement(FindElementBy.xpath, day).click();
	}

	public void selectFlightOption(FlightOption option) {
		if (option.getValue() == "One Way") {
			rbxRbOneWay.setRarioButton();
		} else {
			rbxRoundTrip.setRarioButton();
		}
	}

	public void selectOrigin(LocationOption location) {
		cbxOrigin.click();
		txtSearch.enter(location.getKey());
		liLocationItem.getElement(FindElementBy.xpath, location.getKey()).click();
	}

	public void selectDestination(LocationOption location) {
		cbxDestination.click();
		txtSearch.enter(location.getKey());
		liLocationItem.getElement(FindElementBy.xpath, location.getKey()).click();
	}

	private void selectCal(Date date) {
		selectYear(date.getYear());
		selectMonth(date.getMonth());
		selectDay(date.getDay());
	}

	public void selectDepartDate(Date date) {
		calDepartDate.click();
		selectCal(date);
	}

	public void selectReturnDate(Date date) {
		calReturnDate.click();
		selectCal(date);
	}

	public void selectCurrency(String currency) {
		if (cbxCurrency.getSelectedOption() != currency) {
			cbxCurrency.selectByText(currency);
		}
	}

	public void enterSearchData(FlightInfo flight) {
		selectFlightOption(flight.getFlightOption());
		selectOrigin(flight.getOrigin());
		selectDepartDate(flight.getDepartDate());
		selectDestination(flight.getDestination());
		selectReturnDate(flight.getReturnDate());
		selectCurrency(flight.getCurrency());
		txtNumberOfAdults.enter(flight.getNumberOfAdults());
		chxInfare.setCheckbox(flight.isLowestFare());
		txtPromoCode.enter(flight.getPromoCode());
		txtNumberOfChildrens.enter(flight.getNumberOfChildens());
		txtNumberOfInfants.enter(flight.getNumberOfInfants());
	}

	public SelectTravelOptionsPage submitSearchFlight(FlightInfo flight) {
		enterSearchData(flight);
		btnSearch.click();
		return new SelectTravelOptionsPage();
	}
	
	//Thanh test method-Start
}
