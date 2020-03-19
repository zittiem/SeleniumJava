package tests.vietjet;

import org.testng.annotations.Test;

import pages.vietjet.*;
import tests.TestBase;
import utils.helper.Logger;

public class TS_VietJet_ThanhHoang extends TestBase {
	
	@Test(description = "Search and choose tickets on a specific day successfully.")
	public void TC01() {
		HomePage homePage = new HomePage();
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/en-US/Home'.");
		homePage.checkControlDisplay();
	}
	
	@Test(description = "Search and choose tickets on a specific day successfully.")
	public void TC02() {
		HomePage homePage = new HomePage();
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/en-US/Home'.");
		homePage.checkControlDisplay();
		homePage.checkControlNotDisplay();
		homePage.checkControlNotDisplay();
	}
	
}
