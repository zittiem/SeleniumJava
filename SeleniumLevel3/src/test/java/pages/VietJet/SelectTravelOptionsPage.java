package pages.VietJet;

import java.util.Date;
import java.util.List;
import datatype.VietJet.Booking;
import datatype.VietJet.Enums.Trips;
import datatype.VietJet.Enums.TicketDetails;
import datatype.VietJet.Enums.FlightClass;
import datatype.VietJet.Enums.LocationOption;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.setting.ElementStatus;
import element.setting.FindBy;
import element.wrapper.web.RadioButton;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;
import utils.helper.DateTimeHelper;

public class SelectTravelOptionsPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			getClass().getSimpleName());

	// Element
	protected Element formTravelOption = new Element(locator.getLocator("formTravelOption"));
	protected Element lblDisplayCurrency = new Element(locator.getLocator("lblDisplayCurrency"));
	protected Element lblDepartureFrom = new Element(locator.getLocator("lblDepartureFrom"));
	protected Element lblDepartureTo = new Element(locator.getLocator("lblDepartureTo"));
	protected Element lblDepartureDate = new Element(locator.getLocator("lblDepartureDate"));
	protected Element lblDepartureTime = new Element(locator.getLocator("lblDepartureTime"));
	protected Element lblDepartureFare = new Element(locator.getLocator("lblDepartureFare"));
	protected Element lblDepartureCharges = new Element(locator.getLocator("lblDepartureCharges"));
	protected Element lblDepartureTax = new Element(locator.getLocator("lblDepartureTax"));
	protected Element lblDepartureTotalFare = new Element(locator.getLocator("lblDepartureTotalFare"));
	protected Element lblReturnFrom = new Element(locator.getLocator("lblReturnFrom"));
	protected Element lblReturnTo = new Element(locator.getLocator("lblReturnTo"));
	protected Element lblReturnDate = new Element(locator.getLocator("lblReturnDate"));
	protected Element lblReturnTime = new Element(locator.getLocator("lblReturnTime"));
	protected Element lblReturnFare = new Element(locator.getLocator("lblReturnFare"));
	protected Element lblReturnCharges = new Element(locator.getLocator("lblReturnCharges"));
	protected Element lblReturnTax = new Element(locator.getLocator("lblReturnTax"));
	protected Element lblReturnTotalFare = new Element(locator.getLocator("lblReturnTotalFare"));
	protected Element lblGrandTotal = new Element(locator.getLocator("lblGrandTotal"));
	protected Element lblNumberOfAdults = new Element(locator.getLocator("lblNumberOfAdults"));
	protected Element lblNumberOfChildren = new Element(locator.getLocator("lblNumberOfChildren"));
	protected Element lblNumberOfInfants = new Element(locator.getLocator("lblNumberOfInfants"));
	protected Element optDerparturPrice = new Element(locator.getLocator("optDerparturPrice"));
	protected Element optReturnPrice = new Element(locator.getLocator("optReturnPrice"));
	protected Element btnContinue = new Element(locator.getLocator("btnContinue"));
	protected RadioButton rdxDeparture = null;
	protected RadioButton rdxReturn = null;
	protected Element lblPageTitle = new Element(locator.getLocator("lblPageTitle"));

	// Method

    /**
     * Wait for page load completely. Time out is Constants.LONG_TIME.
     */
	public void waitForPageLoad() {
		btnContinue.waitForCondition(ElementStatus.DISPLAYED, Constants.LONG_TIME, false);
	}

    /**
     * find cheapest ticket regardless the flight class
     *
     * @param  trip
     *         Trips.DEPARTURE | Trips.RETURN
     *
     */
	private void findCheapestTicket(Trips trip) {
		findCheapestTicket(trip, FlightClass.NONE);
	}
	
    /**
     * find cheapest ticket regardless the flight class
     *
     */
	private void findCheapestTickets() {
		findCheapestTickets(FlightClass.NONE);
	}

    /**
     * find cheapest ticket bases on the flight class
     *
     * @param  trip
     *         DEPARTURE | RETURN
     *
     * @param	flightClass
     * 			ECO|PROMO|SKYBOSS|NONE("", "")		
     */
	private void findCheapestTicket(Trips trip, FlightClass flightClass) {
		Element element = null;
		if (trip.getValue().equals("Departure"))
			element = optDerparturPrice;
		else if (trip.getValue().equals("Return"))
			element = optReturnPrice;
		List<Element> elements = element.generateDynamic(flightClass.getValue()).getWrapperElements();
		double price = convertMoneyFromStringToDouble(elements.get(0).getText());
		Element outputElement = elements.get(0);
		for (int i = 1; i < elements.size(); i++) {
			if (convertMoneyFromStringToDouble(elements.get(i).getText()) < price) {
				price = convertMoneyFromStringToDouble(elements.get(i).getText());
				outputElement = elements.get(i);
			}
		}
		if (trip.getValue().equals("Departure")) {
			this.optDerparturPrice = outputElement;
			this.rdxDeparture = new RadioButton(FindBy.xpath,
					optDerparturPrice.getLocator().toString().substring(10) + "//input[@id='gridTravelOptDep']");

		} else if (trip.getValue().equals("Return")) {
			this.optReturnPrice = outputElement;
			this.rdxReturn = new RadioButton(FindBy.xpath,
					optReturnPrice.getLocator().toString().substring(10) + "//input[@id='gridTravelOptRet']");
			}
	}

    /**
     * find cheapest ticket after the specific date time
     *
     * @param  trip
     *         DEPARTURE | RETURN
     *
     * @param	flightClass
     * 			ECO|PROMO|SKYBOSS|NONE("", "")		
     * 
     * @param	date
     * 			date
     * 
     * @param	time
     * 			time
     * 
     */
	private void findCheapestTicketAfterDateTime(Trips trip, FlightClass flightClass, String date, String time) {
		String eleRowXpath = String.format("//tr[contains(@id,'gridTravelOpt%s')]", trip.getKey());
		String finalXpath = null;
		double finalPrice = 0;
		boolean flagSaveData = true;
		List<Element> eleRows = new Element(FindBy.xpath, eleRowXpath).getWrapperElements();
		if (!(date + time).isEmpty()) {
			Date expDateTime = DateTimeHelper.getDate(date + " " + time, ResourceHelper.SHARED_DATA.get().date_format.concat(" HH:mm"));
			for (int i = 1; i <= eleRows.size(); i++) {
				String actualDate = DateTimeHelper.getDateString(
						DateTimeHelper.getDate(new Element(FindBy.xpath,
								eleRowXpath + "[" + i + "]" + "//table[not(@class)]//td" + "[1]").getText()),
						ResourceHelper.SHARED_DATA.get().date_format);
				String actualDepartTime = new Element(FindBy.xpath,
						eleRowXpath + "[" + i + "]" + "//table[not(@class)]//td" + "[2]").getText().substring(0, 5);
				Date actualDateTime = DateTimeHelper.getDate(actualDate + " " + actualDepartTime,
						ResourceHelper.SHARED_DATA.get().date_format.concat(" HH:mm"));
				if (actualDateTime.after(expDateTime)) {
					String actualPriceTableXpath = String.format(eleRowXpath + "[" + i + "]"
							+ "//table[@class='FaresGrid']//td[contains(@id,'gridTravelOpt') and contains(@data-familyid,'%s')]",
							flightClass.getValue());
					List<Element> eleCellPrices = new Element(FindBy.xpath, actualPriceTableXpath).getWrapperElements();

					for (int j = 1; j <= eleCellPrices.size(); j++) {
						if (flagSaveData) {
							finalPrice = convertMoneyFromStringToDouble(
									new Element(FindBy.xpath, actualPriceTableXpath + "[" + j + "]").getText());
							finalXpath = actualPriceTableXpath + "[" + j + "]";
							flagSaveData = false;
						}
						if (convertMoneyFromStringToDouble(new Element(FindBy.xpath, actualPriceTableXpath + "[" + j + "]")
								.getText()) < finalPrice) {
							finalPrice = convertMoneyFromStringToDouble(
									new Element(FindBy.xpath, actualPriceTableXpath + "[" + j + "]").getText());
							finalXpath = actualPriceTableXpath + "[" + j + "]";
						}
					}
				}
			}
		}

		if (trip.getValue().equals("Departure")) {
			this.optDerparturPrice = new Element(FindBy.xpath, finalXpath);
			this.rdxDeparture = new RadioButton(FindBy.xpath,
					optDerparturPrice.getLocator().toString().substring(10) + "//input[@id='gridTravelOptDep']");

		} else if (trip.getValue().equals("Return")) {
			this.optReturnPrice = new Element(FindBy.xpath, finalXpath);
			this.rdxReturn = new RadioButton(FindBy.xpath,
					optReturnPrice.getLocator().toString().substring(10) + "//input[@id='gridTravelOptRet']");
		}
	}

    /**
     * find combo of cheapest tickets (departure and return) bases on the flight class
     *
     * @param	flightClass
     * 			ECO|PROMO|SKYBOSS|NONE("", "")	
     *     	
     */
	private void findCheapestTickets(FlightClass flightClass) {
		findCheapestTicket(Trips.DEPARTURE, flightClass);
		findCheapestTicketAfterDateTime(Trips.RETURN, flightClass,
				getDepartureDateInfo(ResourceHelper.SHARED_DATA.get().date_format),
				getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.ARR_TIME));
	}

    /**
     * select combo of cheapest tickets (departure and return) regardless flightclass
     */
	public void selectCheapestTickets() {
		lblNumberOfAdults.waitForDisplayed(90);
		findCheapestTickets();
		rdxDeparture.scrollIntoView();
		rdxDeparture.select();
		rdxReturn.scrollIntoView();
		rdxReturn.select();
	}

    /**
     * select combo of cheapest tickets (departure and return) base on flightclass
     * 
     * @param	flightClass
     * 			ECO|PROMO|SKYBOSS|NONE("", "")	
     * 
     */
	public void selectCheapestTickets(FlightClass flightClass) {
		findCheapestTickets(flightClass);
		rdxDeparture.scrollIntoView();
		rdxDeparture.select();
		rdxReturn.scrollIntoView();
		rdxReturn.select();
	}

    /**
     * select cheapest ticket regardless the flight class
     *
     * @param  trip
     *         Trips.DEPARTURE | Trips.RETURN
     *
     */
	public void selectCheapestTicket(Trips trip) {
		findCheapestTicket(trip);
		rdxDeparture.scrollIntoView();
		rdxDeparture.select();
		rdxReturn.scrollIntoView();
		rdxReturn.select();
	}

    /**
     * find combo of cheapest tickets (departure and return) bases on the flight class
     *
     * @param	flightClass
     * 			ECO|PROMO|SKYBOSS|NONE("", "")	
     *     	
     */
	public void selectCheapestTicket(FlightClass flightClass) {
		findCheapestTickets(flightClass);
		rdxDeparture.scrollIntoView();
		rdxDeparture.select();
		rdxReturn.scrollIntoView();
		rdxReturn.select();
	}

    /**
     * Convert money from string to double
     *
     * @param  value
     *         money in string format. Ex: 10,000 VND
     *         
     * @return	money with double type
     *
     */
	private double convertMoneyFromStringToDouble(String value) {
		return Double.parseDouble(value.split(" ")[0].replace(",", "")) * 1;
	}

    /**
     * Get departure from information
     *         
     * @return	a string of departure from
     *
     */
	public String getDepartureFromInfo() {
		return lblDepartureFrom.getText().split(":")[1].trim();
	}

    /**
     * Get departure to information
     *         
     * @return	a string of departure to
     *
     */
	public String getDepartureToInfo() {
		return lblDepartureTo.getText().split(":")[1].trim();
	}

    /**
     * Get departure date
     *         
     * @return	a string of departure date
     *
     */
	public String getDepartureDateInfo() {
		String xpath = "//ancestor::tr[contains(@id,'gridTravelOptDep')]//td[@class='SegInfo' and contains(text(),'/')]";
		return new Element(optDerparturPrice, FindBy.xpath, xpath).getText();
	}

    /**
     * Get departure date with input format
     *         
     * @return	a string of departure date with input format
     *
     */
	public String getDepartureDateInfo(String format) {
		return DateTimeHelper.getDateString(DateTimeHelper.getDate(getDepartureDateInfo()), format);
	}

    /**
     * Get departure date in summary section
     *         
     * @return	a string of departure date
     *
     */
	public String getDepartureDateInfoInSummary() {
		return lblDepartureDate.getText();
	}

    /**
     * Get return from location
     *         
     * @return	a string of return from location
     *
     */
	public String getReturnFromInfo() {
		return lblReturnFrom.getText().split(":")[1].trim();
	}

    /**
     * Get return to location
     *         
     * @return	a string of return to location
     *
     */
	public String getReturnToInfo() {
		return lblReturnTo.getText().split(":")[1].trim();
	}

    /**
     * Get return date
     *         
     * @return	a string of return date
     *
     */
	public String getReturnDateInfo() {
		String xpath = "//ancestor::tr[contains(@id,'gridTravelOptRet')]//td[@class='SegInfo' and contains(text(),'/')]";
		return new Element(optReturnPrice, FindBy.xpath, xpath).getText();
	}

    /**
     * Get return date with input format
     *         
     * @return	a string of return date with input format
     *
     */
	public String getReturnDateInfo(String format) {
		return DateTimeHelper.getDateString(DateTimeHelper.getDate(getReturnDateInfo()), format);
	}

    /**
     * Get return date in summary section
     *         
     * @return	a string of return date
     *
     */
	public String getReturnDateInfoInSummary() {
		return lblReturnDate.getText();
	}

    /**
     * Get number of adults
     *         
     * @return	number of adults (int)
     *
     */
	public int getNumberOfAdultsInfo() {
		return Integer.parseInt(lblNumberOfAdults.getText().split(":")[1].trim());
	}

    /**
     * Get number of children
     *         
     * @return	number of children (int)
     *
     */
	public int getNumberOfChildrenInfo() {
		return Integer.parseInt(lblNumberOfChildren.getText().split(":")[1].trim());
	}

    /**
     * Get number of infants
     *         
     * @return	number of infants (int)
     *
     */
	public int getNumberOfInfantsInfo() {
		return Integer.parseInt(lblNumberOfInfants.getText().split(":")[1].trim());
	}

    /**
     * Submit a page by clicking on the Continue button
     *         
     * @return	number of infants (int)
     *
     */
	public void submitPage() {
		btnContinue.moveToElement();
		btnContinue.click();
	}
	
    /**
     * Get current booking information
     *         
     * @return	booking
     *
     */
	public Booking getCurrentBookingInfo() {
		Booking booking = new Booking();
		booking.setDepartureFrom(LocationOption.getCode(lblDepartureFrom.getText().split(":")[1].trim()));
		booking.setDepartureTo(LocationOption.getCode(lblDepartureTo.getText().split(":")[1].trim()));
		booking.setDepartureDate(DateTimeHelper.getDateString(DateTimeHelper.getDate(lblDepartureDate.getText()),
				ResourceHelper.SHARED_DATA.get().date_format));
		booking.setReturnFrom(LocationOption.getCode(lblReturnFrom.getText().split(":")[1].trim()));
		booking.setReturnTo(LocationOption.getCode(lblReturnTo.getText().split(":")[1].trim()));
		booking.setReturnDate(DateTimeHelper.getDateString(DateTimeHelper.getDate(lblReturnDate.getText()),
				ResourceHelper.SHARED_DATA.get().date_format));
		booking.setNumberOfAdults(Integer.parseInt(lblNumberOfAdults.getText().split(":")[1].trim()));
		booking.setNumberOfChildren(Integer.parseInt(lblNumberOfChildren.getText().split(":")[1].trim()));
		booking.setNumberOfInfants(Integer.parseInt(lblNumberOfInfants.getText().split(":")[1].trim()));
		return booking;
	}

    /**
     * Get booking details
     *         
     * @return	booking
     *
     */
	public Booking getBookingDetails() {
		Booking booking = new Booking();
		booking.setDepartureFrom(getDepartureFromInfo());
		booking.setDepartureTo(getDepartureToInfo());
		booking.setDepartureDate(DateTimeHelper.getDateString(DateTimeHelper.getDate(getDepartureDateInfo()),
				ResourceHelper.SHARED_DATA.get().date_format));
		booking.setReturnFrom(getReturnFromInfo());
		booking.setReturnTo(getReturnToInfo());
		booking.setReturnDate(DateTimeHelper.getDateString(DateTimeHelper.getDate(getReturnDateInfo()),
				ResourceHelper.SHARED_DATA.get().date_format));
		booking.setNumberOfAdults(getNumberOfAdultsInfo());
		booking.setNumberOfChildren(getNumberOfChildrenInfo());
		booking.setNumberOfInfants(getNumberOfInfantsInfo());
		booking.setDepartureFare(convertMoneyFromStringToDouble(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.FARE))
				* (booking.getNumberOfAdults() + booking.getNumberOfChildren()
						+ booking.getNumberOfInfants()));
		booking.setDepartureTax(convertMoneyFromStringToDouble(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.TAX)));
		booking.setDepartureCharge(convertMoneyFromStringToDouble(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.CHARGES)));
		booking.setDepartureTotal(convertMoneyFromStringToDouble(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.TOTAL)));
		booking.setDepartureTime(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.DEP_TIME));
		booking.setReturnFare(convertMoneyFromStringToDouble(getSelectedTicketInfo(Trips.RETURN, TicketDetails.FARE))
				* (booking.getNumberOfAdults() + booking.getNumberOfChildren()
						+ booking.getNumberOfInfants()));
		booking.setReturnTax(convertMoneyFromStringToDouble(getSelectedTicketInfo(Trips.RETURN, TicketDetails.TAX)));
		booking.setReturnCharge(convertMoneyFromStringToDouble(getSelectedTicketInfo(Trips.RETURN, TicketDetails.CHARGES)));
		booking.setReturnTotal(convertMoneyFromStringToDouble(getSelectedTicketInfo(Trips.RETURN, TicketDetails.TOTAL)));
		booking.setReturnTime(getSelectedTicketInfo(Trips.RETURN, TicketDetails.DEP_TIME));
		booking.setGrandTotal(booking.getDepartureTotal() + booking.getReturnTotal());
		return booking;
	}

    /**
     * Get selected ticket info
     * 
     * @param	trip
     *         	DEPARTURE|RETURN
     *         
     * @param	info
     * 			FARE|TAX|DEP_TIME|ARR_TIME|CHARGES|TOTAL
     * 
     * @return	a string of ticket info
     *
     */
	public String getSelectedTicketInfo(Trips trip, TicketDetails info) {
		Element element = null;
		String infoXpath = null;
		String returnValue = null;
		if (trip.getValue().equals("Departure")) {
			element = optDerparturPrice;
			infoXpath = "//ancestor::tr[contains(@id,'gridTravelOptDep')]//table[not(@class)]//td[3]";
		} else if (trip.getValue().equals("Return")) {
			element = optReturnPrice;
			infoXpath = "//ancestor::tr[contains(@id,'gridTravelOptRet')]//table[not(@class)]//td[3]";
		}
		if (info.getValue().equals("arriveTime")) {
			returnValue = new Element(element, FindBy.xpath, infoXpath).getText().trim().substring(0, 5);
		} else {
			String _locator = element.getLocator().toString().substring(10)
					+ String.format("//input[@id='%s']", info.getValue());
			returnValue = new Element(FindBy.xpath, _locator).getAttribute("value");
		}
		return returnValue;
	}

    /**
     * Get page title
     * 
     * @return	a string of page title
     *
     */
	public String getPageTitle() {
		lblPageTitle.waitForDisplayed(30);
		return DriverUtils.getTitle();
	}

	// Verify
	
	 /**
     * Return a Boolean value to indicate whether this page is displayed
     *
     * @return  true|false
     * 			true: This page is displayed
     * 			false: This page is not displayed
     * 
     */
	public boolean isDisplayed() {
		waitForPageLoad();
		return DriverUtils.getURL().contains("TravelOptions.aspx");
	}

	 /**
     * Return a Boolean value to indicate whether the displaying currency matches with the expected
     *
     * @return  true|false
     * 			true: the displaying currency matches with the expected
     * 			false: the displaying currency does not match with the expected
     * 
     */
	public boolean isCurrency(String expecteCurrency) {
		return lblDisplayCurrency.generateDynamic(expecteCurrency).getAttribute("innerText").equals(expecteCurrency);
	}
	
	 /**
     * Return a Boolean value to indicate whether the booking information matches with the expected
     *
     * @return  true|false
     * 			true: the displaying booking information matches with the expected
     * 			false: the displaying booking information does not match with the expected
     * 
     */
	public boolean isBookingInfoCorrect(Booking expectedBooking) {
		System.out.print("getDepartureFromInfo: " + getDepartureFromInfo() + " vs "
				+ LocationOption.getValue(expectedBooking.getDepartureFrom()) + "\n");
		System.out.print("getDepartureToInfo: " + getDepartureToInfo() + " vs "
				+ LocationOption.getValue(expectedBooking.getDepartureTo()) + " \n");
		System.out.print("getDepartureDateInfo: " + getDepartureDateInfoInSummary() + " vs "
				+ expectedBooking.getDepartureDate() + " \n");
		System.out.print("getReturnFromInfo: " + getReturnFromInfo() + " vs "
				+ LocationOption.getValue(expectedBooking.getReturnFrom()) + " \n");
		System.out.print("getReturnToInfo: " + getReturnToInfo() + " vs "
				+ LocationOption.getValue(expectedBooking.getReturnTo()) + " \n");
		System.out
				.print("getReturnDateInfo: " + getReturnDateInfoInSummary() + " vs " + expectedBooking.getReturnDate() + " \n");
		System.out.print(
				"getNumberOfAdultsInfo: " + getNumberOfAdultsInfo() + " vs " + expectedBooking.getNumberOfAdults() + " \n");
		System.out.print("getNumberOfChildrenInfo: " + getNumberOfChildrenInfo() + " vs "
				+ expectedBooking.getNumberOfChildren() + " \n");
		System.out.print(
				"getNumberOfInfantsInfo: " + getNumberOfInfantsInfo() + " vs " + expectedBooking.getNumberOfInfants() + " \n");

		return getDepartureFromInfo().equals(LocationOption.getValue(expectedBooking.getDepartureFrom()))
				&& getDepartureToInfo().equals(LocationOption.getValue(expectedBooking.getDepartureTo()))
				&& getDepartureDateInfoInSummary().contains(expectedBooking.getDepartureDate())
				&& getReturnFromInfo().equals(LocationOption.getValue(expectedBooking.getReturnFrom()))
				&& getReturnToInfo().equals(LocationOption.getValue(expectedBooking.getReturnTo()))
				&& getReturnDateInfoInSummary().contains(expectedBooking.getReturnDate())
				&& getNumberOfAdultsInfo() == expectedBooking.getNumberOfAdults()
				&& getNumberOfChildrenInfo() == expectedBooking.getNumberOfChildren()
				&& getNumberOfInfantsInfo() == expectedBooking.getNumberOfInfants();

	}
}