package pages.Agoda;

import java.util.List;
import datatype.Agoda.Enums.Month;
import datatype.Agoda.Enums.TravelFields;
import datatype.Agoda.Enums.TravelTypes;
import datatype.Agoda.RoomBooking;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.wrapper.web.Button;
import element.wrapper.web.DropDown;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import tests.TestBase;
import utils.constant.Constants;
import utils.helper.ResourceHelper;
import utils.helper.DateTimeHelper;

public class FavoriteGroupsPage extends TestBase {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			FavoriteGroupsPage.class);
	
	// Static Elements

	// Dynamic Elements
	protected Element eleCard = new Element(locator.getLocator("eleCard"));
	
	// Methods
    /**
     * View a card with input place
     *
     * @param  place
     *         Place
     *
     */
	public void viewCard(String place) {
		eleCard.generateDynamic(place).waitForDisplayed(60);
		eleCard.generateDynamic(place).click();
	}

}
