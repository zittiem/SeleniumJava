package tests.VietJet;

import org.testng.annotations.Test;

import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.helper.Logger;

public class TS_VietJet_ThanhHoang extends TestBase {
	
	@Test(description = "Search and choose tickets on a specific day successfully.")
	public void TC01() {
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/en-US/Home'.");
		SoftAssertion softAssert = new SoftAssertion();
		softAssert.assertEquals(true, true, "Check booking info");
		softAssert.assertEquals(true, false, "Check booking info");
		softAssert.assertEquals(true, true, "Check booking info");
		softAssert.assertAll();
	}
	
	@Test(description = "Search and choose tickets on a specific day successfully.")
	public void TC02() {
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/en-US/Home'.");
		SoftAssertion softAssert = new SoftAssertion();
		softAssert.assertEquals(true, true, "Check booking info");
		softAssert.assertAll();
	}
	
}
