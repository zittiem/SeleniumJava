package tests.VietJet;

import org.testng.annotations.Test;
import datatype.BookingInfo;
import datatype.LanguageType;
import pages.VietJet.HomePage;
import pages.VietJet.SelectFarePage;
import datatype.BookingInfo.FlightOption;
import datatype.BookingInfo.LocationOption;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.helper.Logger;

public class TS_VietJet_ThanhHoang extends TestBase {
	
	@Test(description = "Search and choose cheapest tickets on next 3 months successfully.")
	public void TC02() {
		HomePage homePage = new HomePage();
		
		Logger.info("1. Navigate to https://www.vietjetair.com/Sites/Web/vi-VN/Home.");
		launchApp("VietJet",LanguageType.VI);

		Logger.info("2. Search a ticket");
		
		BookingInfo booking = new BookingInfo.FlightBuilder().setFlightOption(FlightOption.RETURN)
				.setOrigin(LocationOption.SGN).setDestination(LocationOption.HAN)
				.setLowestFare(true).setCurrency("VND").setNumberOfAdults(2).build();		
		
		SelectFarePage selectFarePage = homePage.searchCheapestFlights(booking);

		Logger.info("3. Choose the cheapest tickets in next 3 months and click 'Tiếp tục' button");
		selectFarePage.selectCheapestDepFareInNextMonths(3);
	}
	
}
