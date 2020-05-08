package tests.Agoda;

import java.util.List;
import org.testng.annotations.Test;

import datatype.Agoda.Account;
import datatype.Agoda.FilterOptions;
import datatype.Agoda.RoomBooking;
import pages.Agoda.FavoriteGroupsPage;
import pages.Agoda.FavoritePropertiesPage;
import pages.Agoda.HomePage;
import pages.Agoda.HotelDetailedPage;
import pages.Agoda.LoginPage;
import pages.Agoda.SearchHotelResultPage;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.constant.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;
import utils.helper.ResourceHelper;

public class TS_Agoda extends TestBase {
	@Test(description = "Search and filter hotels successfully")
	public void TC01() {

		SoftAssertion softAssert = new SoftAssertion();
			
		Logger.info("Precondition: Initial Data");
		
		// Pages
		HomePage homePage = new HomePage();
		SearchHotelResultPage searchHotelResultPage = new SearchHotelResultPage();
		HotelDetailedPage hotelDetailedPage = new HotelDetailedPage();
		
		// Test Data
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC01");
		RoomBooking roomBooking = dataHelper.getDataObject(RoomBooking.class).compileData();
		FilterOptions filter = new FilterOptions(dataHelper.getDataMap(FilterOptions.class.getSimpleName()));
		List<String> roomFeatures = dataHelper.getDataStringList("RoomFeatures");
		
		Logger.info("1. Navigate to https://www.agoda.com/.");
		// This step is included in @BeforeMethod
		
		// Select language
		homePage.selectLanguage(ResourceHelper.SHARED_DATA.get().language_name);

		Logger.info(
				"2. Search the hotel with the following information:\r\n\t-Place: Da Lat\r\n\t-Date: 3 days from tomorrow\r\n\t-Number of people: Family Travelers -> 1 rooms and 2 adults");
		homePage.searchHotel(roomBooking);

		Logger.verify("Search Result is displayed correctly with first 5 hotels(destination).");
		searchHotelResultPage.waitForPageLoad();
		softAssert.assertTrue(searchHotelResultPage.isHotelDestinationCorrect(roomBooking.getDestination(), 5),
				"Search Result is not displayed correctly with first 5 hotels. Please check.");
		
		Logger.info("3. Filter the hotels with breakfast included and select the first hotel");
		searchHotelResultPage.filterMore(filter);
		String expectedHotelName = searchHotelResultPage.getHotelName(1);
		searchHotelResultPage.selectHotel(1);
		hotelDetailedPage.switchTo();
			
		Logger.verify("The hotel detailed page is displayed with correct info (Name)");
		hotelDetailedPage.waitForPageLoad();
		softAssert.assertEquals(hotelDetailedPage.getHotelName(), expectedHotelName,
				"The hotel detailed page is not displayed with correct info (Name). Please check.");
			
		Logger.verify("The hotel detailed page is displayed with correct info (Destination)");
		softAssert.assertTrue(hotelDetailedPage.getHotelDestination().contains(roomBooking.getDestination()),
				"The hotel detailed page is not displayed with correct info (Destination). Please check.");
			
		Logger.verify("The hotel detailed page is displayed with correct info (Breakfast included)");
		softAssert.assertTrue(hotelDetailedPage.isRoomFeatureCorrect(roomFeatures),
				"The hotel detailed page is not displayed with correct info (Breakfast included). Please check.");
		
		softAssert.assertAll();
	}
	
	@Test(description = "Add hotel into Favourite successfully")
	public void TC02() {

		SoftAssertion softAssert = new SoftAssertion();
			
		Logger.info("Precondition: Initial Data");
		
		// Pages
		HomePage homePage = new HomePage();
		LoginPage loginPage = new LoginPage();
		SearchHotelResultPage searchHotelResultPage = new SearchHotelResultPage();
		HotelDetailedPage hotelDetailedPage = new HotelDetailedPage();
		FavoriteGroupsPage favoriteGroupsPage = new FavoriteGroupsPage();
		FavoritePropertiesPage favoritePropertiesPage = new FavoritePropertiesPage();
		
		// Test Data
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC02");
		Account account = dataHelper.getDataObject(Account.class);
		RoomBooking roomBooking = dataHelper.getDataObject(RoomBooking.class).compileData();
		FilterOptions filter = new FilterOptions(dataHelper.getDataMap(FilterOptions.class.getSimpleName()));
		List<String> hotelFacilities = dataHelper.getDataStringList("HotelFacilities");
		
		Logger.info("1. Navigate to https://www.agoda.com/.");
		// This step is included in @BeforeMethod
		
		// Select language
		homePage.selectLanguage(ResourceHelper.SHARED_DATA.get().language_name);

		Logger.info(
				"2. Search the hotel with the following information:\r\n\t-Place: Da Lat\r\n\t-Date: 3 days from next Friday\r\n\t-Number of people: Family Travelers -> 2 rooms and 4 adults");
		homePage.searchHotel(roomBooking);

		Logger.verify("Search Result is displayed correctly with first 5 hotels(destination).");
		searchHotelResultPage.waitForPageLoad();
		softAssert.assertTrue(searchHotelResultPage.isHotelDestinationCorrect(roomBooking.getDestination(), 5),
				"Search Result is not displayed correctly with first 5 hotels. Please check.");
		
		Logger.info("3. Filter the hotels with swimming pool and choose the 1st hotel in the list");
		searchHotelResultPage.filterMore(filter);
		String expectedHotelName = searchHotelResultPage.getHotelName(1);
		searchHotelResultPage.selectHotel(1);
		hotelDetailedPage.switchTo();
			
		Logger.verify("The hotel detailed page is displayed with correct info (Name)");
		hotelDetailedPage.waitForPageLoad();
		softAssert.assertEquals(hotelDetailedPage.getHotelName(), expectedHotelName,
				"The hotel detailed page is not displayed with correct info (Name). Please check.");
			
		Logger.verify("The hotel detailed page is displayed with correct info (Destination)");
		softAssert.assertTrue(hotelDetailedPage.getHotelDestination().contains(roomBooking.getDestination()),
				"The hotel detailed page is not displayed with correct info (Destination). Please check.");
			
		Logger.verify("The hotel detailed page is displayed with correct info (Have swimming pool)");
		softAssert.assertTrue(hotelDetailedPage.isHotelFacilityCorrect(hotelFacilities),
				"The hotel detailed page is not displayed with correct info (Have swimming pool). Please check.");
		
		Logger.info("4. Add the hotel into the saved list");
		hotelDetailedPage.selectFavoriteHeart();
			
		Logger.verify("Login popup appears");
		loginPage.waitForPageLoad();
		softAssert.assertTrue(loginPage.isPageDisplayed(),
				"The Login popup didn't appears. Please check.");
		
		Logger.info("5. Log in Agoda");
		loginPage.signIn(account);
		
		Logger.info("6. Open Saved Properties List Page");
		hotelDetailedPage.openFavoriteMenu();
		favoriteGroupsPage.waitForPageLoad();
		favoriteGroupsPage.selectFavoriteGroupCard(roomBooking.getDestination());
			
		Logger.verify("The hotel is added to the saved list successfully (The booking dates)");
		favoritePropertiesPage.waitForPageLoad();
		softAssert.assertTrue(favoritePropertiesPage.areBookingDatesCorrect(roomBooking.getCheckInDateObj(), roomBooking.getCheckOutDateObj()),
				"The added hotel is not displayed with correct info (The booking dates). Please check.");
			
		Logger.verify("The hotel is added to the saved list successfully (The number of guests)");
		softAssert.assertTrue(favoritePropertiesPage.areNumberOfGuestsCorrect(roomBooking.getNumberOfAdult(), roomBooking.getNumberOfChildren(), roomBooking.getNumberOfRoom()),
				"The added hotel is not displayed with correct info (The number of guests). Please check.");
		
		Logger.verify("The hotel is added to the saved list successfully (The hotel information)");
		softAssert.assertTrue(favoritePropertiesPage.isHotelInformationCorrect(expectedHotelName),
				"The added hotel is not displayed with correct info (The hotel information). Please check.");
		
		try
		{
			// clean up
			favoritePropertiesPage.removeFavoriteHeart(expectedHotelName);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		softAssert.assertAll();
	}
}
