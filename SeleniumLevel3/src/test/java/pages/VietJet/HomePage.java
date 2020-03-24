package pages.VietJet;

import java.util.Date;

import datatype.BookingInfo;
import datatype.BookingInfo.FlightOption;
import datatype.BookingInfo.LocationOption;
import datatype.LanguageType;
import element.base.web.Element;
import element.setting.FindBy;
import element.wrapper.web.CheckBox;
import element.wrapper.web.DropDown;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import utils.assertion.SoftAssertion;
import utils.common.Constants;
import utils.helper.DateTimeHelper;

public class HomePage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH, getClass().getSimpleName());

//	// Elements
//	protected CheckableElement rbxRoundTrip = new CheckableElement(locator.getLocator("rbxRoundTrip"));
//	protected CheckableElement rbxRbOneWay = new CheckableElement(locator.getLocator("rbxRbOneWay"));
//	protected Element calDepartDate = new Element(locator.getLocator("calDepartDate"));
//	protected Element calReturnDate = new Element(locator.getLocator("calReturnDate"));
//	protected EditableElement txtNumberOfAdults = new EditableElement(locator.getLocator("txtNumberOfAdults"));
//	protected CheckableElement chxInfare = new CheckableElement(locator.getLocator("chxInfare"));
//	protected EditableElement txtPromoCode = new EditableElement(locator.getLocator("txtPromoCode"));
//	protected EditableElement txtNumberOfChildrens = new EditableElement(locator.getLocator("txtNumberOfChildrens"));
//	protected EditableElement txtNumberOfInfants = new EditableElement(locator.getLocator("txtNumberOfInfants"));
//	protected Element btnSearch = new Element(locator.getLocator("btnSearch"));
//
//	protected Element eleOrigin = new Element(locator.getLocator("eleOrigin"));
//	protected Element eleDestination = new Element(locator.getLocator("eleDestination"));
//	protected DropdownElement cbxCurrency = new DropdownElement(locator.getLocator("cbxCurrency"));
//	
//	// Select Location
//	protected EditableElement txtSearch = new EditableElement(locator.getLocator("txtSearch"));
//	protected Element liLocationItem = new Element(locator.getLocator("liLocationItem"));
//
//	// Select Date
//	protected Element linkDatePickerNavigation = new Element(locator.getLocator("linkDatePickerNavigation"));
//	protected Element lblCurrentYear = new Element(locator.getLocator("lblCurrentYear"));
//	protected Element celDay = new Element(locator.getLocator("celDay"));
//	protected DropdownElement cbxMonth = new DropdownElement(locator.getLocator("cbxMonth"));
//
//	// Select Passengers
//	protected EditableElement txtNumberOfPassengers = new EditableElement(FindBy.id, "ctl00_UcRightV31_Cbb%s_TextBox");
//	protected Element btnNumberOfPassengers = new Element(FindBy.id, "ctl00_UcRightV31_Cbb%s_Button");
//	protected Element listNumberOfPassengersOption = new Element(FindBy.xpath,
//			"//ul[@id='ctl00_UcRightV31_Cbb%s_OptionList']/li[text()='%s']");
	// Methods
	// Elements

	protected DropDown cbxLanguage = new DropDown(FindBy.id, "ctl00_UcHeaderV31_DrLang");
	protected CheckBox rbxRoundTrip = new CheckBox(FindBy.id, "ctl00_UcRightV31_RbRoundTrip");
	protected CheckBox rbxRbOneWay = new CheckBox(FindBy.id, "ctl00_UcRightV31_RbOneWay");
	protected Element spanOrigin = new Element(FindBy.id, "select2-selectOrigin-container");
	protected Element spanDestination = new Element(FindBy.id, "select2-selectDestination-container");

	protected Element calDepartDate = new Element(FindBy.id, "ctl00_UcRightV31_TxtDepartDate");
	protected Element calReturnDate = new Element(FindBy.id, "ctl00_UcRightV31_TxtReturnDate");
	protected Element cbxCurrency = new Element(FindBy.id, "ctl00_UcRightV31_CbbCurrency_TextBox");
	protected CheckBox chxInfare = new CheckBox(FindBy.id, "ctl00_UcRightV31_ChkInfare");
	protected TextBox txtPromoCode = new TextBox(FindBy.id, "ctl00_UcRightV31_TxtPromoCode");
	protected Element btnSearch = new Element(FindBy.id, "ctl00_UcRightV31_BtSearch");

	// Select Passengers
	protected TextBox txtNumberOfPassengers = new TextBox(FindBy.id, "ctl00_UcRightV31_Cbb%s_TextBox");
	protected Element btnNumberOfPassengers = new Element(FindBy.id, "ctl00_UcRightV31_Cbb%s_Button");
	protected Element listNumberOfPassengersOption = new Element(FindBy.xpath,
			"//ul[@id='ctl00_UcRightV31_Cbb%s_OptionList']/li[text()='%s']");

	// Select Location
	protected TextBox txtSearch = new TextBox(FindBy.xpath,
			"//input [@class='select2-search__field' and @type='search']");
	protected Element liLocationItem = new Element(FindBy.xpath, "//li[contains(@id, '%s')]");
	// Select Date
	protected Element linkDatePickerNavigation = new Element(FindBy.xpath,
			"//a[@class='ui-datepicker-%s ui-corner-all']");
	protected Element lblCurrentYear = new Element(FindBy.xpath, "//span[@class='ui-datepicker-year']");
	protected DropDown cbxMonth = new DropDown(FindBy.xpath, "//select[@class='ui-datepicker-month']");
	protected Element celDay = new Element(FindBy.xpath,
			"//table[@class='ui-datepicker-calendar']/tbody//td[./a[text()=%s]]");
	// Methods

	private void selectYear(int year) {
		int currentYear = Integer.parseInt(lblCurrentYear.getAttribute("innerText"));
		String vector = "";
		if (currentYear < year) {
			vector = "Next";
		} else if (currentYear > year) {
			vector = "Prev";
		}
		while (currentYear != year) {
			lblCurrentYear.Dynamic(vector).click();
			currentYear = Integer.parseInt(lblCurrentYear.getAttribute("innerText"));
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
			rbxRbOneWay.check();
		} else {
			rbxRoundTrip.check();
		}
	}

	public void selectOrigin(LocationOption location) {
		spanOrigin.click();
		txtSearch.enter(location.getKey());
		liLocationItem.Dynamic(location.getKey()).click();
	}

	public void selectDestination(LocationOption location) {
		spanDestination.click();
		txtSearch.enter(location.getKey());
		liLocationItem.Dynamic(location.getKey()).click();
	}

	private void selectCal(Date date) {
		String _date = DateTimeHelper.getDate(date, "d/M/yyyy");
		String[] dateComponent = _date.split("/");
		selectYear(Integer.parseInt(dateComponent[2]));
		selectMonth(Integer.parseInt(dateComponent[1]));
		selectDay(Integer.parseInt(dateComponent[0]));
	}

	public void selectDepartDate(Date date) {
		if (!calDepartDate.getAttribute("value").trim().equals(DateTimeHelper.getDate(date, "dd/MM/yyyy"))) {
			calDepartDate.click();
			selectCal(date);
		}
	}

	public void selectReturnDate(Date date) {
		if (!calReturnDate.getAttribute("value").trim().equals(DateTimeHelper.getDate(date, "dd/MM/yyyy"))) {
			calReturnDate.click();
			selectCal(date);
		}
	}

	public void selectCurrency(String currency) {
		if (cbxCurrency.getAttribute("value") != currency) {
			// Default value always VND and Disables -> not handle yet
			// cbxCurrency.selectByText(currency);
		}
	}

	private void selectNumberOfPassenger(String passenger, int number) {
		int currentNumber = Integer.parseInt(txtNumberOfPassengers.Dynamic(passenger).getAttribute("value"));
		if (currentNumber != number) {
			btnNumberOfPassengers.Dynamic(passenger).click();
			listNumberOfPassengersOption.Dynamic(passenger, number).click();
		}
	}

	public void selectNumberOfAdults(int number) {
		selectNumberOfPassenger("Adults", number);
	}

	public void selectNumberOfChildrens(int number) {
		selectNumberOfPassenger("Children", number);
	}

	public void selectNumberOfInfans(int number) {
		selectNumberOfPassenger("Infants", number);
	}

	public void enterSearchData(BookingInfo flight) {
		selectFlightOption(flight.getFlightOption());
		selectOrigin(flight.getOrigin());
		selectDepartDate(flight.getDepartDate());
		selectDestination(flight.getDestination());
		selectReturnDate(flight.getReturnDate());
		selectCurrency(flight.getCurrency());
		selectNumberOfAdults(flight.getNumberOfAdults());
		chxInfare.setState(flight.isLowestFare());
		txtPromoCode.enter(flight.getPromoCode());
		selectNumberOfChildrens(flight.getNumberOfChildens());
		selectNumberOfInfans(flight.getNumberOfInfants());
	}

	public SelectTravelOptionsPage searchFlight(BookingInfo flight) {
		enterSearchData(flight);
		btnSearch.click();
		return new SelectTravelOptionsPage();
	}

	public SelectFarePage searchLowestFareFlight(BookingInfo flight) {
		enterSearchData(flight);
		btnSearch.click();
		return new SelectFarePage();
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

	// Assertion

	SoftAssertion softAssert = new SoftAssertion();

	public boolean isLanguage(LanguageType language) {
		return cbxLanguage.getSelectedOption().equals(language.getText());

	}
}
