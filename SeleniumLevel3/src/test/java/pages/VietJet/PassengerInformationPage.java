package pages.VietJet;

import datatype.VietJet.Booking;
import driver.manager.DriverUtils;
import element.base.web.Element;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;
import utils.helper.DateTimeHelper;

public class PassengerInformationPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName, getClass().getSimpleName());
	
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

	public Booking getCurrentBookingInfo() {
		Booking booking = new Booking();
		lblGrandTotal.waitForDisplayed(30);
		lblGrandTotal.waitForTextChanged("0", 30);
		booking.setDepartureFrom(lblDepartureFrom.getText().split(":")[1].trim());
		booking.setDepartureTo(lblDepartureTo.getText().split(":")[1].trim());
		booking.setDepartureDate(
				DateTimeHelper.getDateString(DateTimeHelper.getDate(lblDepartureDate.getText()), ResourceHelper.SHARED_DATA.get().date_format));
		booking.setDepartureTime(lblDepartureTime.getText());
		booking.setDepartureFare(getCastValue(lblDepartureFare.getText()));
		booking.setDepartureCharge(getCastValue(lblDepartureCharges.getText()));
		booking.setDepartureTax(getCastValue(lblDepartureTax.getText()));
		booking.setDepartureTotal(getCastValue(lblDepartureTotalFare.getText()));
		booking.setReturnFrom(lblReturnFrom.getText().split(":")[1].trim());
		booking.setReturnTo(lblReturnTo.getText().split(":")[1].trim());
		booking.setReturnDate(
				DateTimeHelper.getDateString(DateTimeHelper.getDate(lblReturnDate.getText()), ResourceHelper.SHARED_DATA.get().date_format));
		booking.setReturnTime(lblReturnTime.getText());
		booking.setReturnFare(getCastValue(lblReturnFare.getText()));
		booking.setReturnCharge(getCastValue(lblReturnCharges.getText()));
		booking.setReturnTax(getCastValue(lblReturnTax.getText()));
		booking.setReturnTotal(getCastValue(lblReturnTotalFare.getText()));
		booking.setGrandTotal(getCastValue(lblGrandTotal.getText()));
		booking.setNumberOfAdults(Integer.parseInt(lblNumberOfAdults.getText().split(":")[1].trim()));
		booking.setNumberOfChildren(Integer.parseInt(lblNumberOfChildren.getText().split(":")[1].trim()));
		booking.setNumberOfInfants(Integer.parseInt(lblNumberOfInfants.getText().split(":")[1].trim()));
		return booking;
	}

	public void waitForPageLoad() {
		formDetail.waitForDisplayed(Constants.LONG_TIME);
	}

	public boolean isDisplayed() {
		waitForPageLoad();
		return DriverUtils.getURL().contains("Details.aspx");
	}
}
