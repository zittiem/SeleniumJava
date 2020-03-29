package tests.VietJet;

import java.text.ParseException;
import org.testng.annotations.Test;
import datatype.VietJet.BookingInfo;
import datatype.VietJet.Enums.FlightClass;
import datatype.VietJet.Enums.LanguageType;
import datatype.VietJet.TicketInfo;
import pages.VietJet.HomePage;
import pages.VietJet.PassengerInformationPage;
import pages.VietJet.SelectFarePage;
import pages.VietJet.SelectTravelOptionsPage;
import tests.TestBase;
import tests.VietJetTestBase;
import utils.assertion.SoftAssertion;
import utils.constants.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;

public class TS_VietJet_ThanhHoang extends VietJetTestBase {
	
	@Test(description = "Search and choose cheapest tickets on next 3 months successfully.")
	public void TC02() throws ParseException {
		HomePage homePage = new HomePage();
		SelectTravelOptionsPage selectTravelOptionsPage = new SelectTravelOptionsPage();
		PassengerInformationPage passengerInfoPage = new PassengerInformationPage();
		SoftAssertion softAssert = new SoftAssertion();
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + "VietJet", new Object() {
		}.getClass().getEnclosingMethod().getName());
		
		Logger.info("1. Navigate to https://www.vietjetair.com/Sites/Web/vi-VN/Home.");
		launchApp();
		
		Logger.verify("The home page is displayed in Vietnamese");
		softAssert.assertTrue(homePage.getSelectedLanguage().contains(LanguageType.VI.getText()), "The home page is not displayed in Vietnamese.");
		
		Logger.info("2. Search a ticket");
		BookingInfo booking = dataHelper.getDataObject(BookingInfo.class).compileData();
		SelectFarePage selectFarePage = homePage.searchCheapestFlights(booking);
		
		Logger.verify("'Chọn giá vé' page is displayed.");
		softAssert.assertEquals(selectFarePage.getPageTitle(), "Vietjet Trang đặt chỗ - Chọn giá vé", "'Chọn giá vé' page is not displayed.");
		
		Logger.verify("The flight information is correct");
		softAssert.assertTrue(selectFarePage.isFightInfoCorrect(booking), "The flight information is incorrect.");
		
		Logger.info("3. Choose the cheapest tickets in next 3 months and click 'Tiếp tục' button");	
		booking = selectFarePage.selectCheapestFareForReturnFightInNextMonths(3,booking);
		
		Logger.verify("'Lựa chọn chuyến đi' page is displayed.");
		softAssert.assertEquals(selectTravelOptionsPage.getPageTitle(), "Vietjet Trang đặt chỗ - Chọn chuyến bay", "'Chọn giá vé' page is not displayed.");
		
		Logger.verify("Ticket information is correct");
		softAssert.assertTrue(selectTravelOptionsPage.isBookingInfoCorrect(booking), "Ticket information is incorrect.");
		
		Logger.info("4. Choose the cheapest tickets and click 'Continue' button");	
		selectTravelOptionsPage.selectCheapestTicket(FlightClass.PROMO);
		//TicketInfo ticket = selectTravelOptionsPage.getTicketDetails();
		selectTravelOptionsPage.submitPage();
		
		Logger.verify("Passenger Information page is displayed");
		softAssert.assertTrue(passengerInfoPage.isDisplayed(), "Passenger Information Page display.");
		
		Logger.verify("Tickets information is correct");
		//TicketInfo actTicket = passengerInfoPage.getCurrentTicketInfo();
		//softAssert.assertObjectEquals(actTicket, ticket, "Tickets information is not correct");
		
		softAssert.assertAll();
		
	}
	
}
