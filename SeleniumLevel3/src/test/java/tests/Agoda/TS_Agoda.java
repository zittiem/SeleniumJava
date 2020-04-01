package tests.Agoda;

import org.testng.annotations.Test;

import datatype.Agoda.TravellingInfo;
import pages.Agoda.GeneralPage;
import pages.Agoda.HomePage;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.constant.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;
import utils.helper.ResourceHelper;

public class TS_Agoda extends TestBase {
	@Test(description = "Verify that user can search and sort hotel successfully.")
	public void TC01() {

		SoftAssertion softAssert = new SoftAssertion();

		System.out.println(Constants.LOCATOR_FOLDER_PATH);
		System.out.println(ResourceHelper.SHARED_DATA.get().appName);
			
		Logger.info("Precondition: Initial Data");
		HomePage homePage = new HomePage();
		
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC01");
		TravellingInfo travel = dataHelper.getDataObject(TravellingInfo.class);

		Logger.info("1. Navigate to https://www.agoda.com/.");
		// This step is included in @BeforeMethod

		Logger.info(
				"2. Search the hotel with the following information:\r\n\t-Place: Da Nang\r\n\t-Date: 3 days from next Friday\r\n\t-Number of people: Family Travelers -> 2 rooms and 4 adults");
		homePage.searchHoltel(travel);

		Logger.verify("Search Result is displayed correctly with first 5 hotels(destination).");
		Logger.info("3. Sort hotels by lowest prices");
		Logger.verify("5 first hotels are sorted with the right order. ");
		Logger.verify("The hotel destination is still correct.");

		softAssert.assertAll();
	}
}
