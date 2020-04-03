package datatype.Agoda;

public class Enums {
	public enum Month {
		JAN(1, "Jan", "January"), FEB(2, "Feb", "February"), MAR(3, "Mar", "March"), APR(4, "Apr", "April"),
		MAY(5, "May", "May"), JUN(6, "Jun", "June"), JUL(7, "Jul", "July"), AUG(8, "Aug", "August"),
		SEP(9, "Sep", "September"), OCT(10, "Oct", "October"), NOV(11, "Nov", "November"), DEC(12, "Dec", "December");

		private int intMont;
		private String sortMonth;
		private String fullMonth;

		Month(int intMont, String sortMonth, String fullMonth) {
			this.intMont = intMont;
			this.sortMonth = sortMonth;
			this.fullMonth = fullMonth;
		}

		public int getMonthNumber() {
			return this.intMont;
		}

		public String getSortMonth() {
			return this.sortMonth;
		}

		public String getFullMonth() {
			return this.fullMonth;
		}

		public static String getSortMonth(int month) {
			for (Month e : Month.values()) {
				if (e.intMont == month)
					return e.sortMonth;
			}
			return null;
		}

		public static String getFullMonth(int month) {
			for (Month e : Month.values()) {
				if (e.intMont == month)
					return e.fullMonth;
			}
			return null;
		}

		public static int getMonth(String value) {
			for (Month e : Month.values()) {
				if (e.sortMonth.equals(value) || e.fullMonth.equals(value))
					return e.intMont;
			}
			return 0;
		}
	}

	public enum WeekDays {
		MO("Mo", "Mon", "Monday"), TU("Tu", "Tue", "Tuesday"), WE("We", "Wed", "Wednesday"),
		TH("Th", "Thu", "Thursday"), FR("Fr", "Fri", "Friday"), SA("Sa", "Sat", "Saturday"), SU("Su", "Sun", "Sunday");

		private String key = null;
		private String sortName = null;
		private String fullName = null;

		WeekDays(String key) {
			this.key = key;
		}

		WeekDays(String key, String sortName, String fullName) {
			this.key = key;
			this.sortName = sortName;
			this.fullName = fullName;
		}

		public String getKey() {
			return key;
		}

		public String getSortName() {
			return sortName;
		}

		public String getFullName() {
			return fullName;
		}

		public String getKey(String value) {
			for (WeekDays e : WeekDays.values()) {
				if (e.sortName.equals(value) || e.fullName.equals(value))
					return e.key;
			}
			return null;
		}

		public String getSortName(String value) {
			for (WeekDays e : WeekDays.values()) {
				if (e.key.equals(value) || e.fullName.equals(value))
					return e.sortName;
			}
			return null;
		}

		public String getFullName(String value) {
			for (WeekDays e : WeekDays.values()) {
				if (e.sortName.equals(value) || e.key.equals(value))
					return e.fullName;
			}
			return null;
		}

	}

	public enum TravelTypes {
		SOLO("Solo traveler", "solo"), COUPLE("Couple/Pair", "couples"), FAMILY("Family travelers", "families"),
		GROUP("Group travelers", "group"), BUSINESS("Business travelers", "business");

		private String value;
		private String code;

		TravelTypes(String value, String code) {
			this.value = value;
			this.code = code;
		}

		public String getValue() {
			return this.value;
		}

		public String getCode() {
			return this.code;
		}

		public static TravelTypes getName(String value) {
			for (TravelTypes travel : TravelTypes.values()) {

				if (travel.getValue().contentEquals(value) || travel.getCode().equals(value)) {
					return travel;
				}
			}
			return null;
		}
	}

	public enum TravelFields {
		ROOM("room"), ADULT("couples"), CHILDREN("families");

		private String value;

		TravelFields(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum Filter {
		Popular("Popular"), Price("PriceFilterRange"), Rating("StarRating"), Locaton("LocationFilters"), More("more");

		Filter(String value) {
			this.value = value;
		}

		private String value;

		public String getValue() {
			return value;
		}

	}
	
	public enum SortOption {
		BestMatch("search-sort-recommended", "Best match"), CheapestPrice("search-sort-price", "Cheapest price"), 
		NearestTo("search-sort-distance-landmark", "Nearest to"), TopReviewed("search-sort-guest-rating", "Top Reviewed"), 
		SecretDeals("search-sort-secret-deals", "Secret deals");

		private String code;
		private String value;
		
		SortOption(String code, String value) {
			this.code= code;
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		public String getCode() {
			return code;
		}

	}
}
