package datatype.VietJet;

import java.util.Date;

import utils.helper.ResourceHelper;
import utils.helper.DateTimeHelper;

public class Booking {
	private String flightOption = "Return";
	private String flightClass = null;
	private String departureFrom = null;
	private String departureTo = null;
	private String departureDate = DateTimeHelper.getDateString(new Date(), ResourceHelper.SHARED_DATA.get().date_format);
	private String departureTime = null;
	private double departureFare = 0;
	private double departureCharge = 0;
	private double departureTax = 0;
	private double departureTotal = 0;
	private String returnFrom = departureTo;
	private String returnTo = departureFrom;
	private String returnDate = DateTimeHelper.getDateString(DateTimeHelper.plusDays(1), ResourceHelper.SHARED_DATA.get().date_format);
	private String returnTime = null;
	private double returnFare = 0;
	private double returnCharge = 0;
	private double returnTax = 0;
	private double returnTotal = 0;
	private double grandTotal = 0;
	private String currency = "VND";
	private boolean lowestFare = false;
	private String promoCode = null;
	private int numberOfAdults = 1;
	private int numberOfChildren = 0;
	private int numberOfInfants = 0;

	public String getFlightOption() {
		return flightOption;
	}

	public void setFlightOption(String flightOption) {
		this.flightOption = flightOption;
	}

	public String getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
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

	public void setDepartureDate(Date departureDate) {
		this.departureDate = DateTimeHelper.getDateString(departureDate, ResourceHelper.SHARED_DATA.get().date_format);
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

	public void setReturnDate(Date returnDate) {
		this.returnDate = DateTimeHelper.getDateString(returnDate, ResourceHelper.SHARED_DATA.get().date_format);
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean isLowestFare() {
		return lowestFare;
	}

	public void setLowestFare(boolean lowestFare) {
		this.lowestFare = lowestFare;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
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

	// Update all the values if need to calculate after providing data.
	public Booking compileData() {
		if (this.departureDate.contains(":"))
			setDepartureDate(DateTimeHelper.getDateString(DateTimeHelper.mapDate(this.departureDate), ResourceHelper.SHARED_DATA.get().date_format));
		if (this.returnDate.contains(":"))
			setReturnDate(DateTimeHelper.getDateString(DateTimeHelper.mapDate(this.returnDate), ResourceHelper.SHARED_DATA.get().date_format));
		setReturnFrom(getDepartureTo());
		setReturnTo(getDepartureFrom());
		return this;
	}

	@Override
	public String toString() {
		return "New_BookingInfo [flightOption=" + flightOption + ", flightClass=" + flightClass + ", departureFrom="
				+ departureFrom + ", departureTo=" + departureTo + ", departureDate=" + departureDate
				+ ", departureTime=" + departureTime + ", departureFare=" + departureFare + ", departureCharge="
				+ departureCharge + ", departureTax=" + departureTax + ", departureTotal=" + departureTotal
				+ ", returnFrom=" + returnFrom + ", returnTo=" + returnTo + ", returnDate=" + returnDate
				+ ", returnTime=" + returnTime + ", returnFare=" + returnFare + ", returnCharge=" + returnCharge
				+ ", returnTax=" + returnTax + ", returnTotal=" + returnTotal + ", grandTotal=" + grandTotal
				+ ", numberOfAdults=" + numberOfAdults + ", numberOfChildren=" + numberOfChildren + ", numberOfInfants="
				+ numberOfInfants + "]";
	}
}
