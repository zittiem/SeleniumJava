package datatype.VietJet;

import java.util.Date;

import utils.constant.Constants;
import utils.helper.DateTimeHelper;

public class FareItem {
	String itemID = null;
	Date itemDate = null;
	int itemFare = 0;

	public FareItem() {

	}
	
	public FareItem(String itemID, Date itemDate, int itemFare) {
		this.itemID = itemID;
		this.itemDate = itemDate;
		this.itemFare = itemFare;
	}
	
	public FareItem(String itemID, int itemFare) {
		this.itemID = itemID;
		this.itemDate = DateTimeHelper.getDate(itemID.substring(17, 25), Constants.SIMPLE_DATE_FORMAT);
		this.itemFare = itemFare;
	}
	
	public String getID() {
		return this.itemID;
	}
	
	public Date getDate() {
		return this.itemDate;
	}
	
	public int getFare() {
		return this.itemFare;
	}
}
