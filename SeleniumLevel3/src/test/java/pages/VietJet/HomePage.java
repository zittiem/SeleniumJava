package pages.VietJet;

import java.util.Date;

import datatype.BookingInfo;
import datatype.BookingInfo.FlightOption;
import datatype.BookingInfo.LocationOption;
import element.resource.web.Element;
import element.resource.web.CheckableElement;
import element.resource.web.DropdownElement;
import element.resource.web.EditableElement;
import helper.LocatorHelper;
import utils.common.Constants;

public class HomePage {	
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH, getClass().getSimpleName());
	
	// Elements
	protected Element rbxRoundTrip = new Element(locator.getLocator("rbxRoundTrip"));
	protected Element rbxRbOneWay = new Element(locator.getLocator("rbxRbOneWay"));	
	protected Element calDepartDate = new Element(locator.getLocator("calDepartDate"));
	protected Element calReturnDate = new Element(locator.getLocator("calReturnDate"));	
	protected EditableElement txtNumberOfAdults = new EditableElement(locator.getLocator("txtNumberOfAdults"));
	protected CheckableElement chxInfare = new CheckableElement(locator.getLocator("chxInfare"));
	protected EditableElement txtPromoCode = new EditableElement(locator.getLocator("txtPromoCode"));
	protected EditableElement txtNumberOfChildrens = new EditableElement(locator.getLocator("txtNumberOfChildrens"));
	protected EditableElement txtNumberOfInfants = new EditableElement(locator.getLocator("txtNumberOfInfants"));
	protected Element btnSearch = new Element(locator.getLocator("btnSearch"));
	protected Element cbxOrigin = new Element(locator.getLocator("cbxOrigin"));	
	protected DropdownElement cbxCurrency = new DropdownElement(locator.getLocator("cbxCurrency"));
	protected DropdownElement cbxDestination = new DropdownElement(locator.getLocator("cbxDestination"));
	
	// Select Location
	protected EditableElement txtSearch = new EditableElement(locator.getLocator("txtSearch"));
	protected Element liLocationItem = new Element(locator.getLocator("liLocationItem"));
	
	// Select Date
	protected Element linkDatePickerNavigation = new Element(locator.getLocator("linkDatePickerNavigation"));
	protected Element lblCurrentYear = new Element(locator.getLocator("lblCurrentYear"));	
	protected Element celDay = new Element(locator.getLocator("celDay"));
	protected DropdownElement cbxMonth = new DropdownElement(locator.getLocator("cbxMonth"));
	
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
			lblCurrentYear.Dynamic(vector).click();
			currentYear = Integer.parseInt(lblCurrentYear.getText());
		}
	}

	private void selectMonth(int month) {
		cbxMonth.selectByValue(Integer.toString(month - 1));
	}

	private void selectDay(int day) {
		celDay.Dynamic(day).click();
	}

	public void selectFlightOption(FlightOption option) {
		if (option.getValue() == "One Way") {
			rbxRbOneWay.click();
		} else {
			rbxRoundTrip.click();
		}
	}

	public void selectOrigin(LocationOption location) {
		cbxOrigin.click();
		txtSearch.enter(location.getKey());
		liLocationItem.Dynamic(location.getKey()).click();
	}

	public void selectDestination(LocationOption location) {
		cbxDestination.click();
		txtSearch.enter(location.getKey());
		liLocationItem.Dynamic(location.getKey()).click();
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

	public void enterSearchData(BookingInfo flight) {
		selectFlightOption(flight.getFlightOption());
		selectOrigin(flight.getOrigin());
		selectDepartDate(flight.getDepartDate());
		selectDestination(flight.getDestination());
		selectReturnDate(flight.getReturnDate());
		selectCurrency(flight.getCurrency());
		txtNumberOfAdults.enter(flight.getNumberOfAdults());
		chxInfare.setState(flight.isLowestFare());
		txtPromoCode.enter(flight.getPromoCode());
		txtNumberOfChildrens.enter(flight.getNumberOfChildens());
		txtNumberOfInfants.enter(flight.getNumberOfInfants());
	}

	public SelectTravelOptionsPage submitSearchFlight(BookingInfo flight) {
		enterSearchData(flight);
		btnSearch.click();
		return new SelectTravelOptionsPage();
	}
	
	// Thanh test method-Start
	
	public SelectFarePage searchCheapestFlights(BookingInfo booking) {
		enterSearchData(booking);
		btnSearch.click();
		return new SelectFarePage();
	}
	
	// Thanh test method-End
}
