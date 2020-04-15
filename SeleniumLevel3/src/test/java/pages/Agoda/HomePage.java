package pages.Agoda;

import datatype.Agoda.RoomBooking;
import element.wrapper.web.Button;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class HomePage extends GeneralPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			HomePage.class);
	
	// Static Elements
	protected Button btnSearch = new Button(locator.getLocator("btnSearch"));
	
	// Method
	
    /**
     * Search a hotel
     *
     * @param  travel
     *         travel info (TravellingInfo)
     *
     */
	public void searchHotel(RoomBooking travel) {
		enterTravelingInfo(travel);
		btnSearch.waitForDisplayed(60);
		btnSearch.click();
	}
}
