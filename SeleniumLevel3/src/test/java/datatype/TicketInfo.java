package datatype;

public class TicketInfo {

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
