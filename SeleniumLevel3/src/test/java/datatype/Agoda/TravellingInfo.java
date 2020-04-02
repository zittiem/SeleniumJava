package datatype.Agoda;

import datatype.VietJet.Booking;
import utils.helper.DateTimeHelper;
import utils.helper.ResourceHelper;

public class TravellingInfo {
	private String destination = null;
	private String checkInDate = null;
	private String checkOutDate = null;
	private String travelOption = null;
	private int numberOfRoom = 0;
	private int numberOfAdult = 0;
	private int numberOfChildren = 0;
	private String childrenAge = null;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getTravelOption() {
		return travelOption;
	}

	public void setTravelOption(String travelOption) {
		this.travelOption = travelOption;
	}

	public int getNumberOfRoom() {
		return numberOfRoom;
	}

	public void setNumberOfRoom(int numberOfRoom) {
		this.numberOfRoom = numberOfRoom;
	}

	public int getNumberOfAdult() {
		return numberOfAdult;
	}

	public void setNumberOfAdult(int numberOfAdult) {
		this.numberOfAdult = numberOfAdult;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public String getChildrenAge() {
		return childrenAge;
	}

	public void setChildrenAge(String childrenAge) {
		this.childrenAge = childrenAge;
	}

	// Update all the values if need to calculate after providing data.
	public TravellingInfo compileData() {
		String _date = null;
		if (this.checkInDate.contains(":")) {
			_date = DateTimeHelper.getDateString(DateTimeHelper.mapDate(this.checkInDate), "dd/MM/yyyy");
			setCheckInDate(_date);
			setCheckInDate(getCheckInDate());
		}
		if (this.checkOutDate.contains(":")) {
			_date = DateTimeHelper.getDateString(DateTimeHelper.mapDate(this.checkOutDate), "dd/MM/yyyy");
			setCheckOutDate(_date);
			setCheckOutDate(getCheckOutDate());
		}

		return this;
	}

	@Override
	public String toString() {
		return "TravellingInfo [destination=" + destination + ", checkInDate=" + checkInDate + ", checkOutDate="
				+ checkOutDate + ", travelOption=" + travelOption + ", numberOfRoom=" + numberOfRoom
				+ ", numberOfAdult=" + numberOfAdult + ", numberOfChildren=" + numberOfChildren + ", childrenAge="
				+ childrenAge + "]";
	}

}
