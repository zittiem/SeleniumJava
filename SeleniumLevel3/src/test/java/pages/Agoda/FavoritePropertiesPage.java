package pages.Agoda;

import java.util.Date;

import element.base.web.Element;
import helper.LocatorHelper;
import tests.TestBase;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class FavoritePropertiesPage extends TestBase {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			FavoritePropertiesPage.class);
	
	// Static Elements
	protected Element eleCheckInDate = new Element(locator.getLocator("eleCheckInDate"));
	protected Element eleCheckOutDate = new Element(locator.getLocator("eleCheckOutDate"));
	protected Element eleGuestInfo = new Element(locator.getLocator("eleGuestInfo"));
	protected Element eleHotelName = new Element(locator.getLocator("eleHotelName"));
	
	// Verify
	 /**
	    * Return a Boolean value to indicate whether the hotel is added to the saved list successfully
	    *
	    * @return  true|false
	    * 			true: the hotel is added to the saved list successfully
	    * 			false: the hotel is not added to the saved list successfully
	    * 
	    */
		public boolean isHotelInfoCorrect(String checkInDate, String checkOutDate, String guestInfo, String hotelName) {
			eleCheckInDate.waitForDisplayed(120);
			
			System.out.print("eleCheckInDate: " + eleCheckInDate.getValue() + " vs " + checkInDate + " \n");
			System.out.print("eleCheckOutDate: " + eleCheckOutDate.getValue() + " vs " + checkOutDate + " \n");
			System.out.print("eleGuestInfo: " + eleGuestInfo.getAttribute("value") + " vs " + guestInfo + " \n");
			System.out.print("eleHotelName: " + eleHotelName.getText() + " vs " + hotelName + " \n");
	
			return eleCheckInDate.getValue().equals(checkInDate)
				&& eleCheckOutDate.getValue().equals(checkOutDate)
				&& eleGuestInfo.getAttribute("value").equals(guestInfo) 
				&& eleHotelName.getText().equals(hotelName);
		}

}
