package pages.VietJet;

import java.util.List;
import datatype.VietJet.BookingInfo;
import datatype.VietJet.TicketInfo;
import datatype.VietJet.BookingInfo.LocationOption;
import datatype.VietJet.TicketInfo.FlightClass;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.setting.FindBy;
import element.wrapper.web.RadioButton;
import helper.LocatorHelper;
import utils.common.Constants;
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
	protected Element lblPageTitle = new Element(FindBy.xpath, "//div[@style='float: left']/h1");

	// Method

	public void waitForPageLoad() {
		this.btnContinue.waitForDisplayed(Constants.LONG_TIME);
	}

	public boolean isDisplayed() {
		waitForPageLoad();
		return DriverUtils.getURL().contains("TravelOptions.aspx");
	}

	// Find the cheapest flight option
	public void findLowestFare(Trips ways, FlightClass flightClass) {
		Element element = null;
		Element outputElement = null;
		if (ways.getValue().equals("Departure")) {
			element = optDerparturPrice;

		} else if (ways.getValue().equals("Return")) {
			element = optReturnPrice;
		}

		List<Element> elements = element.generateDynamic(flightClass.getValue()).getWrapperElements();
		double price = getCastValue(elements.get(0).getText());
		outputElement = elements.get(0);
		for (int i = 1; i < elements.size(); i++) {
			if (getCastValue(elements.get(i).getText()) < price) {
				price = getCastValue(elements.get(i).getText());
				outputElement = elements.get(i);
			}
		}

		if (ways.getValue().equals("Departure")) {
			this.optDerparturPrice = outputElement;

		} else if (ways.getValue().equals("Return")) {
			this.optReturnPrice = outputElement;
		}
	}

	public void selectCheapestDeparture(FlightClass flightClass) {
		findLowestFare(Trips.DEPARTURE, flightClass);
		String _xpath = optDerparturPrice.getLocator().toString().substring(10)
				.concat("/input[@id='gridTravelOptDep']");
		RadioButton rdxCheapest = new RadioButton(FindBy.xpath, _xpath);
		rdxCheapest.scrollIntoView();
		rdxCheapest.select();
	}

	public void selectCheapestReturn(FlightClass flightClass) {
		findLowestFare(Trips.RETURN, flightClass);
		String _xpath = optReturnPrice.getLocator().toString().substring(10).concat("/input[@id='gridTravelOptRet']");
		RadioButton rdxCheapest = new RadioButton(FindBy.xpath, _xpath);
		rdxCheapest.scrollIntoView();
		rdxCheapest.select();
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
		String xpath = optDerparturPrice.getLocator().toString().substring(10)
				+ "//ancestor::tr//tr[@class='gridFlightEvenchecked']//td[@class='SegInfo' and contains(text(),'/')]";
		return new Element(FindBy.xpath, xpath).getText();
	}

	public String getReturnFromInfo() {
		return lblReturnFrom.getText().split(":")[1].trim();
	}

	public String getReturnToInfo() {
		return lblReturnTo.getText().split(":")[1].trim();
	}

	public String getReturnDateInfo() {
		String xpath = optReturnPrice.getLocator().toString().substring(10)
				+ "//ancestor::tr//tr[@class='gridFlightEvenchecked']//td[@class='SegInfo' and contains(text(),'/')]";
		return new Element(FindBy.xpath, xpath).getText();
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

	public void selectCheapestTicket(FlightClass flightClass) {
		selectCheapestDeparture(flightClass);
		selectCheapestReturn(flightClass);
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
		booking.setOriginKey(LocationOption.getCode(lblDepartureFrom.getText().split(":")[1].trim()));
		booking.setOriginValue(lblDepartureFrom.getText().split(":")[1].trim());
		booking.setDepartDate(
				DateTimeHelper.getDateString(DateTimeHelper.getDate(lblDepartureDate.getText()), "dd/MM/yyyy"));
		booking.setDestinationKey(LocationOption.getCode(lblReturnFrom.getText().split(":")[1].trim()));
		booking.setDestinationValue(lblReturnFrom.getText().split(":")[1].trim());
		booking.setReturnDate(
				DateTimeHelper.getDateString(DateTimeHelper.getDate(lblReturnDate.getText()), "dd/MM/yyyy"));
		booking.setNumberOfAdults(Integer.parseInt(lblNumberOfAdults.getText().split(":")[1].trim()));
		booking.setNumberOfChilden(Integer.parseInt(lblNumberOfChildren.getText().split(":")[1].trim()));
		booking.setNumberOfInfants(Integer.parseInt(lblNumberOfInfants.getText().split(":")[1].trim()));
		return booking;
	}

	// Section Store data for next page verifying
	public TicketInfo getTicketDetails() {
		TicketInfo ticketInfo = new TicketInfo();
		ticketInfo.setDepartureFrom(getDepartureFromInfo());
		ticketInfo.setDepartureTo(getDepartureToInfo());
		ticketInfo.setDepartureDate(
				DateTimeHelper.getDateString(DateTimeHelper.getDate(getDepartureDateInfo()), "dd/MM/yyyy"));
		ticketInfo.setReturnFrom(getReturnFromInfo());
		ticketInfo.setReturnTo(getReturnToInfo());
		ticketInfo
				.setReturnDate(DateTimeHelper.getDateString(DateTimeHelper.getDate(getReturnDateInfo()), "dd/MM/yyyy"));
		ticketInfo.setNumberOfAdults(getNumberOfAdultsInfo());
		ticketInfo.setNumberOfChildren(getNumberOfChildrenInfo());
		ticketInfo.setNumberOfInfants(getNumberOfInfantsInfo());
		ticketInfo.setDepartureFare(getCastValue(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.FARE))
				* (ticketInfo.getNumberOfAdults() + ticketInfo.getNumberOfChildren()
						+ ticketInfo.getNumberOfInfants()));
		ticketInfo.setDepartureTax(getCastValue(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.TAX)));
		ticketInfo.setDepartureCharge(getCastValue(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.CHARGES)));
		ticketInfo.setDepartureTotal(getCastValue(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.TOTAL)));
		ticketInfo.setDepartureTime(getSelectedTicketInfo(Trips.DEPARTURE, TicketDetails.TIME));
		ticketInfo.setReturnFare(
				getCastValue(getSelectedTicketInfo(Trips.RETURN, TicketDetails.FARE)) * (ticketInfo.getNumberOfAdults()
						+ ticketInfo.getNumberOfChildren() + ticketInfo.getNumberOfInfants()));
		ticketInfo.setReturnTax(getCastValue(getSelectedTicketInfo(Trips.RETURN, TicketDetails.TAX)));
		ticketInfo.setReturnCharge(getCastValue(getSelectedTicketInfo(Trips.RETURN, TicketDetails.CHARGES)));
		ticketInfo.setReturnTotal(getCastValue(getSelectedTicketInfo(Trips.RETURN, TicketDetails.TOTAL)));
		ticketInfo.setReturnTime(getSelectedTicketInfo(Trips.RETURN, TicketDetails.TIME));
		ticketInfo.setGrandTotal(ticketInfo.getDepartureTotal() + ticketInfo.getReturnTotal());
		return ticketInfo;
	}

	public String getSelectedTicketInfo(Trips ways, TicketDetails info) {
		String locator = null;
		if (ways.getValue().equals("Departure")) {
			locator = optDerparturPrice.getLocator().toString().substring(10);
		} else {
			locator = optReturnPrice.getLocator().toString().substring(10);
		}
		String _xpath = String.format(locator.concat("/input[@id='%s']"), info.getValue());
		return new Element(FindBy.xpath, _xpath).getAttribute("value");
	}

	enum Trips {
		DEPARTURE("Departure"), RETURN("Return");
		private String trip;

		Trips(String trip) {
			this.trip = trip;
		}

		public String getValue() {
			return this.trip;
		}
	}

	enum TicketDetails {
		FARE("fare"), TAX("fare_taxes"), TIME("depTime"), CHARGES("charges"), TOTAL("total_complete_charges");

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
}