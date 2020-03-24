package tests.VietJet;


import org.testng.annotations.Test;

import datatype.BookingInfo;
import datatype.BookingInfo.*;
import datatype.LanguageType;
import datatype.TicketInfo;
import datatype.TicketInfo.FlightClass;
import pages.VietJet.HomePage;
import pages.VietJet.PassengerInformationPage;
import pages.VietJet.SelectFarePage;
import pages.VietJet.SelectTravelOptionsPage;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.helper.DateTimeHelper;
import utils.helper.Logger;

public class TC_VJ_01 extends TestBase {
	SoftAssertion softAssert;
	HomePage homePage;

	@Test(description = "TC_VJ_001, Verify that user can search and choose tickets on a specific day successfully.")
	public void TC01() {
		softAssert = new SoftAssertion();
		homePage = new HomePage();
		Logger.info("Precondition: Provide Booking Information");
		BookingInfo booking = new BookingInfo.FlightBuilder().setFlightOption(FlightOption.RETURN)
				.setOrigin(LocationOption.SGN).setDepartDate(DateTimeHelper.plusDays(3))
				.setDestination(LocationOption.HAN).setReturnDate(DateTimeHelper.plusDays(4)).setCurrency("VND")
				.setNumberOfAdults(2).build();
		booking.showInfo();
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/en-US/Home'.");
		launchApp("VietJet", LanguageType.US);
		Logger.info("2. Search the ticket with the following information");
		SelectTravelOptionsPage traveloptionPage = homePage.submitSearchFlight(booking).waitForPageLoad();
		Logger.info("VP. Select Travel Options page is displayed");
		softAssert.assertTrue(traveloptionPage.isDisplayed(), "Select Travel Options Page does not display.");
		Logger.info("VP. The ticket price is displayed in VND");
		softAssert.assertTrue(traveloptionPage.isCurrency(booking.getCurrency()),
				"Currency does not displayed as expected.");
		Logger.info("VP. The flights dates are displayed correctly");
		Logger.info("VP. The departure and arrival places are correct.");
		Logger.info("VP. Number of passenger is correct");
		softAssert.assertTrue(traveloptionPage.isBookingInfoCorrect(booking), "Failed! - Booking information is not as input");
		Logger.info("3. Choose the cheapest tickets and click 'Continue' button");
		traveloptionPage.selectCheapestTicket(FlightClass.PROMO);
		TicketInfo ticket = traveloptionPage.getTicketInfo().setBookingInfo(booking);
		ticket.showInfo();
		PassengerInformationPage passengerInfoPage = traveloptionPage.submitPage();
		Logger.info("VP. Passenger Information page is displayed");
		softAssert.assertTrue(passengerInfoPage.isDisplayed(), "Faield! - Passenger Information Page does not display as expected.");
		Logger.info("VP. Tickets information is correct");
		passengerInfoPage.checkTicketInfomation(ticket);
		softAssert.assertAll();
	}

	@Test(description = "TC_VJ_002, Verify that user can search and choose cheapest tickets on next 3 months successfully.")
	public void TC02() {
		softAssert = new SoftAssertion();
		homePage = new HomePage();
		Logger.info("Precondition: Provide Booking Information");
		BookingInfo booking = new BookingInfo.FlightBuilder().setFlightOption(FlightOption.RETURN)
				.setOrigin(LocationOption.SGN).setDestination(LocationOption.HAN).setCurrency("VND").setLowestFare(true)
				.setNumberOfAdults(2).build();
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/vi-VN/Home'.");
		launchApp("VietJet", LanguageType.VI);
		Logger.info("VP. The home page is displayed in Vietnamese");
		softAssert.assertTrue(homePage.isLanguage(LanguageType.VI));
	//	Logger.info("2. Search the ticket with the following information");
//		SelectFarePage selectFarePage = homePage.searchLowestFareFlight(booking);
//		Logger.info("VP. 'Chọn giá vé' page is displayed");
//		selectFarePage.checkPageDisplayed();
//		Logger.info("VP. The flight information is correct");
//		selectFarePage.checkBookingInformation(booking);
//		Logger.info("3. Choose the cheapest tickets in next 3 months and click 'Tiếp tục' button");
		// SelectTravelOptionsPage traveloptionPage =
		// selectFarePage.selectCheapestTicket(3);
		// TicketInfo tI = traveloptionPage.getTicketInfo().setBookingInfo(booking);
		// SelectFarePage selectFarePage = traveloptionPage.submitPage();
		// SelectTravelOptionsPage traveloptionPage
		// Logger.info("VP. 'Lựa chọn chuyến đi' page is displayed");
//		passengerInfoPage.checkPageDisplayed();
//		Logger.info("VP. Tickets information is correct");
//		passengerInfoPage.checkTicketInfomation(tI);
	}

}
