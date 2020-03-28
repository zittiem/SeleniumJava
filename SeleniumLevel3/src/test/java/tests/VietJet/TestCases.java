package tests.VietJet;

import org.testng.annotations.Test;

import datatype.VietJet.BookingInfo;
import datatype.VietJet.LanguageType;
import datatype.VietJet.TicketInfo;
import datatype.VietJet.TicketInfo.FlightClass;
import pages.VietJet.HomePage;
import pages.VietJet.PassengerInformationPage;
import pages.VietJet.SelectTravelOptionsPage;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.constants.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;

public class TestCases extends TestBase {
	@Test(description = "TC_VJ_001, Verify that user can search and choose tickets on a specific day successfully.")
	public void TC01() throws Exception {
		Logger.info("Precondition: Initial Data");
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + "VietJet", new Object() {
		}.getClass().getEnclosingMethod().getName());
		SoftAssertion softAssert = new SoftAssertion();
		HomePage homePage = new HomePage();
		BookingInfo booking = dataHelper.mapDataToObject(BookingInfo.class).compileData();
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/en-US/Home'.");
		launchApp("VietJet", LanguageType.US);
		Logger.info("2. Search the ticket with the following information");
		homePage.searchFlight(booking);
		Logger.verify("Select Travel Options page is displayed");
		SelectTravelOptionsPage traveloptionPage = new SelectTravelOptionsPage();
		softAssert.assertTrue(traveloptionPage.isDisplayed(), "Select Travel Options Page does not display.");
		Logger.verify("The ticket price is displayed in VND");
		softAssert.assertTrue(traveloptionPage.isCurrency(booking.getCurrency()),
				"The ticket price is not displayed in VND");
		Logger.verify("The flights dates are displayed correctly");
		Logger.verify("The departure and arrival places are correct.");
		Logger.verify("Number of passenger is correct");
		BookingInfo actBooking = traveloptionPage.getCurrentBookingInfo();
		actBooking.showInfo();
		softAssert.assertObjectEquals(actBooking, booking, "Booking info does not display properly.");
		Logger.info("3. Choose the cheapest tickets and click 'Continue' button");
		traveloptionPage.selectCheapestTicket(FlightClass.PROMO);
		TicketInfo ticket = traveloptionPage.getTicketDetails();
		traveloptionPage.submitPage();
		PassengerInformationPage passengerInfoPage = new PassengerInformationPage();
		Logger.verify("Passenger Information page is displayed");
		softAssert.assertTrue(passengerInfoPage.isDisplayed(), "Passenger Information Page display.");
		Logger.verify("Tickets information is correct");
		TicketInfo actTicket = passengerInfoPage.getCurrentTicketInfo();
		softAssert.assertObjectEquals(actTicket, ticket, "Tickets information is not correct");
		softAssert.assertAll();
	}

	@Test(description = "TC_VJ_003, Verify that user can search and choose cheapest tickets on next 3 months successfully.")
	public void TC03() {
	}

}
