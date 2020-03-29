package tests;


import datatype.VietJet.DataManager;
import datatype.VietJet.DataManager.SharedData;
import driver.manager.DriverUtils;
import utils.constants.Constants;
import utils.helper.DataHelper;

public class VietJetTestBase extends TestBase {
	
	@Override
	protected void prepareAppData() {
		DataHelper dataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "SharedData");
		SharedData shared = dataHelper.getDataObject(SharedData.class, this.language);
		shared.appName = this.appName;
		shared.language = this.language;
		DataManager.SHARED_DATA.set(shared);
	}
	
	@Override
	protected void launchApp() {
		DriverUtils.maximizeBrowser();
		DriverUtils.navigate(DataManager.SHARED_DATA.get().url);
	}
}