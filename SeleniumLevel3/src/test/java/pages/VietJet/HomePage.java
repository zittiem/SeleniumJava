package pages.VietJet;

import java.util.Date;

import datatype.BookingInfo;
import datatype.BookingInfo.FlightOption;
import datatype.BookingInfo.LocationOption;
import driver.manager.Element;
import driver.manager.SelectElement;
import driver.setting.FindElementBy;

public class HomePage {
	// Elements
	protected Element rbxRoundTrip = new Element(FindElementBy.id, "ctl00_UcRightV31_RbRoundTrip");
	protected Element rbxRbOneWay = new Element(FindElementBy.id, "ctl00_UcRightV31_RbOneWay");
	protected SelectElement cbxOrigin = new SelectElement(FindElementBy.id, "selectOrigin]");
	protected SelectElement cbxDestination = new SelectElement(FindElementBy.id, "selectDestination");
	protected Element calDepartDate = new Element(FindElementBy.id, "ctl00_UcRightV31_TxtDepartDate");
	protected Element calReturnDate = new Element(FindElementBy.id, "ctl00_UcRightV31_TxtReturnDate");
	protected SelectElement cbxCurrency = new SelectElement(FindElementBy.id, "ctl00_UcRightV31_CbbCurrency_TextBox");
	protected Element txtNumberOfAdults = new Element(FindElementBy.id, "ctl00_UcRightV31_CbbAdults_TextBox");
	protected Element chxInfare = new Element(FindElementBy.id, "ctl00_UcRightV31_ChkInfare");
	protected Element txtPromoCode = new Element(FindElementBy.id, "ctl00_UcRightV31_TxtPromoCode");
	protected Element txtNumberOfChildrens = new Element(FindElementBy.id, "ctl00_UcRightV31_CbbChildren_TextBox");
	protected Element txtNumberOfInfants = new Element(FindElementBy.id, "ctl00_UcRightV31_CbbInfants_TextBox");
	protected Element btnSearch = new Element(FindElementBy.id, "ctl00_UcRightV31_BtSearch");
	// Select Location
	protected Element txtSearch = new Element("//input [contains(@class,'search__field') and @type='search']");
	protected Element liLocationItem = new Element(
			"//li[contains(@class, 'results__option') and contains(text(),'%s')]");
	// Select Date
	protected Element linkDatePickerNavigation = new Element("//a[@class='ui-datepicker-%s ui-corner-all']");
	protected Element lblCurrentYear = new Element("//span[@class='ui-datepicker-year']");
	protected SelectElement cbxMonth = new SelectElement("//select[@class='ui-datepicker-month']");
	protected Element celDay = new Element("//table[@class='ui-datepicker-calendar']/tbody//td[./a[text()=%s]]");
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

	public void enterSearchData(BookingInfo flight) {
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

	public SelectTravelOptionsPage submitSearchFlight(BookingInfo flight) {
		enterSearchData(flight);
		btnSearch.click();
		return new SelectTravelOptionsPage();
	}

	// Thanh test method-Start

}
