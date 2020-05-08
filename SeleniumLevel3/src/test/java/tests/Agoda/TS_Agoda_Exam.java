package tests.Agoda;

import org.testng.annotations.Test;
import datatype.Agoda.UserInfo;
import driver.manager.DriverUtils;
import datatype.Agoda.FilterOptions;
import datatype.Agoda.RoomBooking;
import pages.Agoda.FavoriteGroupsPage;
import pages.Agoda.FavoritePropertiesPage;
import pages.Agoda.HomePage;
import pages.Agoda.HotelDetailedPage;
import pages.Agoda.SearchHotelResultPage;
import pages.Agoda.SignInPopUp;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.constant.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;
import utils.helper.ResourceHelper;

public class TS_Agoda_Exam extends TestBase {
	@Test(description = "Verify that user can search and filter hotel successfully.")
	public void TC01_Exam() {

		SoftAssertion softAssert = new SoftAssertion();
			
		Logger.info("Precondition: Initial Data");
		
		// Pages
		HomePage homePage = new HomePage();
		SearchHotelResultPage searchHotel = new SearchHotelResultPage();
		HotelDetailedPage hotelDetailed = new HotelDetailedPage();
		
		// Test Data
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC01_Exam");
		RoomBooking roomBooking = dataHelper.getDataObject(RoomBooking.class).compileData();
		FilterOptions filter = new FilterOptions(dataHelper.getDataMap(FilterOptions.class.getSimpleName()));
		
		Logger.info("1. Navigate to https://www.agoda.com/.");
		// This step is included in @BeforeMethod
		
		// Select language
		homePage.selectLanguage(ResourceHelper.SHARED_DATA.get().language_name);

		Logger.info(
				"2. Search the hotel with the following information:\r\n" + 
				"-Place: Da Lat\r\n" + 
				"-Date: 3 days from next tomorrow\r\n" + 
				"-Number of people: Family Travelers -> 1 rooms and 2 adults");
		homePage.searchHotel(roomBooking);

		Logger.verify("Search Result is displayed correctly with first 5 hotels(destination).");
		
		searchHotel.waitForPageLoad();
		softAssert.assertTrue(searchHotel.isHotelDestinationCorrect(roomBooking.getDestination(), 5),
				"Search Result is not displayed correctly with first 5 hotels. Please check.");
		
		Logger.info("3. Filter the hotels with breakfast included and select the first hotel");
		searchHotel.filterMore(filter);
		String expectedHotelName = searchHotel.getHotelName(1);
		searchHotel.selectHotel(1);
		DriverUtils.switchToLatest("Hotel Detailed Page");
			
		Logger.verify("The hotel detailed page is displayed with correct info (Name)");
		hotelDetailed.waitForPageLoad();
		softAssert.assertEquals(hotelDetailed.getHotelName(), expectedHotelName,
				"The hotel detailed page is not displayed with correct info (Name). Please check.");
			
		Logger.verify("The hotel detailed page is displayed with correct info (Destination)");
		softAssert.assertTrue(hotelDetailed.getHotelDestination().contains(roomBooking.getDestination()),
				"The hotel detailed page is not displayed with correct info (Destination). Please check.");
			
		Logger.verify("The hotel detailed page is displayed with correct info (Breakfast included)");
		softAssert.assertTrue(hotelDetailed.isBreakfastIncluded(),
				"The hotel detailed page is not displayed with correct info (Breakfast included). Please check.");
		
		softAssert.assertAll();
		
	}
	
	@Test(description = "Verify that user can add hotel into Favorite successfully.")
	public void TC02_Exam() throws InterruptedException {

		SoftAssertion softAssert = new SoftAssertion();
			
		Logger.info("Precondition: Initial Data");
		
		// Pages
		HomePage homePage = new HomePage();
		SearchHotelResultPage searchHotel = new SearchHotelResultPage();
		HotelDetailedPage hotelDetailed = new HotelDetailedPage();
		SignInPopUp signInPopUp = new SignInPopUp();
		FavoriteGroupsPage favoriteGroupsPage = new FavoriteGroupsPage();
		FavoritePropertiesPage favoritePropertiesPage = new FavoritePropertiesPage();
		
		// Test Data
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC02_Exam");
		RoomBooking roomBooking = dataHelper.getDataObject(RoomBooking.class).compileData();
		UserInfo agodaAccount = dataHelper.getDataObject(UserInfo.class);
		FilterOptions filter = new FilterOptions(dataHelper.getDataMap(FilterOptions.class.getSimpleName()));
		String expectedGuesInfo = "2 room, 4 adults, 0 children";
		
		Logger.info("1. Navigate to https://www.agoda.com/.");
		// This step is included in @BeforeMethod
		
		// Select language
		homePage.selectLanguage(ResourceHelper.SHARED_DATA.get().language_name);

		Logger.info(
				"2. Search the hotel with the following information:\r\n" + 
				"-Place: Da Lat\r\n" + 
				"-Date: 3 days from next Friday\r\n" + 
				"-Number of people: Family Travelers -> 2 rooms and 4 adults");
		homePage.searchHotel(roomBooking);

		Logger.verify("Search Result is displayed correctly with first 5 hotels(destination).");
		
		searchHotel.waitForPageLoad();
		softAssert.assertTrue(searchHotel.isHotelDestinationCorrect(roomBooking.getDestination(), 5),
				"Search Result is not displayed correctly with first 5 hotels. Please check.");
		
		Logger.info("3. Filter the hotels with swimming pool and choose the 1st hotel in the list");
		searchHotel.filterMore(filter);
		String expectedHotelName = searchHotel.getHotelName(1);
		searchHotel.selectHotel(1);
		DriverUtils.switchToLatest("Hotel Detailed Page");
		hotelDetailed.waitForPageLoad();
		String expectedCheckInDate = hotelDetailed.getCheckInDate();
		String expectedCheckOutDate = hotelDetailed.getCheckOutDate();

			
		Logger.verify("The hotel detailed page is displayed with correct info (Name)");
		softAssert.assertEquals(hotelDetailed.getHotelName(), expectedHotelName,
				"The hotel detailed page is not displayed with correct info (Name). Please check.");
			
		Logger.verify("The hotel detailed page is displayed with correct info (Destination)");
		softAssert.assertTrue(hotelDetailed.getHotelDestination().contains(roomBooking.getDestination()),
				"The hotel detailed page is not displayed with correct info (Destination). Please check.");
			
		Logger.verify("The hotel detailed page is displayed with correct info (Breakfast included)");
		softAssert.assertTrue(hotelDetailed.isBreakfastIncluded(),
				"The hotel detailed page is not displayed with correct info (Breakfast included). Please check.");
		
		Logger.info("4. Add the hotel into the saved list");
		hotelDetailed.addHotelToSavedList();
		
		Logger.verify("Login popup appears");
		softAssert.assertTrue(signInPopUp.isDisplayed(),
				"The Login popup does not appear. Please check.");

		Logger.info("5. Log in Agoda");
		signInPopUp.signIn(agodaAccount);
		
		Logger.info("6. Open Saved Properties List Page");
		hotelDetailed.selectHeaderDropDownItem("Saved properties list");
		
		Logger.verify("The hotel is added to the saved list successfully\r\n" + 
				"-The booking dates\r\n" + 
				"-The number of guests\r\n" + 
				"-The hotel information");
		favoriteGroupsPage.viewCard(roomBooking.getDestination());
		softAssert.assertTrue(favoritePropertiesPage.isHotelInfoCorrect(expectedCheckInDate, expectedCheckOutDate, expectedGuesInfo, expectedHotelName),
				"Failed Data. Please check.");
		
		softAssert.assertAll();
	}	
	
}
