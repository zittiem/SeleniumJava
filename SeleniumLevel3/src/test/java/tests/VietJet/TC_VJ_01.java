package tests.VietJet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import datatype.BookingInfo;
import datatype.BookingInfo.FlightOption;
import datatype.BookingInfo.LocationOption;
import pages.VietJet.HomePage;
import pages.VietJet.SelectTravelOptionsPage;
import tests.TestBase;
import utils.common.Common;
import utils.helper.Logger;

public class TC_VJ_01 extends TestBase   {
	@Test(description = "TC_VJ_001, Verify that user can search and choose tickets on a specific day successfully.")
	public void TC01() {
		HomePage homePage = new HomePage();
		BookingInfo flight = new BookingInfo.FlightBuilder().setFlightOption(FlightOption.RETURN)
				.setOrigin(LocationOption.SGN).setDepartDate(Common.plusDays(1)).setDestination(LocationOption.HAN)
				.setReturnDate(Common.plusDays(4)).setCurrency("VND").setNumberOfAdults(2).build();		
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/en-US/Home'.");
		Logger.info("2. Search the ticket with the following information");
		SelectTravelOptionsPage traveloptionPage = homePage.submitSearchFlight(flight);
		Logger.info("VP. Select Travel Options page is displayed");
		assertTrue(traveloptionPage.isDisplayed(), "Failed! - Select Travel Option Page does not display as exected.");
		Logger.info("VP. The ticket price is displayed in VND");
		assertTrue(traveloptionPage.isCurrencyDisplay(flight.getCurrency()),
				"Failed! - Currency is different from " + flight.getCurrency());
		Logger.info("VP. The flights dates are displayed correctly");
		assertTrue(traveloptionPage.getDepartureDateInfo().contains(flight.getDepartDate().toString()), "Failed! - ");
		assertTrue(traveloptionPage.getReturnDateInfo().contains(flight.getReturnDate().toString()), "Failed! - ");
		Logger.info("VP. The departure and arrival places are correct.");
		assertEquals(traveloptionPage.getDepartureFromInfo(), flight.getOrigin().getValue(), "Failed! - Departure From info is not correct.");
		assertEquals(traveloptionPage.getDepartureToInfo(), flight.getDestination().getValue(), "Failed! - Departure To info is not correct.");
		assertEquals(traveloptionPage.getReturnFromInfo(), flight.getDestination().getValue(), "Failed! - Return From info is not correct.");
		assertEquals(traveloptionPage.getReturnToInfo(), flight.getOrigin().getValue(), "Failed! - Return To info is not correct.");
		Logger.info("VP. Number of passenger is correct");
		assertEquals(traveloptionPage.getNumberOfAdultsInfo(), flight.getNumberOfAdults(), "Failed! - ");
		assertEquals(traveloptionPage.getNumberOfChildrensInfo(), flight.getNumberOfChildens(), "Failed! - ");
		assertEquals(traveloptionPage.getNumberOfInfantsInfo(), flight.getNumberOfInfants(), "Failed! - ");		
		Logger.info("3. Choose the cheapest tickets and click 'Continue' button");
		//
		Logger.info("VP. Passenger Information page is displayed");
		//
		Logger.info("4. Tickets information is correct");
		//
	}

}
