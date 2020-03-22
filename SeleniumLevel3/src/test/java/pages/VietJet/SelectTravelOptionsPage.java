package pages.VietJet;

import java.util.List;

import org.openqa.selenium.WebElement;

import datatype.TicketInfo.FlightClass;
import element.resource.web.Element;
import element.setting.FindBy;
import helper.LocatorHelper;

public class SelectTravelOptionsPage {	
	LocatorHelper locator = new LocatorHelper(getClass().getSimpleName());
	
	// Element
	protected Element formTravelOption = new Element(locator.getLocator("formTravelOption"));
	protected Element lblPageTitle = new Element(locator.getLocator("lblPageTitle"));
	protected Element lblDisplayCurrency = new Element(locator.getLocator("lblDisplayCurrency"));
	protected Element lblDepartureFrom = new Element(locator.getLocator("lblDepartureFrom"));
	protected Element lblDepartureTo = new Element(locator.getLocator("lblDepartureTo"));
	protected Element lblDepartureDate = new Element(locator.getLocator("lblDepartureDate"));
	protected Element lblReturnFrom = new Element(locator.getLocator("lblReturnFrom"));
	protected Element lblReturnTo = new Element(locator.getLocator("lblReturnTo"));
	protected Element lblReturnDate = new Element(locator.getLocator("lblReturnDate"));
	protected Element lblNumberOfAdults = new Element(locator.getLocator("lblNumberOfAdults"));
	protected Element lblNumberOfChildrens = new Element(locator.getLocator("lblNumberOfChildrens"));
	protected Element lblNumberOfInfants = new Element(locator.getLocator("lblNumberOfInfants"));
	
	//Select ticket
	protected Element optDerparturPrice = new Element(locator.getLocator("optDerparturPrice"));
	protected Element optReturnPrice = new Element(locator.getLocator("optReturnPrice"));

	protected Element btnContinue = new Element(locator.getLocator("btnContinue"));
	// Method
	public boolean isDisplayed() {
		return (this.lblPageTitle.isDisplayed() && this.formTravelOption.isDisplayed());
	}

	public boolean isCurrencyDisplay(String currency) {
		return this.lblDisplayCurrency.getText().contains(currency);
	}

	// Find the cheapest flight option

	public Element getCheapestDeparture(FlightClass flightClass) {
		return this.getCheapestOption(optDerparturPrice.Dynamic(flightClass.getValue()));
	}

	public Element getCheapestReturn(FlightClass flightClass) {
		return this.getCheapestOption(optReturnPrice.Dynamic(flightClass.getValue()));
	}

	private Element getCheapestOption(Element element) {
		List<WebElement> elements = element.getElements();
		int price = 0;
		Element expElement = null;
		for (int i = 1; i <= elements.size(); i++) {
			String _xpath = element.getLocation().toString().substring(10);
			Element _element = new Element(FindBy.xpath, "(" + _xpath + ")[" + i + "]");
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