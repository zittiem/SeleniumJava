package tests.VietJet;

import org.testng.annotations.Test;
import pages.VietJet.*;
import tests.TestBaseVJ;
import utils.helper.Logger;

public class TS_VietJet_ThanhTestReport extends TestBaseVJ {

	HomePage homePage;
	
	@Test(description = "Search and choose tickets on a specific day successfully.")
	
	public void TC01() {
		
		Logger.info("1. Navigate to 'https://www.vietjetair.com/Sites/Web/en-US/Home'.");
		homePage.selectOrigin("Ho Chi Minh (SGN)");
	}
	
}
