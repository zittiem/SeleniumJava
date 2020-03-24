package pages.VietJet;

import java.util.List;

import datatype.BookingInfo;
import datatype.TicketInfo;
import datatype.TicketInfo.FlightClass;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.setting.FindBy;
import element.wrapper.web.CheckBox;
import helper.LocatorHelper;
import utils.assertion.SoftAssertion;
import utils.common.Constants;
import utils.helper.DateTimeHelper;

public class SelectTravelOptionsPage {
	LocatorHelper locator = new LocatorHelper(getClass().getSimpleName());
	// Element
	protected Element formTravelOption = new Element(FindBy.id, "TravelOptions");
	protected Element lblPageTitle = new Element(FindBy.xpath, "//div[@style='float: left']/h1");
	// Booking Summary
	protected Element lblDisplayCurrency = new Element(FindBy.xpath, "//span[@class='BSGrandTotal' and text()='%s']");
	protected Element lblDepartureFrom = new Element(FindBy.xpath, "//table[@id='tblLeg1APs']//td[1]");
	protected Element lblDepartureTo = new Element(FindBy.xpath, "//table[@id='tblLeg1APs']//td[2]");
	protected Element lblDepartureDate = new Element(FindBy.id, "Leg1Date");
	protected Element lblDepartureTime = new Element(FindBy.id, "Leg1Time");
	protected Element lblDepartureFare = new Element(FindBy.id, "Leg1BSFare");
	protected Element lblDepartureCharges = new Element(FindBy.id, "Leg1BSCharges");
	protected Element lblDepartureTax = new Element(FindBy.id, "Leg1BSFareTax");
	protected Element lblDepartureTotalFare = new Element(FindBy.id, "Leg1BSTotalFare");

	protected Element lblReturnFrom = new Element(FindBy.xpath, "//table[@id='tblLeg2APs']//td[1]");
	protected Element lblReturnTo = new Element(FindBy.xpath, "//table[@id='tblLeg2APs']//td[2]");
	protected Element lblReturnDate = new Element(FindBy.id, "Leg2Date");
	protected Element lblReturnTime = new Element(FindBy.id, "Leg2Time");
	protected Element lblReturnFare = new Element(FindBy.id, "Leg2BSFare");
	protected Element lblReturnCharges = new Element(FindBy.id, "Leg2BSCharges");
	protected Element lblReturnTax = new Element(FindBy.id, "Leg2BSFareTax");
	protected Element lblReturnTotalFare = new Element(FindBy.id, "Leg2BSTotalFare");
	protected Element lblGrandTotal = new Element(FindBy.id, "BSGrandTotal");

	protected Element lblNumberOfAdults = new Element(FindBy.xpath, "//table[@id='tblPaxCountsInfo']//td[1]");
	protected Element lblNumberOfChildrens = new Element(FindBy.xpath, "//table[@id='tblPaxCountsInfo']//td[2]");
	protected Element lblNumberOfInfants = new Element(FindBy.xpath, "//table[@id='tblPaxCountsInfo']//td[3]");
	// ***For element that capture by index -> will update when having solution for
	// multi-languages

	// Select ticket
	protected Element optDerparturPrice = new Element(FindBy.xpath,
			"//tr[contains(@id,'gridTravelOptDep')]//td[@data-familyid='%s']");
	protected Element optReturnPrice = new Element(FindBy.xpath,
			"//tr[contains(@id,'gridTravelOptRet')]//td[@data-familyid='%s']");
	protected Element btnContinue = new Element(FindBy.xpath, "//a[contains(@href,'continue')]");

	// Method

	public SelectTravelOptionsPage waitForPageLoad() {
		this.btnContinue.waitForDisplayed(Constants.LONG_TIME);
		return this;
	}

	public boolean isDisplayed() {
		return DriverUtils.getURL().contains("TravelOptions.aspx");
	}

	// Find the cheapest flight option
	public void selectCheapestDeparture(FlightClass flightClass) {
		List<Element> elements = optDerparturPrice.Dynamic(flightClass.getValue()).getWrapperElements();
		double price = getMonney(elements.get(0));
		Element expElement = elements.get(0);
		for (int i = 1; i < elements.size(); i++) {
			if (getMonney(elements.get(i)) < price) {
				price = getMonney(elements.get(i));
				expElement = elements.get(i);
			}
		}
		String _xpath = expElement.getLocator().toString().substring(10) + "/input[@id='gridTravelOptDep']";
		CheckBox rdxCheapestDeparture = new CheckBox(FindBy.xpath, _xpath);
		rdxCheapestDeparture.scrollIntoView();
		rdxCheapestDeparture.check();
	}

	public void selectCheapestReturn(FlightClass flightClass) {
		List<Element> elements = optReturnPrice.Dynamic(flightClass.getValue()).getWrapperElements();
		double price = getMonney(elements.get(0));
		Element expElement = elements.get(0);
		for (int i = 1; i < elements.size(); i++) {
			if (getMonney(elements.get(i)) < price) {
				price = getMonney(elements.get(i));
				expElement = elements.get(i);
			}
		}
		String _xpath = expElement.getLocator().toString().substring(10) + "/input[@id='gridTravelOptRet']";
		CheckBox rdxCheapestReturn = new CheckBox(FindBy.xpath, _xpath);
		rdxCheapestReturn.scrollIntoView();
		rdxCheapestReturn.check();
	}

	private double getMonney(Element element) {
		return Double.parseDouble(element.getText().split(" ")[0].replace(",", ""));
	}

	public String getDepartureFromInfo() {
		return lblDepartureFrom.getText().split(":")[1].trim();
	}

	public String getDepartureToInfo() {
		return lblDepartureTo.getText().split(":")[1].trim();
	}

	public String getDepartureDateInfo() {
		return lblDepartureDate.getText().trim();
	}

	public String getReturnFromInfo() {
		return lblReturnFrom.getText().split(":")[1].trim();
	}

	public String getReturnToInfo() {
		return lblReturnTo.getText().split(":")[1].trim();
	}

	public String getReturnDateInfo() {
		return lblReturnDate.getText().trim();
	}

	public int getNumberOfAdultsInfo() {
		return Integer.parseInt(lblNumberOfAdults.getText().split(":")[1].trim());
	}

	public int getNumberOfChildrensInfo() {
		return Integer.parseInt(lblNumberOfChildrens.getText().split(":")[1].trim());
	}

	public int getNumberOfInfantsInfo() {
		return Integer.parseInt(lblNumberOfInfants.getText().split(":")[1].trim());
	}

	public void selectCheapestTicket(FlightClass flightClass) {
		selectCheapestDeparture(flightClass);
		selectCheapestReturn(flightClass);
	}

	public PassengerInformationPage submitPage() {
		btnContinue.moveToElement();
		btnContinue.click();
		return new PassengerInformationPage();
	}

	// Assert
	SoftAssertion softAssert = new SoftAssertion();
	// SoftAssert softAssert = new SoftAssert();

	public boolean isCurrency(String currency) {
		System.out.println(lblDisplayCurrency.Dynamic(currency).getAttribute("innerText"));
		return lblDisplayCurrency.Dynamic(currency).getAttribute("innerText").equals(currency);
	}

	public boolean isBookingInfoCorrect(BookingInfo booking) {
		return getDepartureFromInfo().equals(booking.getOrigin().getValue())
				&& getDepartureToInfo().equals(booking.getDestination().getValue())
				&& getDepartureDateInfo().equals(DateTimeHelper.getDate(booking.getDepartDate()))
				&& getReturnFromInfo().equals(booking.getDestination().getValue())
				&& getReturnToInfo().equals(booking.getOrigin().getValue())
				&& getReturnDateInfo().equals(DateTimeHelper.getDate((booking.getReturnDate())))
				&& getNumberOfAdultsInfo() == booking.getNumberOfAdults()
				&& getNumberOfChildrensInfo() == booking.getNumberOfChildens()
				&& getNumberOfInfantsInfo() == booking.getNumberOfInfants();
	}

	// Section Store data for next page verifying
	TicketInfo ticketInfo = new TicketInfo();

	public TicketInfo getTicketInfo() {
		ticketInfo.setDepartureFare(getMonney(lblDepartureFare));
		ticketInfo.setDepartureTax(getMonney(lblDepartureTax));
		ticketInfo.setDepartureCharge(getMonney(lblDepartureCharges));
		ticketInfo.setDepartureTotal(getMonney(lblDepartureTotalFare));
		ticketInfo.setDepartureTime(lblDepartureTime.getText().trim());
		ticketInfo.setReturnFare(getMonney(lblReturnFare));
		ticketInfo.setReturnTax(getMonney(lblReturnTax));
		ticketInfo.setReturnCharge(getMonney(lblReturnCharges));
		ticketInfo.setReturnTotal(getMonney(lblReturnTotalFare));
		ticketInfo.setReturnTime(lblReturnTime.getText().trim());
		ticketInfo.setGrandTotal(getMonney(lblGrandTotal));
		return ticketInfo;
	}

}