package datatype;

import java.util.Date;

import utils.common.Common;

public class FlightInfo {

	private FlightOption flightOption = FlightOption.RETURN;
	private LocationOption origin;
	private Date departDate;
	private LocationOption destination;
	private Date returnDate;
	private String currency ="";
	private int numberOfAdults;
	private boolean lowestFare = false;
	private String promoCode = "";
	private int numberOfChildens = 0;
	private int numberOfInfants = 0;

	public FlightOption getFlightOption() {
		return flightOption;
	}

	public LocationOption getOrigin() {
		return origin;
	}

	public Date getDepartDate() {
		return departDate;
	}

	public LocationOption getDestination() {
		return destination;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public String getCurrency() {
		return currency;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public boolean isLowestFare() {
		return lowestFare;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public int getNumberOfChildens() {
		return numberOfChildens;
	}

	public int getNumberOfInfants() {
		return numberOfInfants;
	}

	public void showInfo() {
		System.out.println("===================Flight Information===================" + "\nFlight Option: " + this.flightOption + "\nOrigin: "
				+ this.origin.getValue() + "\nDepart Date: " + Common.getDate(this.departDate) + "\nDestination :" + this.destination.getValue()
				+ "\nReturn Date: " + Common.getDate(this.returnDate) + "\nCurrency: " + this.currency + "\nNumber Of Adults: "
				+ this.numberOfAdults + "\nSelect lowest fare: " + this.lowestFare + "\nPromo Code: "
				+ this.promoCode + "\nNumber of Childrens: " + this.numberOfChildens + "\nNumber of Infants: "
				+ this.numberOfInfants + "\n=========================================================");
	}
	public FlightInfo(FlightOption flightOption, LocationOption origin, Date departDate, LocationOption destination,
			Date returnDate, String currency, int numberOfAdults, boolean lowestFare, String promoCode,
			int numberOfChildens, int numberOfInfants) {
		super();
		this.flightOption = flightOption;
		this.origin = origin;
		this.departDate = departDate;
		this.destination = destination;
		this.returnDate = returnDate;
		this.currency = currency;
		this.numberOfAdults = numberOfAdults;
		this.promoCode = promoCode;
		this.lowestFare = lowestFare;
		this.numberOfChildens = numberOfChildens;
		this.numberOfInfants = numberOfInfants;
	}

	public static class FlightBuilder {
		private FlightOption flightOption;
		private LocationOption origin;
		private Date departDate;
		private LocationOption destination;
		private Date returnDate;
		private String currency;
		private int numberOfAdults;
		private boolean lowestFare;
		private String promoCode;
		private int numberOfChildens;
		private int numberOfInfants;

		public FlightBuilder setFlightOption(FlightOption flightOption) {
			this.flightOption = flightOption;
			return this;
		}

		public FlightBuilder setOrigin(LocationOption origin) {
			this.origin = origin;
			return this;
		}

		public FlightBuilder setDepartDate(Date departDate) {
			this.departDate = departDate;
			return this;
		}

		public FlightBuilder setDestination(LocationOption destination) {
			this.destination = destination;
			return this;
		}

		public FlightBuilder setReturnDate(Date returnDate) {
			this.returnDate = returnDate;
			return this;
		}

		public FlightBuilder setCurrency(String currency) {
			this.currency = currency;
			return this;
		}

		public FlightBuilder setNumberOfAdults(int numberOfAdults) {
			this.numberOfAdults = numberOfAdults;
			return this;
		}

		public FlightBuilder setLowestFare(boolean lowestFare) {
			this.lowestFare = lowestFare;
			return this;
		}

		public FlightBuilder setPromoCode(String promoCode) {
			this.promoCode = promoCode;
			return this;
		}

		public FlightBuilder setNumberOfChildens(int numberOfChildens) {
			this.numberOfChildens = numberOfChildens;
			return this;
		}

		public FlightBuilder setNumberOfInfants(int numberOfInfants) {
			this.numberOfInfants = numberOfInfants;
			return this;
		}

		public FlightInfo build() {
			FlightInfo flight = new FlightInfo(this.flightOption, this.origin, this.departDate, this.destination,
					this.returnDate, this.currency, this.numberOfAdults, this.lowestFare, this.promoCode,
					this.numberOfChildens, this.numberOfInfants);
			return flight;
		}		
	}

	public enum FlightOption {
		ONEWAY("One Way"), RETURN("Return");
		private String option;

		FlightOption(String option) {
			this.option = option;
		}

		public String getValue() {
			return option;
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
		RGN("RGN", "Yangon");

		private String key;
		private String value;

		LocationOption(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getKey() {
			return key;
		}

	}
}
