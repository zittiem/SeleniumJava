package pages.VietJet;

import java.util.List;

import datatype.TicketInfo.FlightClass;
import driver.manager.Element;
import driver.setting.FindElementBy;

public class SelectTravelOptionsPage {
	// Element
	protected Element formTravelOption = new Element(FindElementBy.id,"TravelOptions");
	protected Element lblPageTitle = new Element("//div[@style='float: left']/h1");
	protected Element lblDisplayCurrency = new Element("//div[@style='float: left']/h3");
	protected Element lblDepartureFrom = new Element("//table[@id='tblLeg1APs']//td[1]");
	protected Element lblDepartureTo = new Element("//table[@id='tblLeg1APs']//td[2]");
	protected Element lblDepartureDate = new Element("//span [@id='Leg1Date']");
	protected Element lblReturnFrom = new Element("//table[@id='tblLeg2APs']//td[1]");
	protected Element lblReturnTo = new Element("//table[@id='tblLeg2APs']//td[2]");
	protected Element lblReturnDate = new Element("//span [@id='Leg2Date']");
	protected Element lblNumberOfAdults = new Element("//table[@id='tblPaxCountsInfo']//td[1]");
	protected Element lblNumberOfChildrens = new Element("//table[@id='tblPaxCountsInfo']//td[2]");
	protected Element lblNumberOfInfants = new Element("//table[@id='tblPaxCountsInfo']//td[3]");
	// ***For element that capture by index -> will update when having solution for
	// multi-languages
	// Select ticket
	protected Element optDerparturPrice = new Element(
			"//tr[contains(@id,'gridTravelOptDep')]//td[@data-familyid='%s']");
	protected Element optReturnPrice = new Element("//tr[contains(@id,'gridTravelOptRet')]//td[@data-familyid='%s']");

	protected Element btnContinue = new Element("//a[contains(@href,'continue')]");
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
	
	public PassengerInformationPage submit()
	{
		btnContinue.click();
		return new PassengerInformationPage();
	}

}
