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

		public static int getMonthFromSortMonth(String sortMonth) {
			for (Month e : Month.values()) {
				if (e.sortMonth.equals(sortMonth))
					return e.intMont;
			}
			return 0;
		}

		public static int getMonthFromFullMonth(String fullMonth) {
			for (Month e : Month.values()) {
				if (e.sortMonth.equals(fullMonth))
					return e.intMont;
			}
			return 0;
		}
	}
	public enum TravelTypes {
		SOLO("solo"), COUPLE("couples"), FAMILY("families"), GROUP("group"), BUSINESS("business");

		private String value;

		TravelTypes(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
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
}
