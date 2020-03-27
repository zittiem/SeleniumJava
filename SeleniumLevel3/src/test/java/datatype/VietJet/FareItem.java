package datatype.VietJet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FareItem {
	String itemID = null;
	Date itemDate = null;
	Integer itemFare = null;

	public FareItem() {

	}
	
	public FareItem(String itemID, Date itemDate, int itemFare) {
		this.itemID = itemID;
		this.itemDate = itemDate;
		this.itemFare = itemFare;
	}
	
	public FareItem(String itemID, int itemFare) {
		Date itemDate = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
		String strDate = itemID.substring(17, 25);
		
		try {
			itemDate = formatter.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.itemID = itemID;
		this.itemDate = itemDate;
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
