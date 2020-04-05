package pages.Agoda;

import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

import datatype.Agoda.Enums.ReviewCategory;
import element.base.web.Element;
import element.setting.FindBy;
import helper.LocatorHelper;
import utils.constant.Constants;
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

	// Dynamic Elements
	protected Element eleHotelFacility = new Element(locator.getLocator("eleHotelFacility"));
	
	// Special Locators
	protected Pair<FindBy, String> reviewCategoryLocator = locator.getLocator("eleReviewCategory");
	protected Pair<FindBy, String> reviewScoreLocator = locator.getLocator("eleReviewScore");
	
	// Methods
	public void waitForPageLoad() {
		eleHotelName.waitForDisplayed(Constants.LONG_TIME);
	}

	public void openReviewScorePopup() {
		scrollToTop();
		eleHotelReviewScoreNumber.click();
		eleReviewCardPopup.waitForDisplayed(Constants.SHORT_TIME);
	}
	
	public String getHotelName() {
		return eleHotelName.getText();
	}
	
	public String getHotelDestination() {
		return eleHotelAddress.getText();
	}
	
	// Verify
	
	public boolean isHotelFacilityCorrect(List<String> facilities) {
		eleHotelFeatures.moveToElement();
		for (String facility : facilities) {
			if (!eleHotelFacility.generateDynamic(facility).isDisplayed())
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isHotelReviewCategoryCorrect() {
		for (ReviewCategory category : ReviewCategory.values()) {
			Element eleReviewCategory = new Element(eleReviewCardPopup, reviewCategoryLocator, category.getValue());
			if (!eleReviewCategory.isDisplayed())
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isHotelReviewScoreCorrect(Map<String, String> reviewScores) {
		for (String key : reviewScores.keySet()) {
			Element eleReviewScore = new Element(eleReviewCardPopup, reviewScoreLocator, key);
			if (!eleReviewScore.isDisplayed() || !eleReviewScore.getText().equals(reviewScores.get(key)))
			{
				return false;
			}
		}
		return true;
	}
}
