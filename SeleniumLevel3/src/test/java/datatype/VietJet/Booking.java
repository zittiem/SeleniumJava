package datatype.VietJet;

import java.util.Date;

import utils.helper.DateTimeHelper;

public class Booking {
	private String flightOption = "Return";
	private String flightClass = null;
	private String departureFrom = null;
	private String departureTo = null;
	private String departureDate = DateTimeHelper.getDateString(new Date(), "dd/MM/yyyy");
	private String departureTime = null;
	private double departureFare = 0;
	private double departureCharge = 0;
	private double departureTax = 0;
	private double departureTotal = 0;
	private String returnFrom = departureTo;
	private String returnTo = departureFrom;
	private String returnDate = DateTimeHelper.getDateString(DateTimeHelper.plusDays(1), "dd/MM/yyyy");
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
		this.departureDate = DateTimeHelper.getDateString(departureDate, DataManager.SHARED_DATA.get().date_format);
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
		this.departureDate = DateTimeHelper.getDateString(returnDate, DataManager.SHARED_DATA.get().date_format);
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
			setDepartureDate(DateTimeHelper.getDateString(DateTimeHelper.mapDate(this.departureDate), "dd/MM/yyyy"));
		if (this.returnDate.contains(":"))
			setReturnDate(DateTimeHelper.getDateString(DateTimeHelper.mapDate(this.returnDate), "dd/MM/yyyy"));
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

	public enum FlightClass {
		ECO("Eco", "Eco"), PROMO("Promo", "Promo"), SKYBOSS("", "Skyboss"), NONE("", "");

		private String key;
		private String value;

		FlightClass(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public String getKey() {
			return this.key;
		}
	}

	public enum FlightOption {
		ONEWAY("One Way"), RETURN("Return"), NONE("");
		private String option;

		FlightOption(String option) {
			this.option = option;
		}

		public String getValue() {
			return this.option;
		}
	}

	public enum LocationOption {
		SGN("SGN", "Ho Chi Minh"), HAN("HAN", "Ha Noi"), BMV("BMV", "Buon Ma Thuot"), VCA("VCA", "Can Tho"),
		VCL("VCL", "Chu Lai"), DLI("DLI", "Da Lat"), DAD("DAD", "Da Nang"), VDH("VDH", "Dong Hoi"),
		HPH("HPH", "Hai Phong"), HUI("HUI", "Hue"), CXR("CXR", "Nha Trang"), PQC("PQC", "Phu Quoc"),
		PXU("PXU", "Pleiku"), UIH("UIH", "Quy Nhon"), THD("THD", "Thanh Hoa"), TBB("TBB", "Tuy Hoa"),
		VDO("VDO", "Van Don"), VII("VII", "Vinh"), BKK("BKK", "Bangkok - Suvarnabhumi"), CNX("CNX", "Chiang Mai"),
		CEI("CEI", "Chiang Rai"), KBV("KBV", "Krabi"), HKT("HKT", "Phuket"), UTH("UTH", "Udon Thani"),
		PUS("PUS", "Busan"), TAE("TAE", "Daegu"), HAK("HAK", "Haikou"), HND("HND", "Haneda Tokyo"),
		HFE("HFE", "Hefei Xinqiao"), HKG("HKG", "Hong Kong – Terminal 1"), KHH("KHH", "Kaohsiung"),
		KUL("KUL", "Kuala Lumpur – KLIA"), MFM("MFM", "Macau"), BOM("BOM", "Mumbai - Terminal 2"),
		DEL("DEL", "New Delhi"), DPS("DPS", "Ngurah Rai - Bali"), KIX("KIX", "Osaka - Terminal 1"),
		PNH("PNH", "Phnom Penh"), ICN("ICN", "Seoul"), REP("REP", "Siem Reap"), SIN("SIN", "Singapore - Terminal 4"),
		RMQ("RMQ", "Taichung"), TNN("TNN", "Tainan"), TPE("TPE", "Taipei - Terminal 1"), NRT("NRT", "Tokyo Narita"),
		RGN("RGN", "Yangon"), NONE("", "");

		private String key;
		private String value;

		LocationOption(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public String getKey() {
			return this.key;
		}

		public static String getCode(String value) {
			for (LocationOption e : LocationOption.values()) {
				if (e.value.equals(value))
					return e.key;
			}
			return null;
		}

		public static String getValue(String code) {
			for (LocationOption e : LocationOption.values()) {
				if (e.key.equals(code))
					return e.value;
			}
			return null;
		}
	}
}
