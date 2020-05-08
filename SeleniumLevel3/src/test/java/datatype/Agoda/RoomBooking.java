package datatype.Agoda;

import java.util.Date;

import utils.helper.DateTimeHelper;

public class RoomBooking {
	private String searchKeyword = null;
	private String destination = null;
	private String checkInDate = null;
	private Date checkInDateObj = null;
	private String checkOutDate = null;
	private Date checkOutDateObj = null;
	private String travelOption = null;
	private int numberOfRoom = 0;
	private int numberOfAdult = 0;
	private int numberOfChildren = 0;
	private String childrenAge = null;

	
	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	
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
	public RoomBooking compileData() {
		String _date = null;
		if (this.checkInDate.contains(":")) {
			_date = DateTimeHelper.getDateString(DateTimeHelper.mapDate(this.checkInDate), "dd/MM/yyyy");
			setCheckInDate(_date);
		}
		if (this.checkOutDate.contains(":")) {
			_date = DateTimeHelper.getDateString(DateTimeHelper.mapDate(this.checkOutDate), "dd/MM/yyyy");
			setCheckOutDate(_date);
		}
		
		setCheckInDateObj(DateTimeHelper.getDate(getCheckInDate(), "dd/MM/yyyy"));
		setCheckOutDateObj(DateTimeHelper.getDate(getCheckOutDate(), "dd/MM/yyyy"));

		return this;
	}

	public Date getCheckInDateObj() {
		return checkInDateObj;
	}

	public void setCheckInDateObj(Date checkInDateObj) {
		this.checkInDateObj = checkInDateObj;
	}

	public Date getCheckOutDateObj() {
		return checkOutDateObj;
	}

	public void setCheckOutDateObj(Date checkOutDateObj) {
		this.checkOutDateObj = checkOutDateObj;
	}

	@Override
	public String toString() {
		return "TravellingInfo [destination=" + destination + ", checkInDate=" + checkInDate + ", checkOutDate="
				+ checkOutDate + ", travelOption=" + travelOption + ", numberOfRoom=" + numberOfRoom
				+ ", numberOfAdult=" + numberOfAdult + ", numberOfChildren=" + numberOfChildren + ", childrenAge="
				+ childrenAge + "]";
	}

}
