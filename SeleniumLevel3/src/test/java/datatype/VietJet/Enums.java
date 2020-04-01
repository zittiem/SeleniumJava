package datatype.VietJet;

public class Enums {

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
	
	public enum LanguageType {
		US("US", "English"), VI("VI", "Tiếng Việt");

		private String code;
		private String text;

		private LanguageType() {
			this.code = "US";
			this.text = "English";
		}

		private LanguageType(String code, String text) {
			this.code = code;
			this.text = text;
		}

		public static LanguageType getLanguageByCode(String code) {
			for (LanguageType lang : LanguageType.values()) {
				if (lang.code.equals(code)) {
					return lang;
				}
			}
			return null;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}
	
	public enum Month {
		Jan(0, "Tháng Một"), Feb(1, "Tháng Hai"), Mar(2, "Tháng Ba"), Apr(3, "Tháng Tư"), May(4, "Tháng Năm"), 
		Jun(5, "Tháng Sáu"), Jul(6, "Tháng Bảy"), Aug(7, "Tháng Tám"), Sep(8, "Tháng Chín"), Oct(9, "Tháng Mười"), 
		Nov(10, "Tháng Mười Một"), Dec(11, "Tháng Mười Hai");
		
		private int key;
		private String value;

		Month(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public int getKey() {
			return this.key;
		}
		
		public static int getKey(String value) {
			for (Month e : Month.values()) {
				if (value.contains(e.value))
					return e.key;
			}
			return 100;
		}
	}
	
	public enum FlightType {
		Dep("Dep", "Depart"), Ret("Ret", "Return");

		private String key;
		private String value;

		FlightType(String key, String value) {
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
}
