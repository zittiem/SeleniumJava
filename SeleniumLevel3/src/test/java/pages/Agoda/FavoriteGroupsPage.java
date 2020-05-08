package pages.Agoda;

import element.base.web.Element;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class FavoriteGroupsPage extends GeneralPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			FavoriteGroupsPage.class);
	
	// Static Elements
	protected Element eleFavoriteGroupMain = new Element(locator.getLocator("eleFavoriteGroupMain"));
	
	// Dynamic Elements
	protected Element eleFavoriteGroupCard = new Element(locator.getLocator("eleFavoriteGroupCard"));
	
	// Method
	/**
     * Wait for page load completely. Time out is Constants.LONG_TIME.
     */
	public void waitForPageLoad() {
		eleFavoriteGroupMain.waitForDisplayed(Constants.LONG_TIME);
	}
	
    /**
     * Sign in to web page
     *
     * @param  account
     *         user login info (Account)
     *
     */
	public void selectFavoriteGroupCard(String groupName) {
		eleFavoriteGroupCard.generateDynamic(groupName).waitForDisplayed(Constants.SHORT_TIME);
		eleFavoriteGroupCard.clickByJS();
	}
}
