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
	
	// Static Elements
	protected DropDown cbxLanguage = new DropDown(locator.getLocator("cbxLanguage"));
	protected RadioButton rbxRoundTrip = new RadioButton(locator.getLocator("rbxRoundTrip"));
	protected RadioButton rbxRbOneWay = new RadioButton(locator.getLocator("rbxRbOneWay"));
	protected Element eleOriginSpan = new Element(locator.getLocator("eleOriginSpan"));
	protected Element eleDestinationSpan = new Element(locator.getLocator("eleDestinationSpan"));
	protected Element eleCurrencyCbx = new Element(locator.getLocator("eleCurrencyCbx"));
	protected CheckBox chxInfare = new CheckBox(locator.getLocator("chxInfare"));
	protected TextBox txtPromoCode = new TextBox(locator.getLocator("txtPromoCode"));
	protected Element eleSearchBtn = new Element(locator.getLocator("eleSearchBtn"));
	protected TextBox txtSearch = new TextBox(locator.getLocator("txtSearch"));
	protected Element eleCurrentYearLbl = new Element(locator.getLocator("eleCurrentYearLbl"));
	protected DropDown cbxMonth = new DropDown(locator.getLocator("cbxMonth"));
	
	// Dynamic Elements
	protected Element eleDateCal = new Element(locator.getLocator("eleDateCal"));
	protected TextBox txtNumberOfPassengers = new TextBox(locator.getLocator("txtNumberOfPassengers"));
	protected Element eleNumberOfPassengersBtn = new Element(locator.getLocator("eleNumberOfPassengersBtn"));
	protected Element eleNumberOfPassengersOptionLst = new Element(locator.getLocator("eleNumberOfPassengersOptionLst"));
	protected Element eleLocationItemLi = new Element(locator.getLocator("eleLocationItemLi"));
	protected Element eleDatePickerNavigationLnk = new Element(locator.getLocator("eleDatePickerNavigationLnk"));
	protected Element eleDayCel = new Element(locator.getLocator("eleDayCel"));
	
	// Methods
	
    /**
     * Wait for page load completely. Time out is Constants.LONG_TIME.
     */
	public void waitForPageLoad() {
		eleSearchBtn.waitForDisabled(Constants.LONG_TIME);
	}

    /**
     * Find and select a year in the calendar
     *
     * @param  year
     *         The specific year that should be selected (int)
     *
     */
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

    /**
     * Find and select a month in the calendar
     *
     * @param  month
     *         The specific month that should be selected (int)
     *
     */
	private void selectMonth(int month) {
		cbxMonth.selectByValue(Integer.toString(month - 1));
	}

    /**
     * Find and select a day in the calendar
     *
     * @param  day
     *         The specific day that should be selected (int)
     *
     */
	private void selectDay(int day) {
		eleDayCel.generateDynamic(day).click();
	}

    /**
     * Select flight option. It includes "One Way" or "Return"
     *
     * @param  option
     *         Flight option: "One Way" or "Return"
     *
     */
	public void selectFlightOption(String option) {
		if (option.equals("One Way")) {
			rbxRbOneWay.select();
		} else if (option.equals("Return")) {
			rbxRoundTrip.select();
		}
	}

    /**
     * Select Origin of the flight
     *
     * @param  location
     *         Origin location
     *
     */
	public void selectOrigin(String location) {
		eleOriginSpan.click();
		txtSearch.enter(location);
		eleLocationItemLi.generateDynamic(location).click();
	}

    /**
     * Select destination of the flight
     *
     * @param  location
     *         Destination location
     *
     */
	public void selectDestination(String location) {
		eleDestinationSpan.click();
		txtSearch.enter(location);
		eleLocationItemLi.generateDynamic(location).click();
	}

	
    /**
     * Select a date in the calendar
     *
     * @param  date
     *         Date that should be selected
     *
     */
	private void selectCal(String date) {
		String[] dateComponent = date.split("/");
		selectYear(Integer.parseInt(dateComponent[2]));
		selectMonth(Integer.parseInt(dateComponent[1]));
		selectDay(Integer.parseInt(dateComponent[0]));
	}

    /**
     * Select flight date. It can be depart date or return date
     *
     * @param  flightType
     *         Depart or Return
     *
     * @param	date
     * 			Date
     * 
     */
	public void selectFlightDate(FlightType flightType, String date) {
		eleDateCal = eleDateCal.generateDynamic(flightType.getValue());
		if (!eleDateCal.getAttribute("value").trim().equals(date)) {
			eleDateCal.click();
			selectCal(date);
		}
	}

	
    /**
     * Select currency. Currently only VND is acceptable.
     *
     * @param  currency
     *         Currency
     *
     */
	public void selectCurrency(String currency) {
		if (eleCurrencyCbx.getAttribute("value") != currency) {
			// Default value always VND and Disables -> not handle yet
			// cbxCurrency.selectByText(currency);
		}
	}

    /**
     * Select number of passenger
     *
     * @param  passenger
     *         passenger type. It can be Adults|Children|Infants
     *
     * @param	number
     * 			number of passenger
     */
	private void selectNumberOfPassenger(String passenger, int number) {
		int currentNumber = Integer.parseInt(txtNumberOfPassengers.generateDynamic(passenger).getAttribute("value"));
		if (currentNumber != number) {
			eleNumberOfPassengersBtn.generateDynamic(passenger).click();
			eleNumberOfPassengersOptionLst.generateDynamic(passenger, number).click();
		}
	}

    /**
     * Select number of Adults
     *
     * @param	number
     * 			number of passenger
     */
	public void selectNumberOfAdults(int number) {
		selectNumberOfPassenger("Adults", number);
	}

    /**
     * Select number of Children
     *
     * @param	number
     * 			number of passenger
     */
	public void selectNumberOfChildrens(int number) {
		selectNumberOfPassenger("Children", number);
	}

    /**
     * Select number of Infants
     *
     * @param	number
     * 			number of passenger
     */
	public void selectNumberOfInfans(int number) {
		selectNumberOfPassenger("Infants", number);
	}

    /**
     * Input the booking info to search a suitable flight
     *
     * @param	bookingInfo
     * 			Information of a booking
     */
	public void enterSearchData(Booking bookingInfo) {
		selectFlightOption(bookingInfo.getFlightOption());
		selectOrigin(bookingInfo.getDepartureFrom());
		selectFlightDate(FlightType.Dep, bookingInfo.getDepartureDate());
		selectDestination(bookingInfo.getReturnFrom());
		selectFlightDate(FlightType.Ret, bookingInfo.getReturnDate());
		selectCurrency(bookingInfo.getCurrency());
		chxInfare.setState(bookingInfo.isLowestFare());
		txtPromoCode.enter(bookingInfo.getPromoCode());
		selectNumberOfAdults(bookingInfo.getNumberOfAdults());
		selectNumberOfChildrens(bookingInfo.getNumberOfChildren());
		selectNumberOfInfans(bookingInfo.getNumberOfInfants());
	}

    /**
     * Search a flight base on the booking info. This method will input the booking info and then click Search button
     *
     * @param	bookingInfo
     * 			Information of a booking
     */
	public void searchFlight(Booking bookingInfo) {
		enterSearchData(bookingInfo);
		eleSearchBtn.click();
	}
	
    /**
     * Get current displaying language
     *
     * @return	a string of displaying language
     * 			
     */
	public String getSelectedLanguage() {
		return cbxLanguage.getSelectedOption();
	}
	
    /**
     * Search a flight with lowest fare option is selected.
     *
     * @param	bookingInfo
     * 			Information of a booking
     */
	public SelectFarePage searchCheapestFlights(Booking booking) {
		enterSearchData(booking);
		eleSearchBtn.click();
		return new SelectFarePage();
	}

	// Verify
	
	 /**
     * Return a Boolean value to indicate whether the current displaying language match with expected
     *
     * @param	language
     *			expected language
     *
     * @return  true|false
     * 			true: The displaying language matches with the expected language 
     * 			false: The displaying language does not match with the expected language 
     * 
     */
	public boolean isLanguage(String expectedLanguage) {
		return cbxLanguage.getSelectedOption().equals(expectedLanguage);
	}

}
