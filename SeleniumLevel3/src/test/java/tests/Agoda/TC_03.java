package tests.Agoda;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;
import datatype.Agoda.FilterOptions;
import datatype.Agoda.TravellingInfo;
import driver.manager.DriverUtils;
import pages.Agoda.HomePage;
import pages.Agoda.HotelDetailedPage;
import pages.Agoda.SearchHoltelResultPage;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.constant.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;

public class TC_03 extends TestBase {
	@Test(description = "Verify that user can add hotel into Favourite successfully .")
	public void TC03() throws InterruptedException {
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC03");
		SoftAssertion softAssert = new SoftAssertion();

		Logger.info("Precondition: Initial Data");
		TravellingInfo travel = dataHelper.getDataObject(TravellingInfo.class).compileData();
		FilterOptions filter = new FilterOptions(dataHelper.getDataMap(FilterOptions.class.getSimpleName()));
		List<String> hotelFacilities1 = dataHelper.getDataStringList("HotelFacilities1");
		List<String> hotelFacilities2 = dataHelper.getDataStringList("HotelFacilities2");
		
		HomePage homePage = new HomePage();
		Logger.info("1. Navigate to https://www.agoda.com/.");
		// This step is included in @BeforeMethod

		Logger.info("2. Search the hotel with the following information:" + "\r\n\t-Place: Da Nang"
				+ "\r\n\t-Date: 3 days from next Friday"
				+ "\r\n\t-Number of people: Family Travelers -> 2 rooms and 4 adults");
		homePage.searchHoltel(travel);

		Logger.verify("Search Result is displayed correctly with first 5 hotels (destination).");
		SearchHoltelResultPage searchHotel = new SearchHoltelResultPage();
		searchHotel.waitForPageLoad();
		softAssert.assertTrue(searchHotel.isHotelDestinationCorrect(travel.getDestination(), 5),
				"Search Result is not displayed correctly with first 5 hotels (destination). Please check.");

		Logger.info("3. Filter the non smoking hotels and choose the 5th hotel in the list");
		searchHotel.filterMore(filter);
		String expectedHotelName = searchHotel.getHotelName(5);
		searchHotel.selectHotel(5);
		DriverUtils.switchToLatest();
		
		Logger.verify("The hotel detailed page is displayed with correct info (Name)");
		HotelDetailedPage hotelDetailed = new HotelDetailedPage();
		hotelDetailed.waitForPageLoad();
		softAssert.assertEquals(hotelDetailed.getHotelName(), expectedHotelName,
				"The hotel detailed page is not displayed with correct info (Name). Please check.");
		
		Logger.verify("The hotel detailed page is displayed with correct info (Destination)");
		softAssert.assertTrue(hotelDetailed.getHotelDestination().contains(travel.getDestination()),
				"The hotel detailed page is not displayed with correct info (Destination). Please check.");
		
		Logger.verify("The hotel detailed page is displayed with correct info (Have swimming pool)");
		softAssert.assertTrue(hotelDetailed.isHotelFacilityCorrect(hotelFacilities1),
				"The hotel detailed page is not displayed with correct info (Have swimming pool). Please check.");
		
		Logger.info("4. Move mouse to point of the hotel to show detailed review points");
		hotelDetailed.openReviewScorePopup();
		Logger.verify("Detailed review popup appears and show the following information: Cleanliness, Facilities, Service, Location, Value for money");
		softAssert.assertTrue(hotelDetailed.isHotelReviewCategoryCorrect(),
				"Detailed review popup doesn't show the following information: Cleanliness, Facilities, Service, Location, Value for money. Please check.");
		DriverUtils.close();
		
		Logger.info("5. Choose the first hotel");
		DriverUtils.switchToFirst();
		expectedHotelName = searchHotel.getHotelName(1);
		Map<String, String> expectedreviewScore = searchHotel.getHotelScores(1);
		searchHotel.selectHotel(1);
		DriverUtils.switchToLatest();
		
		Logger.verify("The hotel detailed page is displayed with correct info (Name)");
		hotelDetailed.waitForPageLoad();
		softAssert.assertEquals(hotelDetailed.getHotelName(), expectedHotelName,
				"The hotel detailed page is not displayed with correct info (Name). Please check.");
		
		Logger.verify("The hotel detailed page is displayed with correct info (Destination)");
		softAssert.assertTrue(hotelDetailed.getHotelDestination().contains(travel.getDestination()),
				"The hotel detailed page is not displayed with correct info (Destination). Please check.");
		
		Logger.verify("The hotel detailed page is displayed with correct info (Non smoking hotel)");
		softAssert.assertTrue(hotelDetailed.isHotelFacilityCorrect(hotelFacilities2),
				"The hotel detailed page is not displayed with correct info (Non smoking hotel). Please check.");
		
		Logger.verify("The hotel detailed page is displayed with correct info (Detailed review points)");
		hotelDetailed.openReviewScorePopup();
		softAssert.assertTrue(hotelDetailed.isHotelReviewScoreCorrect(expectedreviewScore),
				"The hotel detailed page is not displayed with correct info (Detailed review points). Please check.");

		softAssert.assertAll();
	}
}
