package pages.VietJet;

import datatype.VietJet.BookingInfo;
import datatype.VietJet.LanguageType;
import element.base.web.Element;
import element.wrapper.web.CheckBox;
import element.wrapper.web.DropDown;
import element.wrapper.web.RadioButton;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import utils.constants.Constants;

public class HomePage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH, getClass().getSimpleName());
	// Elements

	protected DropDown cbxLanguage = new DropDown(locator.getLocator("cbxLanguage"));
	protected RadioButton rbxRoundTrip = new RadioButton(locator.getLocator("rbxRoundTrip"));
	protected RadioButton rbxRbOneWay = new RadioButton(locator.getLocator("rbxRbOneWay"));
	protected Element spanOrigin = new Element(locator.getLocator("spanOrigin"));
	protected Element spanDestination = new Element(locator.getLocator("spanDestination"));
	protected Element calDepartDate = new Element(locator.getLocator("calDepartDate"));
	protected Element calReturnDate = new Element(locator.getLocator("calReturnDate"));
	protected Element cbxCurrency = new Element(locator.getLocator("cbxCurrency"));
	protected CheckBox chxInfare = new CheckBox(locator.getLocator("chxInfare"));
	protected TextBox txtPromoCode = new TextBox(locator.getLocator("txtPromoCode"));
	protected Element btnSearch = new Element(locator.getLocator("btnSearch"));
	protected TextBox txtNumberOfPassengers = new TextBox(locator.getLocator("txtNumberOfPassengers"));
	protected Element btnNumberOfPassengers = new Element(locator.getLocator("btnNumberOfPassengers"));
	protected Element listNumberOfPassengersOption = new Element(locator.getLocator("listNumberOfPassengersOption"));
	protected TextBox txtSearch = new TextBox(locator.getLocator("txtSearch"));
	protected Element liLocationItem = new Element(locator.getLocator("liLocationItem"));
	protected Element linkDatePickerNavigation = new Element(locator.getLocator("linkDatePickerNavigation"));
	protected Element lblCurrentYear = new Element(locator.getLocator("lblCurrentYear"));
	protected DropDown cbxMonth = new DropDown(locator.getLocator("cbxMonth"));
	protected Element celDay = new Element(locator.getLocator("celDay"));

	// Methods
	public void waitForPageLoad() {
		btnSearch.waitForDisabled(Constants.LONG_TIME);
	}

	private void selectYear(int year) {
		int currentYear = Integer.parseInt(lblCurrentYear.getAttribute("innerText"));
		String vector = "";
		if (currentYear < year) {
			vector = "Next";
		} else if (currentYear > year) {
			vector = "Prev";
		}
		while (currentYear != year) {
			lblCurrentYear.generateDynamic(vector).click();
			currentYear = Integer.parseInt(lblCurrentYear.getAttribute("innerText"));
		}

	}

	private void selectMonth(int month) {
		cbxMonth.selectByValue(Integer.toString(month - 1));
	}

	private void selectDay(int day) {
		celDay.generateDynamic(day).click();
	}

	public void selectFlightOption(String option) {
		if (option.equals("One Way")) {
			rbxRbOneWay.select();
		} else if (option.equals("Return")) {
			rbxRoundTrip.select();
		}
	}

	public void selectOrigin(String location) {
		spanOrigin.click();
		txtSearch.enter(location);
		liLocationItem.generateDynamic(location).click();
	}

	public void selectDestination(String location) {
		spanDestination.click();
		txtSearch.enter(location);
		liLocationItem.generateDynamic(location).click();
	}

	private void selectCal(String date) {
		String[] dateComponent = date.split("/");
		selectYear(Integer.parseInt(dateComponent[2]));
		selectMonth(Integer.parseInt(dateComponent[1]));
		selectDay(Integer.parseInt(dateComponent[0]));
	}

	public void selectDepartDate(String date) {
		if (!calDepartDate.getAttribute("value").trim().equals(date)) {
			calDepartDate.click();
			selectCal(date);
		}
	}

	public void selectReturnDate(String date) {
		if (!calReturnDate.getAttribute("value").trim().equals(date)) {
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
		int currentNumber = Integer.parseInt(txtNumberOfPassengers.generateDynamic(passenger).getAttribute("value"));
		if (currentNumber != number) {
			btnNumberOfPassengers.generateDynamic(passenger).click();
			listNumberOfPassengersOption.generateDynamic(passenger, number).click();
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
		selectOrigin(flight.getOriginKey());
		selectDepartDate(flight.getDepartDate());
		selectDestination(flight.getDestinationKey());
		selectReturnDate(flight.getReturnDate());
		selectCurrency(flight.getCurrency());
		selectNumberOfAdults(flight.getNumberOfAdults());
		chxInfare.setState(flight.isLowestFare());
		txtPromoCode.enter(flight.getPromoCode());
		selectNumberOfChildrens(flight.getNumberOfChildren());
		selectNumberOfInfans(flight.getNumberOfInfants());
	}

	public void searchFlight(BookingInfo flight) {
		enterSearchData(flight);
		btnSearch.click();
	}

	public void searchLowestFareFlight(BookingInfo flight) {
		enterSearchData(flight);
		btnSearch.click();
	}

	// Assertion
	public boolean isLanguage(LanguageType language) {
		return cbxLanguage.getSelectedOption().equals(language.getText());
	}
	
	public String getSelectedLanguage() {
		return cbxLanguage.getSelectedOption();
	}
	
	public SelectFarePage searchCheapestFlights(BookingInfo booking) {
		enterSearchData(booking);
		btnSearch.click();
		return new SelectFarePage();
	}
}
