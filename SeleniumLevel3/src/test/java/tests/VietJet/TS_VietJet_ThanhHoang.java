package tests.VietJet;

import java.text.ParseException;

import org.testng.annotations.Test;
import datatype.BookingInfo;
import datatype.LanguageType;
import driver.manager.DriverUtils;
import pages.VietJet.HomePage;
import pages.VietJet.SelectFarePage;
import pages.VietJet.SelectTravelOptionsPage;
import datatype.BookingInfo.FlightOption;
import datatype.BookingInfo.LocationOption;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.helper.Logger;

public class TS_VietJet_ThanhHoang extends TestBase {
	
	@Test(description = "Search and choose cheapest tickets on next 3 months successfully.")
	public void TC02() throws ParseException {
		HomePage homePage = new HomePage();
		SoftAssertion softAssert = new SoftAssertion();
		
		Logger.info("1. Navigate to https://www.vietjetair.com/Sites/Web/vi-VN/Home.");
		launchApp("VietJet",LanguageType.VI);
		
		Logger.verify("The home page is displayed in Vietnamese");
		softAssert.assertTrue(homePage.getSelectedLanguage().contains(LanguageType.VI.getText()), "The home page is not displayed in Vietnamese.");
		
		Logger.info("2. Search a ticket");
		BookingInfo booking = new BookingInfo.FlightBuilder().setFlightOption(FlightOption.RETURN)
				.setOrigin(LocationOption.SGN).setDestination(LocationOption.HAN)
				.setLowestFare(true).setCurrency("VND").setNumberOfAdults(2).build();	
		
		SelectFarePage selectFarePage = homePage.searchCheapestFlights(booking);
		
		Logger.verify("'Chọn giá vé' page is displayed.");
		softAssert.assertEquals(selectFarePage.getPageTitle(), "Vietjet Trang đặt chỗ - Chọn giá vé", "'Chọn giá vé' page is not displayed.");
		
		Logger.verify("The flight information is correct");
		softAssert.assertTrue(selectFarePage.isFightInfoCorrect(booking), "The flight information is incorrect.");
		
		Logger.info("3. Choose the cheapest tickets in next 3 months and click 'Tiếp tục' button");	
		booking = selectFarePage.selectCheapestFareForReturnFightFareInNextMonths(3,booking);
		
		System.out.print("booking_depDate: " + booking.getDepartDate() + " \n");
		System.out.print("booking_retDate: " + booking.getReturnDate() + " \n");
		
		Logger.verify("'Lựa chọn chuyến đi' page is displayed.");
		SelectTravelOptionsPage selectTravelOptionsPage = new SelectTravelOptionsPage();
		softAssert.assertEquals(selectTravelOptionsPage.getPageTitle(), "Vietjet Trang đặt chỗ - Chọn chuyến bay", "'Chọn giá vé' page is not displayed.");
		
		Logger.verify("Ticket information is correct");
		softAssert.assertTrue(selectTravelOptionsPage.isBookingInfoCorrect(booking), "Ticket information is incorrect.");
		
		Logger.info("4. Choose the cheapest tickets and click 'Continue' button");	
		
		softAssert.assertAll();
		
	}
	
}
