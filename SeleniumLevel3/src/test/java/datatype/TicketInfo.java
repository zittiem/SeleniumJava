package datatype;

import utils.helper.DateTimeHelper;

public class TicketInfo {

	String departureFrom = null;
	String departureTo = null;
	String departureDate = null;
	String departureTime = null;
	double departureFare = 0;
	double departureCharge = 0;
	double departureTax = 0;
	double departureTotal = 0;
	String returnFrom = null;
	String returnTo = null;
	String returnDate = null;
	String returnTime = null;
	double returnFare = 0;
	double returnCharge = 0;
	double returnTax = 0;
	double returnTotal = 0;
	double grandTotal = 0;
	int numberOfAdults = 0;
	int numberOfChildren = 0;
	int numberOfInfents = 0;

	public TicketInfo setBookingInfo(BookingInfo bI) {
		this.departureFrom = bI.getOrigin().getValue();
		this.departureTo = bI.getDestination().getValue();
		this.departureDate = DateTimeHelper.getDate(bI.getDepartDate());
		this.returnFrom = bI.getDestination().getValue();
		this.returnTo = bI.getOrigin().getValue();
		this.returnDate = DateTimeHelper.getDate(bI.getReturnDate());
		this.numberOfAdults = bI.getNumberOfAdults();
		this.numberOfChildren = bI.getNumberOfChildens();
		this.numberOfInfents = bI.getNumberOfInfants();
		return this;
	}

	public String getDepartureFrom() {
		return departureFrom;
	}

	public void setDepartureFrom(String departureFrom) {
		this.departureFrom = departureFrom;
	}

	public String getDepartureTo() {
		return departureTo;
	}

	public void setDepartureTo(String departureTo) {
		this.departureTo = departureTo;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public double getDepartureFare() {
		return departureFare;
	}

	public void setDepartureFare(double departureFare) {
		this.departureFare = departureFare;
	}

	public double getDepartureCharge() {
		return departureCharge;
	}

	public void setDepartureCharge(double departureCharge) {
		this.departureCharge = departureCharge;
	}

	public double getDepartureTax() {
		return departureTax;
	}

	public void setDepartureTax(double departureTax) {
		this.departureTax = departureTax;
	}

	public double getDepartureTotal() {
		return departureTotal;
	}

	public void setDepartureTotal(double departureTotal) {
		this.departureTotal = departureTotal;
	}

	public String getReturnFrom() {
		return returnFrom;
	}

	public void setReturnFrom(String returnFrom) {
		this.returnFrom = returnFrom;
	}

	public String getReturnTo() {
		return returnTo;
	}

	public void setReturnTo(String returnTo) {
		this.returnTo = returnTo;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public double getReturnFare() {
		return returnFare;
	}

	public void setReturnFare(double returnFare) {
		this.returnFare = returnFare;
	}

	public double getReturnCharge() {
		return returnCharge;
	}

	public void setReturnCharge(double returnCharge) {
		this.returnCharge = returnCharge;
	}

	public double getReturnTax() {
		return returnTax;
	}

	public void setReturnTax(double returnTax) {
		this.returnTax = returnTax;
	}

	public double getReturnTotal() {
		return returnTotal;
	}

	public void setReturnTotal(double returnTotal) {
		this.returnTotal = returnTotal;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public int getNumberOfInfents() {
		return numberOfInfents;
	}

	public void setNumberOfInfents(int numberOfInfents) {
		this.numberOfInfents = numberOfInfents;
	}

	public void showInfo() {
		System.out.println("===================Ticket Information===================" + "\n- DepartureFrom: "
				+ this.departureFrom + "\n- DepartureTo: " + this.departureTo + "\n- DepartureDate: "
				+ this.departureDate + "\n- DepartureTime: " + this.departureTime + "\n- DepartureFare: "
				+ this.departureFare + "\n- DepartureCharge: " + this.departureCharge + "\n- DepartureTax: "
				+ this.departureTax + "\n- DepartureTotal: " + this.departureTotal + "\n- ReturnFrom: "
				+ this.returnFrom + "\n- ReturnTo: " + this.returnTo + "\n- ReturnDate: " + this.returnDate
				+ "\n- ReturnTime: " + this.returnTime + "\n- ReturnFare: " + this.returnFare + "\n- ReturnCharge: "
				+ this.returnCharge + "\n- ReturnTax: " + this.returnTax + "\n- ReturnTotal: " + this.returnTotal
				+ "\n- GrandTotal: " + this.grandTotal + "\n- NumberOfAdults: " + this.numberOfAdults
				+ "\n- NumberOfChildren: " + this.numberOfChildren + "\n- NumberOfInfents: " + this.numberOfInfents
				+ "\n=========================================================");

	}

	public enum FlightClass {
		ECO("Eco"), PROMO("Promo"), SKYBOSS("Skyboss");

		private String flightClass;

		FlightClass(String flightClass) {
			this.flightClass = flightClass;
		}

		public String getValue() {
			return this.flightClass;
		}
	}

}
