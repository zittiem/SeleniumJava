package pages.VietJet;

import java.util.Date;
import java.util.List;

import datatype.VietJet.BookingInfo;
import datatype.VietJet.BookingInfo.LocationOption;
import datatype.VietJet.BookingInfo.FlightClass;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.setting.FindBy;
import element.wrapper.web.RadioButton;
import helper.LocatorHelper;
import utils.constants.Constants;
import utils.helper.DateTimeHelper;

public class SelectTravelOptionsPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH, getClass().getSimpleName());
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

	public void waitForPageLoad() {
		this.btnContinue.waitForDisplayed(Constants.LONG_TIME);
	}

	public boolean isDisplayed() {
		waitForPageLoad();
		return DriverUtils.getURL().contains("TravelOptions.aspx");
	}

	// Find the cheapest flight option

	private void findCheapestTicket(Trips trip) {
		findCheapestTicket(trip, FlightClass.NONE);
	}

	private void findCheapestTicket(Trips trip, FlightClass flightClass) {
		Element element = null;
		if (trip.getValue().equals("Departure"))
			element = optDerparturPrice;
		else if (trip.getValue().equals("Return"))
			element = optReturnPrice;
		List<Element> elements = element.generateDynamic(flightClass.getValue()).getWrapperElements();
		double price = getCastValue(elements.get(0).getText());
		Element outputElement = elements.get(0);
		for (int i = 1; i < elements.size(); i++) {
			if (getCastValue(elements.get(i).getText()) < price) {
				price = getCastValue(elements.get(i).getText());
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

	private void findCheapestTicketAfterDateTime(Trips trip, FlightClass clazz, String date, String time) {
		String eleRowXpath = String.format("//tr[contains(@id,'gridTravelOpt%s')]", trip.getKey());
		String finalXpath = null;
		double finalPrice = 0;
		boolean flagSaveData = true;
		List<Element> eleRows = new Element(FindBy.xpath, eleRowXpath).getWrapperElements();
		if (!(date + time).isEmpty()) {
			Date expDateTime = DateTimeHelper.getDate(date + " " + time, "dd/MM/yyyy HH:mm");
			for (int i = 1; i <= eleRows.size(); i++) {
				String actualDate = DateTimeHelper.getDateString(
						DateTimeHelper.getDate(new Element(FindBy.xpath,
								eleRowXpath + "[" + i + "]" + "//table[not(@class)]//td" + "[1]").getText()),
						"dd/MM/yyyy");
				String actualDepartTime = new Element(FindBy.xpath,
						eleRowXpath + "[" + i + "]" + "//table[not(@class)]//td" + "[2]").getText().substring(0, 5);
				Date actualDateTime = DateTimeHelper.getDate(actualDate + " " + actualDepartTime, "dd/MM/yyyy HH:mm");
				if (actualDateTime.after(expDateTime)) {
					String actualPriceTableXpath = String.format(eleRowXpath + "[" + i + "]"
							+ "//table[@class='FaresGrid']//td[contains(@id,'gridTravelOpt') and contains(@data-familyid,'%s')]",
							clazz.getValue());
					List<Element> eleCellPrices = new Element(FindBy.xpath, actualPriceTableXpath).getWrapperElements();

					for (int j = 1; j <= eleCellPrices.size(); j++) {
						if (flagSaveData) {
							finalPrice = getCastValue(
									new Element(FindBy.xpath, actualPriceTableXpath + "[" + j + "]").getText());
							finalXpath = actualPriceTableXpath + "[" + j + "]";
							flagSaveData = false;
						}
						if (getCastValue(new Element(FindBy.xpath, actualPriceTableXpath + "[" + j + "]")
								.getText()) < finalPrice) {
							finalPrice = getCastValue(
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

	private void findCheapestTickets() {
		findCheapestTickets(FlightClass.NONE);
	}

	private void findCheapestTickets(FlightClass flightClass) {
		findCheapestTicket(Trips.DEPARTURE, flightClass);
		findCheapestTicketAfterDateTime(Trips.RETURN, flightClass, getDepartureDateInfo("dd/MM/yyyy"),
				getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.ARR_TIME));
	}

	public void selectCheapestTickets() {
		findCheapestTickets();
		rdxDeparture.scrollIntoView();
		rdxDeparture.select();
		rdxReturn.scrollIntoView();
		rdxReturn.select();
	}

	public void selectCheapestTickets(FlightClass flightClass) {
		findCheapestTickets(flightClass);
		rdxDeparture.scrollIntoView();
		rdxDeparture.select();
		rdxReturn.scrollIntoView();
		rdxReturn.select();
	}

	public void selectCheapestTicket(Trips trip) {
		findCheapestTicket(trip);
		rdxDeparture.scrollIntoView();
		rdxDeparture.select();
		rdxReturn.scrollIntoView();
		rdxReturn.select();
	}

	public void selectCheapestTicket(FlightClass flightClass) {
		findCheapestTickets(flightClass);
		rdxDeparture.scrollIntoView();
		rdxDeparture.select();
		rdxReturn.scrollIntoView();
		rdxReturn.select();
	}

	private double getCastValue(String value) {
		return Double.parseDouble(value.split(" ")[0].replace(",", "")) * 1;
	}

	public String getDepartureFromInfo() {
		return lblDepartureFrom.getText().split(":")[1].trim();
	}

	public String getDepartureToInfo() {
		return lblDepartureTo.getText().split(":")[1].trim();
	}

	public String getDepartureDateInfo() {
		String xpath = "//ancestor::tr[contains(@id,'gridTravelOptDep')]//td[@class='SegInfo' and contains(text(),'/')]";
		return new Element(optDerparturPrice, FindBy.xpath, xpath).getText();
	}

	public String getDepartureDateInfo(String format) {
		return DateTimeHelper.getDateString(DateTimeHelper.getDate(getDepartureDateInfo()), format);
	}
	
	public String getDepartureDateInfoInSummary() {
		return lblDepartureDate.getText();
	}

	public String getReturnFromInfo() {
		return lblReturnFrom.getText().split(":")[1].trim();
	}

	public String getReturnToInfo() {
		return lblReturnTo.getText().split(":")[1].trim();
	}

	public String getReturnDateInfo() {
		String xpath = "//ancestor::tr[contains(@id,'gridTravelOptRet')]//td[@class='SegInfo' and contains(text(),'/')]";
		return new Element(optReturnPrice, FindBy.xpath, xpath).getText();
	}

	public String getReturnDateInfo(String format) {
		return DateTimeHelper.getDateString(DateTimeHelper.getDate(getReturnDateInfo()), format);
	}
	
	public String getReturnDateInfoInSummary() {
		return lblReturnDate.getText();
	}

	public int getNumberOfAdultsInfo() {
		return Integer.parseInt(lblNumberOfAdults.getText().split(":")[1].trim());
	}

	public int getNumberOfChildrenInfo() {
		return Integer.parseInt(lblNumberOfChildren.getText().split(":")[1].trim());
	}

	public int getNumberOfInfantsInfo() {
		return Integer.parseInt(lblNumberOfInfants.getText().split(":")[1].trim());
	}

	public void submitPage() {
		btnContinue.moveToElement();
		btnContinue.click();
	}

	// Assert

	public boolean isCurrency(String currency) {
		return lblDisplayCurrency.generateDynamic(currency).getAttribute("innerText").equals(currency);
	}

	public BookingInfo getCurrentBookingInfo() {
		BookingInfo booking = new BookingInfo();
		booking.setDepartureFrom(LocationOption.getCode(lblDepartureFrom.getText().split(":")[1].trim()));
		booking.setDepartureTo(LocationOption.getCode(lblDepartureTo.getText().split(":")[1].trim()));
		booking.setDepartureDate(
				DateTimeHelper.getDateString(DateTimeHelper.getDate(lblDepartureDate.getText()), "dd/MM/yyyy"));
		booking.setReturnFrom(LocationOption.getCode(lblReturnFrom.getText().split(":")[1].trim()));
		booking.setReturnTo(LocationOption.getCode(lblReturnTo.getText().split(":")[1].trim()));
		booking.setReturnDate(
				DateTimeHelper.getDateString(DateTimeHelper.getDate(lblReturnDate.getText()), "dd/MM/yyyy"));
		booking.setNumberOfAdults(Integer.parseInt(lblNumberOfAdults.getText().split(":")[1].trim()));
		booking.setNumberOfChildren(Integer.parseInt(lblNumberOfChildren.getText().split(":")[1].trim()));
		booking.setNumberOfInfants(Integer.parseInt(lblNumberOfInfants.getText().split(":")[1].trim()));
		return booking;
	}

	// Section Store data for next page verifying
	public BookingInfo getTicketDetails() {
		BookingInfo ticketDetails = new BookingInfo();
		ticketDetails.setDepartureFrom(getDepartureFromInfo());
		ticketDetails.setDepartureTo(getDepartureToInfo());
		ticketDetails.setDepartureDate(
				DateTimeHelper.getDateString(DateTimeHelper.getDate(getDepartureDateInfo()), "dd/MM/yyyy"));
		ticketDetails.setReturnFrom(getReturnFromInfo());
		ticketDetails.setReturnTo(getReturnToInfo());
		ticketDetails
				.setReturnDate(DateTimeHelper.getDateString(DateTimeHelper.getDate(getReturnDateInfo()), "dd/MM/yyyy"));
		ticketDetails.setNumberOfAdults(getNumberOfAdultsInfo());
		ticketDetails.setNumberOfChildren(getNumberOfChildrenInfo());
		ticketDetails.setNumberOfInfants(getNumberOfInfantsInfo());
		ticketDetails.setDepartureFare(getCastValue(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.FARE))
				* (ticketDetails.getNumberOfAdults() + ticketDetails.getNumberOfChildren()
						+ ticketDetails.getNumberOfInfants()));
		ticketDetails.setDepartureTax(getCastValue(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.TAX)));
		ticketDetails.setDepartureCharge(getCastValue(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.CHARGES)));
		ticketDetails.setDepartureTotal(getCastValue(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.TOTAL)));
		ticketDetails.setDepartureTime(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.DEP_TIME));
		ticketDetails.setReturnFare(getCastValue(getSelectedTicketInfo(Trips.RETURN, TicketDetails.FARE))
				* (ticketDetails.getNumberOfAdults() + ticketDetails.getNumberOfChildren()
						+ ticketDetails.getNumberOfInfants()));
		ticketDetails.setReturnTax(getCastValue(getSelectedTicketInfo(Trips.RETURN, TicketDetails.TAX)));
		ticketDetails.setReturnCharge(getCastValue(getSelectedTicketInfo(Trips.RETURN, TicketDetails.CHARGES)));
		ticketDetails.setReturnTotal(getCastValue(getSelectedTicketInfo(Trips.RETURN, TicketDetails.TOTAL)));
		ticketDetails.setReturnTime(getSelectedTicketInfo(Trips.RETURN, TicketDetails.DEP_TIME));
		ticketDetails.setGrandTotal(ticketDetails.getDepartureTotal() + ticketDetails.getReturnTotal());
		return ticketDetails;
	}

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

	enum Trips {
		DEPARTURE("Dep", "Departure"), RETURN("Ret", "Return");

		private String key;
		private String value;

		Trips(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public String getKey() {
			return this.key;
		}
	}

	enum TicketDetails {
		FARE("fare"), TAX("fare_taxes"), DEP_TIME("depTime"), ARR_TIME("arriveTime"), CHARGES("charges"),
		TOTAL("total_complete_charges");

		private String details;

		TicketDetails(String details) {
			this.details = details;
		}

		public String getValue() {
			return this.details;
		}
	}

	public String getPageTitle() {
		lblPageTitle.waitForDisplayed(30);
		return DriverUtils.getTitle();
	}
	
	public boolean isBookingInfoCorrect(BookingInfo booking) {
		System.out.print("getDepartureFromInfo: " + getDepartureFromInfo() + " vs "+ booking.getOriginValue() + "\n");
		System.out.print("getDepartureToInfo: " + getDepartureToInfo() + " vs "+ booking.getDestinationValue() + " \n");
		System.out.print("getDepartureDateInfo: " + getDepartureDateInfoInSummary() + " vs "+ booking.getDepartDate() + " \n");
		System.out.print("getReturnFromInfo: " + getReturnFromInfo() + " vs "+ booking.getDestinationValue() + " \n");
		System.out.print("getReturnToInfo: " + getReturnToInfo() + " vs "+ booking.getOriginValue() + " \n");
		System.out.print("getReturnDateInfo: " + getReturnDateInfoInSummary() + " vs "+ booking.getReturnDate() + " \n");
		System.out.print("getNumberOfAdultsInfo: " + getNumberOfAdultsInfo() + " vs "+ booking.getNumberOfAdults() + " \n");
		System.out.print("getNumberOfChildrenInfo: " + getNumberOfChildrenInfo() + " vs "+ booking.getNumberOfChildren() + " \n");
		System.out.print("getNumberOfInfantsInfo: " + getNumberOfInfantsInfo() + " vs "+ booking.getNumberOfInfants() + " \n");
		
		return getDepartureFromInfo().equals(booking.getOriginValue())
				&& getDepartureToInfo().equals(booking.getDestinationValue())
				&& getDepartureDateInfoInSummary().contains(booking.getDepartDate())
				&& getReturnFromInfo().equals(booking.getDestinationValue())
				&& getReturnToInfo().equals(booking.getOriginValue())
				&& getReturnDateInfoInSummary().contains(booking.getReturnDate())
				&& getNumberOfAdultsInfo() == booking.getNumberOfAdults()
				&& getNumberOfChildrenInfo() == booking.getNumberOfChildren()
				&& getNumberOfInfantsInfo() == booking.getNumberOfInfants();
	}
}