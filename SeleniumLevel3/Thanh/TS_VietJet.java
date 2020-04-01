package tests.VietJet;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.testng.annotations.Test;
import datatype.VietJet.Booking;
import datatype.VietJet.Enums.LanguageType;
import pages.VietJet.HomePage;
import pages.VietJet.PassengerInformationPage;
import pages.VietJet.SelectFarePage;
import pages.VietJet.SelectTravelOptionsPage;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.constant.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;

public class TS_VietJet extends TestBase {
	
	@Test(description = "Verify that user can search and choose tickets on a specific day successfully.")
	public void TC01() throws Exception {
		
		SoftAssertion softAssert = new SoftAssertion();
		
		Logger.info("Precondition: Initial Data");
		HomePage homePage = new HomePage();
		SelectTravelOptionsPage travelOptionPage = new SelectTravelOptionsPage();
		PassengerInformationPage passengerInfoPage = new PassengerInformationPage();
		
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC01");
		
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/en-US/Home'.");
		//This step is included in @BeforeMethod
		
		Logger.info("2. Search the ticket with the following information");
		Booking booking = dataHelper.getDataObject(Booking.class).compileData();
		homePage.searchFlight(booking);
		
		Logger.verify("Select Travel Options page is displayed");
		softAssert.assertTrue(travelOptionPage.isDisplayed(), "Select Travel Options Page does not display.");
		
		Logger.verify("The ticket price is displayed in VND");
		softAssert.assertTrue(travelOptionPage.isCurrency(booking.getCurrency()),
				"The ticket price is not displayed in VND");
		
		Logger.verify("The flights dates are displayed correctly");
		Logger.verify("The departure and arrival places are correct.");
		Logger.verify("Number of passenger is correct");
		Booking actBooking = travelOptionPage.getCurrentBookingInfo();
		softAssert.assertObjectEquals(actBooking, booking, "Booking info does not display properly.");
		
		Logger.info("3. Choose the cheapest tickets and click 'Continue' button");
		travelOptionPage.selectCheapestTickets();
		Booking expTicketDetails = travelOptionPage.getTicketDetails();
		travelOptionPage.submitPage();
		
		Logger.verify("Passenger Information page is displayed");
		softAssert.assertTrue(passengerInfoPage.isDisplayed(), "Passenger Information Page display.");
		
		Logger.verify("Tickets information is correct");
		Booking actTicketDetails = passengerInfoPage.getCurrentBookingInfo();
		softAssert.assertObjectEquals(actTicketDetails, expTicketDetails, "Tickets information is not correct");
		
		softAssert.assertAll();
	}

	@Test(description = "Verify that user can search and choose cheapest tickets on next 3 months successfully.")
	public void TC02() throws ParseException {
		
		SoftAssertion softAssert = new SoftAssertion();
		
		Logger.info("Precondition: Initial Data");
		HomePage homePage = new HomePage();
		SelectTravelOptionsPage selectTravelOptionsPage = new SelectTravelOptionsPage();
		PassengerInformationPage passengerInfoPage = new PassengerInformationPage();
		
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC02");
		
		Logger.info("1. Navigate to https://www.vietjetair.com/Sites/Web/vi-VN/Home.");
		//This step is included in @BeforeMethod
		
		Logger.verify("The home page is displayed in Vietnamese");
		softAssert.assertTrue(homePage.getSelectedLanguage().contains(LanguageType.VI.getText()), "The home page is not displayed in Vietnamese.");
		
		Logger.info("2. Search a ticket");
		Booking booking = dataHelper.getDataObject(Booking.class).compileData();
		SelectFarePage selectFarePage = homePage.searchCheapestFlights(booking);
		
		Logger.verify("'Chọn giá vé' page is displayed.");
		softAssert.assertEquals(selectFarePage.getPageTitle(), "Vietjet Trang đặt chỗ - Chọn giá vé", "'Chọn giá vé' page is not displayed.");
		
		Logger.verify("The flight information is correct");
		softAssert.assertTrue(selectFarePage.isFightInfoCorrect(booking), "The flight information is incorrect.");
		
		Logger.info("3. Choose the cheapest tickets in next 3 months and click 'Tiếp tục' button");	
		
		// Define minDate and maxDate
		Calendar cal = Calendar.getInstance();
	    Date minDate = cal.getTime();
	    cal.add(Calendar.MONTH, 3);
	    Date maxDate = cal.getTime();
	    
	    //Select combo cheapest fare for the return flight
	    selectFarePage.selectReturnFlightCheapestFareInDefinedTimeFrame(minDate, maxDate);
	    selectFarePage.submitPage();
	    
	    Logger.info("4. Choose the cheapest tickets and click 'Continue' button");
	    selectTravelOptionsPage.selectCheapestTickets();
		Booking expBookingDetails = selectTravelOptionsPage.getTicketDetails();
		selectTravelOptionsPage.submitPage();
		
		Logger.verify("Passenger Information page is displayed");
		softAssert.assertTrue(passengerInfoPage.isDisplayed(), "Passenger Information Page display.");
		
		Logger.verify("Tickets information is correct");
		Booking actBookingDetails = passengerInfoPage.getCurrentBookingInfo();
		softAssert.assertObjectEquals(actBookingDetails, expBookingDetails, "Tickets information is not correct");
		
		softAssert.assertAll();
	}
	
}
