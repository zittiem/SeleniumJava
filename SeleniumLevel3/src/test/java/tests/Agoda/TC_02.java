package tests.Agoda;

import org.testng.annotations.Test;

import datatype.Agoda.TravellingInfo;
import pages.Agoda.HomePage;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.constant.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;

public class TC_02 extends TestBase {
	@Test(description = "Verify that user can search and filter hotel successfully .")
	public void TC02() {

		SoftAssertion softAssert = new SoftAssertion();

		Logger.info("Precondition: Initial Data");
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC02");
		HomePage homePage = new HomePage();
		TravellingInfo travel = dataHelper.getDataObject(TravellingInfo.class);
		System.out.println(travel.toString());

		Logger.info("1. Navigate to https://www.agoda.com/.");
		// This step is included in @BeforeMethod

		Logger.info(
				"2. Search the hotel with the following information:\r\n\t-Place: Da Nang\r\n\t-Date: 3 days from next Friday\r\n\t-Number of people: Family Travelers -> 2 rooms and 4 adults");
		homePage.searchHoltel(travel);

		Logger.verify("Search Result is displayed correctly with first 5 hotels(destination).");
		Logger.verify("3. Filter the hotels with the following info:\r\n\t-Price: 500000-1000000VND\r\n\t-Star:3");
		Logger.verify("The flights dates are displayed correctly");
		Logger.verify("The departure and arrival places are correct.");
		Logger.verify("Number of passenger is correct");
		softAssert.assertTrue(false);
		Logger.verify("Passenger Information page is displayed");
		Logger.verify("Tickets information is correct");
		softAssert.assertAll();
	}
}
