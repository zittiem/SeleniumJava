package datatype.VietJet;

import helper.JsonHelper;

public class Ticket {

	private String departureFrom = null;
	private String departureTo = null;
	private String departureDate = null;
	private String departureTime = null;
	private double departureFare = 0;
	private double departureCharge = 0;
	private double departureTax = 0;
	private double departureTotal = 0;
	private String returnFrom = null;
	private String returnTo = null;
	private String returnDate = null;
	private String returnTime = null;
	private double returnFare = 0;
	private double returnCharge = 0;
	private double returnTax = 0;
	private double returnTotal = 0;
	private double grandTotal = 0;
	private int numberOfAdults = 0;
	private int numberOfChildren = 0;
	private int numberOfInfants = 0;

	public Ticket setBookingInfo(Booking bI) {
		this.departureFrom = bI.getOriginValue();
		this.departureTo = bI.getDestinationValue();
		this.departureDate = bI.getDepartureDate();
		this.returnFrom = bI.getDestinationValue();
		this.returnTo = bI.getOriginValue();
		this.returnDate = bI.getReturnDate();
		this.numberOfAdults = bI.getNumberOfAdults();
		this.numberOfChildren = bI.getNumberOfChildren();
		this.numberOfInfants = bI.getNumberOfInfants();
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

	public int getNumberOfInfants() {
		return numberOfInfants;
	}

	public void setNumberOfInfants(int numberOfInfants) {
		this.numberOfInfants = numberOfInfants;
	}

	@Override
	public String toString() {
		return String.format("<pre>=================== Ticket Information ===================\n%s</pre>", JsonHelper.convertObjectToPrettyJsonString(this));
	}
}
