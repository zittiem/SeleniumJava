package pages.VietJet;

import datatype.TicketInfo;
import element.base.web.Element;
import element.setting.FindBy;
import utils.assertion.SoftAssertion;

public class PassengerInformationPage {
	// Element
	protected Element formDetail = new Element(FindBy.id, "Details");
	// Booking Summary
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

	// Methods
	private double getMonney(Element element) {
		return Double.parseDouble(element.getText().split(" ")[0].replace(",", ""));
	}

	// Assert
	SoftAssertion softAssert = new SoftAssertion();

	public void checkTicketInfomation(TicketInfo ticket) {
		softAssert.assertTrue(lblDepartureFrom.getText().contains(ticket.getDepartureFrom()));
		softAssert.assertTrue(lblDepartureTo.getText().contains(ticket.getDepartureTo()));
		softAssert.assertEquals(lblDepartureDate.getText(),ticket.getDepartureDate());
		softAssert.assertEquals(lblDepartureTime.getText(),ticket.getDepartureTime());
		softAssert.assertEquals(getMonney(lblDepartureFare),ticket.getDepartureFare());
		softAssert.assertEquals(getMonney(lblDepartureCharges),ticket.getDepartureCharge());
		softAssert.assertEquals(getMonney(lblDepartureTax), ticket.getDepartureTax() / 2);
		softAssert.assertEquals(getMonney(lblDepartureTotalFare), ticket.getDepartureTotal());
		softAssert.assertTrue(lblReturnFrom.getText().contains(ticket.getReturnFrom()));
		softAssert.assertTrue(lblReturnTo.getText().contains(ticket.getReturnTo()));
		softAssert.assertEquals(lblReturnDate.getText(), ticket.getReturnDate());
		softAssert.assertEquals(lblReturnTime.getText(), ticket.getReturnTime());
		softAssert.assertEquals(getMonney(lblReturnFare), ticket.getReturnFare());
		softAssert.assertEquals(getMonney(lblReturnCharges), ticket.getReturnCharge());
		softAssert.assertEquals(getMonney(lblReturnTax), ticket.getReturnTax() / 2);
		softAssert.assertEquals(getMonney(lblReturnTotalFare), ticket.getReturnTotal());
		softAssert.assertEquals(getMonney(lblGrandTotal), ticket.getGrandTotal());
	}

	public boolean isDisplayed() {
		return formDetail.isDisplayed();
	}
}
