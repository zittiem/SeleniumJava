package tests.VietJet;

import org.testng.annotations.Test;

import pages.VietJet.HomePage;
import tests.TestBase;
import utils.helper.Logger;

public class TS_VietJet_ThanhHoang extends TestBase {
	
	@Test(description = "Search and choose tickets on a specific day successfully.")
	public void TC01() {
		HomePage homePage = new HomePage();
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/en-US/Home'.");
	}
	
	@Test(description = "Search and choose tickets on a specific day successfully.")
	public void TC02() {
		HomePage homePage = new HomePage();
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/en-US/Home'.");
	}
	
}
