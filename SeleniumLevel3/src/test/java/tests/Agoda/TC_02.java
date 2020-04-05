package tests.Agoda;

import org.testng.annotations.Test;
import datatype.Agoda.Enums.Filter;
import datatype.Agoda.TravellingInfo;
import pages.Agoda.HomePage;
import pages.Agoda.SearchHoltelResultPage;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.constant.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;

public class TC_02 extends TestBase {
	@Test(description = "Verify that user can search and filter hotel successfully .")
	public void TC02() throws InterruptedException {
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC02");
		SoftAssertion softAssert = new SoftAssertion();

		Logger.info("Precondition: Initial Data");
		TravellingInfo travel = dataHelper.getDataObject(TravellingInfo.class).compileData();
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

		Logger.info("3. Filter the hotels with the following info:\r\n\t-Price: 500000-1000000VND\r\n\t-Star:3");
		searchHotel.filterPrice(500000, 1000000);
		searchHotel.filterStarRating(3);

		Logger.verify("The price and start filtered ishighlighted");
		softAssert.assertTrue(searchHotel.isFiltersHighted(Filter.Price, Filter.Rating),
				"The price or star filtered not highlighted. Please check.");

		Logger.verify("Search Result is displayed correctly with first 5 hotels (destination, price, star).");
		softAssert.assertTrue(searchHotel.isHotelDestinationCorrect(travel.getDestination(), 5), 
				"Search Result is displayed correctly with first 5 hotels (destination).");
		softAssert.assertTrue(searchHotel.isHotelPriceCorrect(500000, 1000000, 5), 
				"Search Result is displayed correctly with first 5 hotels (price).");
		softAssert.assertTrue(searchHotel.isHotelStarRatingCorrect(3, 5), 
				"Search Result is displayed correctly with first 5 hotels (star).");

		Logger.info("4. Remove price filter");
		searchHotel.deleteFilter(Filter.Price);

		Logger.verify("The price slice is reset");
		softAssert.assertTrue(searchHotel.isPriceSliderReset(), "The price slice is not reset. Please check");

		softAssert.assertAll();
	}
}
