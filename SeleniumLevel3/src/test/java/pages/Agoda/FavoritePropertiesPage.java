package pages.Agoda;

import java.util.Date;

import element.base.web.Element;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.DateTimeHelper;
import utils.helper.ResourceHelper;

public class FavoritePropertiesPage extends GeneralPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			FavoritePropertiesPage.class);
	
	// Static Elements
	protected Element eleCheckInBox = new Element(locator.getLocator("eleCheckInBox"));
	protected Element eleCheckOutBox = new Element(locator.getLocator("eleCheckOutBox"));
	protected Element eleAdult = new Element(locator.getLocator("eleAdult"));
	protected Element eleChildren = new Element(locator.getLocator("eleChildren"));
	protected Element eleRoom = new Element(locator.getLocator("eleRoom"));
		
	// Dynamic Elements
	protected Element eleHotelFavoriteHeart = new Element(locator.getLocator("eleHotelFavoriteHeart"));
	protected Element eleHotelName = new Element(locator.getLocator("eleHotelName"));
	
	// Method
	/**
     * Wait for page load completely. Time out is Constants.LONG_TIME.
     */
	public void waitForPageLoad() {
		eleCheckInBox.waitForDisplayed(Constants.LONG_TIME);
	}
	
    /**
     * Remove favorite heart
     *
     * @param  hotelName
     *         hotel name (String)
     *
     */
	public void removeFavoriteHeart(String hotelName) {
		eleHotelFavoriteHeart.generateDynamic(hotelName).click();
	}
	
	// Verify
	
	 /**
     * Return a Boolean value to indicate whether the booking dates are correct
     *
     * @param	checkIn
     *			CheckIn date
     *
     *			checkOut
     *			CheckOut date
     *
     * @return  true|false
     * 			true: all booking date correct 
     * 			false: any booking date incorrect
     * 
     */
	public boolean areBookingDatesCorrect(Date checkIn, Date checkOut) {
		return checkIn.equals(DateTimeHelper.getDate(eleCheckInBox.getValue(), ResourceHelper.SHARED_DATA.get().data_date_format))
			&& checkOut.equals(DateTimeHelper.getDate(eleCheckOutBox.getValue(), ResourceHelper.SHARED_DATA.get().data_date_format));
	}
	
	/**
     * Return a Boolean value to indicate whether the booking dates are correct
     *
     * @param	adult, children, room (int)
     *
     * @return  true|false
     * 			true: all numbers correct 
     * 			false: any number incorrect
     * 
     */
	public boolean areNumberOfGuestsCorrect(int adult, int children, int room) {
		System.out.print("Adult: " + String.format(ResourceHelper.getResource("adult_format"), adult) + " vs " + eleAdult.getText() + " \n");
		System.out.print("Children: " + String.format(ResourceHelper.getResource("children_format"), children) + " vs " + eleChildren.getText() + " \n");
		System.out.print("Room: " + String.format(ResourceHelper.getResource("room_format"), room) + " vs " + eleRoom.getText() + " \n");
		return String.format(ResourceHelper.getResource("adult_format"), adult).contentEquals(eleAdult.getText())
			&& String.format(ResourceHelper.getResource("children_format"), children).contentEquals(eleChildren.getText())
			&& String.format(ResourceHelper.getResource("room_format"), room).contentEquals(eleRoom.getText());
	}
	
	/**
     * Return a Boolean value to indicate whether the hotel information is correct
     *
     * @param	hotelName (String)
     *
     * @return  true|false
     * 			true: hotel name correct 
     * 			false: hotel name incorrect
     * 
     */
	public boolean isHotelInformationCorrect(String hotelName) {
		return eleHotelName.generateDynamic(hotelName).isDisplayed();
	}
}
