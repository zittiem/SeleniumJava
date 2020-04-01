package pages.VietJet;

import datatype.VietJet.Booking;
import datatype.VietJet.Enums.FlightType;
import element.base.web.Element;
import element.wrapper.web.CheckBox;
import element.wrapper.web.DropDown;
import element.wrapper.web.RadioButton;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class HomePage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName, getClass().getSimpleName());
	// Elements

	protected DropDown cbxLanguage = new DropDown(locator.getLocator("cbxLanguage"));
	protected RadioButton rbxRoundTrip = new RadioButton(locator.getLocator("rbxRoundTrip"));
	protected RadioButton rbxRbOneWay = new RadioButton(locator.getLocator("rbxRbOneWay"));
	protected Element eleOriginSpan = new Element(locator.getLocator("eleOriginSpan"));
	protected Element eleDestinationSpan = new Element(locator.getLocator("eleDestinationSpan"));
	protected Element eleDateCal = new Element(locator.getLocator("eleDateCal"));
	protected Element eleCurrencyCbx = new Element(locator.getLocator("eleCurrencyCbx"));
	protected CheckBox chxInfare = new CheckBox(locator.getLocator("chxInfare"));
	protected TextBox txtPromoCode = new TextBox(locator.getLocator("txtPromoCode"));
	protected Element eleSearchBtn = new Element(locator.getLocator("eleSearchBtn"));
	protected TextBox txtNumberOfPassengers = new TextBox(locator.getLocator("txtNumberOfPassengers"));
	protected Element eleNumberOfPassengersBtn = new Element(locator.getLocator("eleNumberOfPassengersBtn"));
	protected Element eleNumberOfPassengersOptionLst = new Element(locator.getLocator("eleNumberOfPassengersOptionLst"));
	protected TextBox txtSearch = new TextBox(locator.getLocator("txtSearch"));
	protected Element eleLocationItemLi = new Element(locator.getLocator("eleLocationItemLi"));
	protected Element eleDatePickerNavigationLnk = new Element(locator.getLocator("eleDatePickerNavigationLnk"));
	protected Element eleCurrentYearLbl = new Element(locator.getLocator("eleCurrentYearLbl"));
	protected DropDown cbxMonth = new DropDown(locator.getLocator("cbxMonth"));
	protected Element eleDayCel = new Element(locator.getLocator("eleDayCel"));

	// Methods
	public void waitForPageLoad() {
		eleSearchBtn.waitForDisabled(Constants.LONG_TIME);
	}

	private void selectYear(int year) {
		int currentYear = Integer.parseInt(eleCurrentYearLbl.getAttribute("innerText"));
		String vector = "";
		if (currentYear < year) {
			vector = "Next";
		} else if (currentYear > year) {
			vector = "Prev";
		}
		while (Math.abs(currentYear - year) > 0) {
			eleCurrentYearLbl.generateDynamic(vector).click();
			eleCurrentYearLbl.waitForAttributeChanged("innerText", String.valueOf(currentYear), Constants.SHORT_TIME);
			currentYear = Integer.parseInt(eleCurrentYearLbl.getAttribute("innerText"));
		}

	}

	private void selectMonth(int month) {
		cbxMonth.selectByValue(Integer.toString(month - 1));
	}

	private void selectDay(int day) {
		eleDayCel.generateDynamic(day).click();
	}

	public void selectFlightOption(String option) {
		if (option.equals("One Way")) {
			rbxRbOneWay.select();
		} else if (option.equals("Return")) {
			rbxRoundTrip.select();
		}
	}

	public void selectOrigin(String location) {
		eleOriginSpan.click();
		txtSearch.enter(location);
		eleLocationItemLi.generateDynamic(location).click();
	}

	public void selectDestination(String location) {
		eleDestinationSpan.click();
		txtSearch.enter(location);
		eleLocationItemLi.generateDynamic(location).click();
	}

	private void selectCal(String date) {
		String[] dateComponent = date.split("/");
		selectYear(Integer.parseInt(dateComponent[2]));
		selectMonth(Integer.parseInt(dateComponent[1]));
		selectDay(Integer.parseInt(dateComponent[0]));
	}

	public void selectDate(FlightType flightType, String date) {
		eleDateCal = eleDateCal.generateDynamic(flightType.getValue());
		if (!eleDateCal.getAttribute("value").trim().equals(date)) {
			eleDateCal.click();
			selectCal(date);
		}
	}

	public void selectCurrency(String currency) {
		if (eleCurrencyCbx.getAttribute("value") != currency) {
			// Default value always VND and Disables -> not handle yet
			// cbxCurrency.selectByText(currency);
		}
	}

	private void selectNumberOfPassenger(String passenger, int number) {
		int currentNumber = Integer.parseInt(txtNumberOfPassengers.generateDynamic(passenger).getAttribute("value"));
		if (currentNumber != number) {
			eleNumberOfPassengersBtn.generateDynamic(passenger).click();
			eleNumberOfPassengersOptionLst.generateDynamic(passenger, number).click();
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

	public void enterSearchData(Booking bookingInfo) {
		selectFlightOption(bookingInfo.getFlightOption());
		selectOrigin(bookingInfo.getDepartureFrom());
		selectDate(FlightType.Dep, bookingInfo.getDepartureDate());
		selectDestination(bookingInfo.getReturnFrom());
		selectDate(FlightType.Ret, bookingInfo.getReturnDate());
		selectCurrency(bookingInfo.getCurrency());
		chxInfare.setState(bookingInfo.isLowestFare());
		txtPromoCode.enter(bookingInfo.getPromoCode());
		selectNumberOfAdults(bookingInfo.getNumberOfAdults());
		selectNumberOfChildrens(bookingInfo.getNumberOfChildren());
		selectNumberOfInfans(bookingInfo.getNumberOfInfants());
	}

	public void searchFlight(Booking bookingInfo) {
		enterSearchData(bookingInfo);
		eleSearchBtn.click();
	}

	public void searchLowestFareFlight(Booking flight) {
		enterSearchData(flight);
		eleSearchBtn.click();
	}

	// Assertion
	public boolean isLanguage(String language) {
		return cbxLanguage.getSelectedOption().equals(language);
	}
	
	public String getSelectedLanguage() {
		return cbxLanguage.getSelectedOption();
	}
	
	public SelectFarePage searchCheapestFlights(Booking booking) {
		enterSearchData(booking);
		eleSearchBtn.click();
		return new SelectFarePage();
	}
}
