package pages.VietJet;

import driver.manager.Element;
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
	protected Element rbnDerparturPrice = new Element(locator.getLocate("rbnDerparturPrice"));
	protected Element rbnReturnPrice = new Element(locator.getLocate("rbnReturnPrice"));

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
