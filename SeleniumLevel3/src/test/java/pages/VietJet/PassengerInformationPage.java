package pages.VietJet;

import datatype.VietJet.TicketInfo;
import driver.manager.DriverUtils;
import element.base.web.Element;
import helper.LocatorHelper;
import utils.constants.Constants;
import utils.helper.DateTimeHelper;

public class PassengerInformationPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH, getClass().getSimpleName());
	// Element
	protected Element formDetail = new Element(locator.getLocator("formDetail"));
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
	protected Element btnContinue = new Element(locator.getLocator("btnContinue"));

	// Methods
	private double getCastValue(String value) {
		return Double.parseDouble(value.split(" ")[0].replace(",", "")) * 1;
	}

	public TicketInfo getCurrentTicketInfo() {
		TicketInfo ticketInfo = new TicketInfo();
		ticketInfo.setDepartureFrom(lblDepartureFrom.getText().split(":")[1].trim());
		ticketInfo.setDepartureTo(lblDepartureTo.getText().split(":")[1].trim());
		ticketInfo.setDepartureDate(
				DateTimeHelper.getDateString(DateTimeHelper.getDate(lblDepartureDate.getText()), "dd/MM/yyyy"));
		ticketInfo.setDepartureTime(lblDepartureTime.getText());
		ticketInfo.setDepartureFare(getCastValue(lblDepartureFare.getText()));
		ticketInfo.setDepartureCharge(getCastValue(lblDepartureCharges.getText()));
		ticketInfo.setDepartureTax(getCastValue(lblDepartureTax.getText()));
		ticketInfo.setDepartureTotal(getCastValue(lblDepartureTotalFare.getText()));
		ticketInfo.setReturnFrom(lblReturnFrom.getText().split(":")[1].trim());
		ticketInfo.setReturnTo(lblReturnTo.getText().split(":")[1].trim());
		ticketInfo.setReturnDate(
				DateTimeHelper.getDateString(DateTimeHelper.getDate(lblReturnDate.getText()), "dd/MM/yyyy"));
		ticketInfo.setReturnTime(lblReturnTime.getText());
		ticketInfo.setReturnFare(getCastValue(lblReturnFare.getText()));
		ticketInfo.setReturnCharge(getCastValue(lblReturnCharges.getText()));
		ticketInfo.setReturnTax(getCastValue(lblReturnTax.getText()));
		ticketInfo.setReturnTotal(getCastValue(lblReturnTotalFare.getText()));
		ticketInfo.setGrandTotal(getCastValue(lblGrandTotal.getText()));
		ticketInfo.setNumberOfAdults(Integer.parseInt(lblNumberOfAdults.getText().split(":")[1].trim()));
		ticketInfo.setNumberOfChildren(Integer.parseInt(lblNumberOfChildren.getText().split(":")[1].trim()));
		ticketInfo.setNumberOfInfants(Integer.parseInt(lblNumberOfInfants.getText().split(":")[1].trim()));
		return ticketInfo;
	}

	public void waitForPageLoad() {
		formDetail.waitForDisplayed(Constants.LONG_TIME);
	}

	public boolean isDisplayed() {
		waitForPageLoad();
		return DriverUtils.getURL().contains("Details.aspx");
	}
}
