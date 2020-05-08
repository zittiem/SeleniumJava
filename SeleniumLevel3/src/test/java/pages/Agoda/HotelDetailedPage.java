package pages.Agoda;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.javatuples.Pair;

import datatype.Agoda.Enums.ReviewCategory;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.setting.FindBy;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.Logger;
import utils.helper.ResourceHelper;

public class HotelDetailedPage extends GeneralPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			HotelDetailedPage.class);
	
	// Static Elements
	protected Element eleHotelName = new Element(locator.getLocator("eleHotelName"));
	protected Element eleHotelAddress = new Element(locator.getLocator("eleHotelAddress"));
	protected Element eleHotelReviewScoreNumber = new Element(locator.getLocator("eleHotelReviewScoreNumber"));
	protected Element eleReviewCardPopup = new Element(locator.getLocator("eleReviewCardPopup"));
	protected Element eleHotelFeatures = new Element(locator.getLocator("eleHotelFeatures"));
	protected Element elesNonSmokingRoomAmenity = new Element(locator.getLocator("elesNonSmokingRoomAmenity"));
	protected Element eleRoom = new Element(locator.getLocator("eleRoom"));
	protected Element eleFavoriteHeart = new Element(locator.getLocator("eleFavoriteHeart"));

	// Dynamic Elements
	protected Element eleHotelFacility = new Element(locator.getLocator("eleHotelFacility"));
	protected Element eleRoomFeature = new Element(locator.getLocator("eleRoomFeature"));
	
	// Special Locators
	protected Pair<FindBy, String> reviewCategoryLocator = locator.getLocator("eleReviewCategory");
	protected Pair<FindBy, String> reviewScoreLocator = locator.getLocator("eleReviewScore");
	
	// Methods
	
    /**
     * Wait for page load completely. Time out is Constants.LONG_TIME.
     */
	public void waitForPageLoad() {
		eleHotelName.waitForDisplayed(Constants.LONG_TIME);
	}
	
	/**
     * Switch to Hotel Detailed page
     */
	public void switchTo() {
		DriverUtils.switchToLatest();
	}

	 /**
     * Open the review score popup by clicking on the hotel header review score
     */
	public void openReviewScorePopup() {
		scrollToTop();
		eleHotelReviewScoreNumber.click();
		eleReviewCardPopup.waitForDisplayed(Constants.SHORT_TIME);
	}
	
	/**
     * Add Hotel into Favorite list
     */
	public void selectFavoriteHeart() {
		scrollToTop();
		eleFavoriteHeart.click();
	}
	
	 /**
     * Get Name of hotel
     *
     * @return  A string of hotel name
     * 
     */
	public String getHotelName() {
		return eleHotelName.getText();
	}
	
	 /**
     * Get destination of hotel
     *
     * @return  A string of hotel destination
     * 
     */
	public String getHotelDestination() {
		return eleHotelAddress.getText();
	}
	
	/**
     * Load room data
     *
     * @return  void
     * 
     */
	public void loadRooms() {
		eleRoom.waitForDisplayed(Constants.SHORT_TIME);
		StopWatch sw = new StopWatch();
		sw.start();
		int size = 0;
		int previousSize = 0;
		List<Element> elements;
		do
		{
			elements = eleRoom.getWrapperElements();
			size = elements.size();
			if (size != previousSize)
			{
				previousSize = size;
				elements.get(size - 1).moveToElement();
			}
			else break;
		}
		while (sw.getTime(TimeUnit.SECONDS) < Constants.SHORT_TIME);
	}
	
	// Verify
	
	 /**
     * Return a Boolean value to indicate whether the specified hotel facilities are correct
     *
     * @param	facilities
     *			List of the specified hotel facilities
     *
     * @return  true|false
     * 			true: all facilities exist 
     * 			false: any facility does not exist
     * 
     */
	public boolean isHotelFacilityCorrect(List<String> facilities) {
		eleHotelFeatures.moveToElement();
		for (String facility : facilities) {
			if (!eleHotelFacility.generateDynamic(facility).isDisplayed())
			{
				if(!elesNonSmokingRoomAmenity.isDisplayed()) {
					Logger.info("Hotel's URL: " + DriverUtils.getURL());
					return false;
				}
			}
		}
		return true;
	}
	
	// Verify
	
	 /**
     * Return a Boolean value to indicate whether the specified room feature are correct
     *
     * @param	facilities
     *			List of the specified hotel facilities
     *
     * @return  true|false
     * 			true: all room feature exist 
     * 			false: any room feature does not exist
     * 
     */
	public boolean isRoomFeatureCorrect(List<String> roomFeatures) {
		loadRooms();
		for (String feature : roomFeatures) {
			if (!eleRoomFeature.generateDynamic(feature).isDisplayed())
			{
				return false;
			}
		}
		return true;
	}
	
	 /**
     * Return a Boolean value to indicate whether the specified hotel review category is correct
     * It should include the following information: Cleanliness, Facilities, Service, Location, Value for money
     *
     * @param	facilities
     *			List of the specified hotel facilities
     *
     * @return  true|false
     * 			true: all hotel review categories exist 
     * 			false: any hotel review category does not exist
     * 
     */
	public boolean isHotelReviewCategoryCorrect() {
		for (ReviewCategory category : ReviewCategory.values()) {
			Element eleReviewCategory = new Element(eleReviewCardPopup, reviewCategoryLocator, category.getValue());
			if (!eleReviewCategory.isDisplayed())
			{
				Logger.info("Hotel's URL: " + DriverUtils.getURL());
				return false;
			}
		}
		return true;
	}
	
	 /**
     * Return a Boolean value to indicate whether the detailed review scores are correct (match with expected review scores)
     *
     * @param	expectedReviewScores
     *			Expected review scores are stored as Map<Review category, Review score>
     *
     * @return  true|false
     * 			true: detailed review scores are correct 
     * 			false: any review category does not exist or the detailed review score does not match with expected score.
     * 
     */
	public boolean isHotelReviewScoreCorrect(Map<String, String> expectedReviewScores) {
		Logger.info("Hotel's URL: " + DriverUtils.getURL());
		for (String key : expectedReviewScores.keySet()) {
			Element eleReviewScore = new Element(eleReviewCardPopup, reviewScoreLocator, key);
			if (!eleReviewScore.isDisplayed() || !eleReviewScore.getText().equals(expectedReviewScores.get(key)))
			{
				return false;
			}
		}
		return true;
	}
}
