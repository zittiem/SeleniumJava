package pages.VietJet;

import java.util.List;

import datatype.TicketInfo.FlightClass;
import element.resource.Element;
import element.setting.FindElementBy;
import driver.manager.Locator;

public class SelectTravelOptionsPage {	
	Locator locator = new Locator(getClass().getSimpleName());
	
	// Element
	protected Element formTravelOption = new Element(locator.getLocate("formTravelOption"));
	protected Element lblPageTitle = new Element(locator.getLocate("lblPageTitle"));
	protected Element lblDisplayCurrency = new Element(locator.getLocate("lblDisplayCurrency"));
	protected Element lblDepartureFrom = new Element(locator.getLocate("lblDepartureFrom"));
	protected Element lblDepartureTo = new Element(locator.getLocate("lblDepartureTo"));
	protected Element lblDepartureDate = new Element(locator.getLocate("lblDepartureDate"));
	protected Element lblReturnFrom = new Element(locator.getLocate("lblReturnFrom"));
	protected Element lblReturnTo = new Element(locator.getLocate("lblReturnTo"));
	protected Element lblReturnDate = new Element(locator.getLocate("lblReturnDate"));
	protected Element lblNumberOfAdults = new Element(locator.getLocate("lblNumberOfAdults"));
	protected Element lblNumberOfChildrens = new Element(locator.getLocate("lblNumberOfChildrens"));
	protected Element lblNumberOfInfants = new Element(locator.getLocate("lblNumberOfInfants"));
	
	//Select ticket
	protected Element optDerparturPrice = new Element(locator.getLocate("optDerparturPrice"));
	protected Element optReturnPrice = new Element(locator.getLocate("optReturnPrice"));

	protected Element btnContinue = new Element(locator.getLocate("btnContinue"));
	// Method
	public boolean isDisplayed() {
		return (this.lblPageTitle.isDisplayed() && this.formTravelOption.isDisplayed());
	}

	public boolean isCurrencyDisplay(String currency) {
		return this.lblDisplayCurrency.getText().contains(currency);
	}

	// Find the cheapest flight option

	public Element getCheapestDeparture(FlightClass flightClass) {
		return this.getCheapestOption(optDerparturPrice.getElement(FindElementBy.xpath, flightClass.getValue()));
	}

	public Element getCheapestReturn(FlightClass flightClass) {
		return this.getCheapestOption(optReturnPrice.getElement(FindElementBy.xpath, flightClass.getValue()));
	}

	private Element getCheapestOption(Element element) {
		List<Element> elements = element.getElements();
		int price = 0;
		Element expElement = null;
		for (int i = 1; i <= elements.size(); i++) {
			String _xpath = element.getLocation().toString().substring(10);
			Element _element = new Element(FindElementBy.xpath, "(" + _xpath + ")[" + i + "]");
			System.out.println(getTicketPrice(_element));
			if (getTicketPrice(_element) < price) {
				price = getTicketPrice(_element);
				expElement = _element;
			}
		}
		return expElement;
	}

	private int getTicketPrice(Element element) {
		return Integer.parseInt(element.getText().split(" ")[1].replace(",", ""));
	}

	public String getDepartureFromInfo() {
		return lblDepartureFrom.getText();
	}

	public String getDepartureToInfo() {
		return lblDepartureTo.getText();
	}

	public String getDepartureDateInfo() {
		return lblDepartureDate.getText();
	}

	public String getReturnFromInfo() {
		return lblReturnFrom.getText();
	}

	public String getReturnToInfo() {
		return lblReturnTo.getText();
	}

	public String getReturnDateInfo() {
		return lblReturnDate.getText();
	}

	public String getNumberOfAdultsInfo() {
		return lblNumberOfAdults.getText();
	}

	public String getNumberOfChildrensInfo() {
		return lblNumberOfChildrens.getText();
	}

	public String getNumberOfInfantsInfo() {
		return lblNumberOfInfants.getText();
	}

	public SelectTravelOptionsPage selectCheapestTicket(FlightClass flightClass) {
		getCheapestDeparture(flightClass).click();
		getCheapestReturn(flightClass).click();
		return this;
	}
	
//	public PassengerInformationPage submit()
//	{
//		btnContinue.click();
//		return new PassengerInformationPage();
//	}

}