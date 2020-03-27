package tests.VietJet;

import org.testng.annotations.Test;

import datatype.VietJet.BookingInfo;
import datatype.VietJet.LanguageType;
import pages.VietJet.HomePage;
import pages.VietJet.SelectFarePage;
import tests.TestBase;
import utils.common.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;

public class TS_VietJet_ThanhHoang extends TestBase {

	@Test(description = "Search and choose cheapest tickets on next 3 months successfully.")
	public void TC02() {
		HomePage homePage = new HomePage();
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + "VietJet", new Object() {
		}.getClass().getEnclosingMethod().getName());

		Logger.info("1. Navigate to https://www.vietjetair.com/Sites/Web/vi-VN/Home.");
		launchApp("VietJet", LanguageType.VI);

		Logger.info("2. Search a ticket");

		BookingInfo booking = dataHelper.mapDataToObject(BookingInfo.class).compileData();
		homePage.searchFlight(booking);

		SelectFarePage selectFarePage = new SelectFarePage();

		Logger.info("3. Choose the cheapest tickets in next 3 months and click 'Tiếp tục' button");
		selectFarePage.selectCheapestDepFareInNextMonths(3);
	}

}
