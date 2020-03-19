package pages.VietJet;

import element.resource.Element;

public class SelectTravelOptionsPage {
	// Element

	protected Element formTravelOption = new Element("//form[@id='TravelOptions']");
	protected Element lblPageTitle = new Element("//div[@style='float: left']/h1");
	protected Element lblDisplayCurrency = new Element("//div[@style='float: left']/h3");
	protected Element lblDepartureFrom = new Element(
			"//table[@id='tblLeg1APs']//td[./span[@class='label' and text()='Từ: ' or text()='From: ']]");
	protected Element lblDepartureTo = new Element(
			"//table[@id='tblLeg1APs']//td[./span[@class='label' and text()='Đến: ' or text()='To: ']]");
	protected Element lblDepartureDate = new Element("//span [@id='Leg1Date']");
	protected Element lblReturnFrom = new Element(
			"//table[@id='tblLeg2APs']//td[./span[@class='label' and text()='Từ: ' or text()='From: ']]");
	protected Element lblReturnTo = new Element(
			"//table[@id='tblLeg2APs']//td[./span[@class='label' and text()='Đến: ' or text()='To: ']]");
	protected Element lblReturnDate = new Element("//span [@id='Leg2Date']");
	protected Element lblNumberOfAdults = new Element(
			"//table[@id='tblPaxCountsInfo']//td[./span[starts-with(text(),'Adults')]]");
	protected Element lblNumberOfChildrens = new Element(
			"//table[@id='tblPaxCountsInfo']//td[./span[starts-with(text(),'Children')]]");
	protected Element lblNumberOfInfants = new Element(
			"//table[@id='tblPaxCountsInfo']//td[./span[starts-with(text(),'Infants')]]");
//Select ticket

	protected Element rbnDerparturPrice = new Element(
			"//tr[contains(@id,'gridTravelOptDep')]//td[@data-familyid='%s']");
	protected Element rbnReturnPrice = new Element("//tr[contains(@id,'gridTravelOptRet')]//td[@data-familyid='%s']");

	// Method
	public boolean isDisplayed() {

		// DriverUtils.getTitle();
		return (this.lblPageTitle.isDisplayed() && this.lblDisplayCurrency.isDisplayed()
				&& this.formTravelOption.isDisplayed());
	}

	public boolean isCurrencyDisplay(String currency) {
		return this.lblDisplayCurrency.getText().contains(currency);
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
}
