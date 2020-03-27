package datatype.VietJet;

import java.util.Date;
import utils.helper.DateTimeHelper;

public class BookingInfo {

	private String flightOption = "Return";
	private String originKey = null;
	private String originValue = null;
	private String departDate = DateTimeHelper.getDateString(new Date(), "dd/MM/yyyy");
	private String destinationKey = null;
	private String destinationValue = null;
	private String returnDate = DateTimeHelper.getDateString(DateTimeHelper.plusDays(1), "dd/MM/yyyy");
	private String currency = "VND";
	private int numberOfAdults = 0;
	private boolean lowestFare = false;
	private String promoCode = null;
	private int numberOfChildren = 0;
	private int numberOfInfants = 0;

	public String getFlightOption() {
		return flightOption;
	}

	public void setFlightOption(String flightOption) {
		this.flightOption = flightOption;
	}

	public String getOriginKey() {
		return originKey;
	}

	public void setOriginKey(String originKey) {
		this.originKey = originKey;
	}

	public String getOriginValue() {
		return originValue;
	}

	public void setOriginValue(String originValue) {
		this.originValue = originValue;
	}

	public String getDepartDate() {
		return departDate;
	}

	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}

	public String getDestinationKey() {
		return destinationKey;
	}

	public void setDestinationKey(String destinationKey) {
		this.destinationKey = destinationKey;
	}

	public String getDestinationValue() {
		return destinationValue;
	}

	public void setDestinationValue(String destinationValue) {
		this.destinationValue = destinationValue;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
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

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChilden(int numberOfChilden) {
		this.numberOfChildren = numberOfChilden;
	}

	public int getNumberOfInfants() {
		return numberOfInfants;
	}

	public void setNumberOfInfants(int numberOfInfants) {
		this.numberOfInfants = numberOfInfants;
	}
	
	public void setDepartDate(Date depDate) {
		this.departDate = DateTimeHelper.getDateString(depDate, "dd/MM/yyyy");
	}
	
	public void setReturnDate(Date retDate) {
		this.returnDate = DateTimeHelper.getDateString(retDate, "dd/MM/yyyy");
	}

	public void showInfo() {
		System.out.println("===================Flight Information===================" + "\nFlight Option: "
				+ this.flightOption + "\nOrigin: " + this.originValue + "\nDepart Date: " + this.departDate
				+ "\nDestination :" + this.destinationValue + "\nReturn Date: " + this.returnDate + "\nCurrency: "
				+ this.currency + "\nNumber Of Adults: " + this.numberOfAdults + "\nSelect lowest fare: "
				+ this.lowestFare + "\nPromo Code: " + this.promoCode + "\nNumber of Childrens: " + this.numberOfChildren
				+ "\nNumber of Infants: " + this.numberOfInfants
				+ "\n=========================================================");
	}

	public BookingInfo compileData() {
		if (this.departDate.contains("Days")) {
			setDepartDate(DateTimeHelper.getDateString(DateTimeHelper.mapDate(this.departDate),"dd/MM/yyyy"));
		}
		if (this.returnDate.contains("Days")) {
			setReturnDate(DateTimeHelper.getDateString(DateTimeHelper.mapDate(this.returnDate),"dd/MM/yyyy"));
		}
		return this;
	}

	public enum FlightOption {
		ONEWAY("One Way"), RETURN("Return");
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
		RGN("RGN", "Yangon");

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
