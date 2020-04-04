package tests.LGmail;

import org.testng.annotations.Test;

import datatype.LGmail.UserInfo;
import pages.LGmail.LogInPage;
import tests.TestBase;
import utils.constant.Constants;
import utils.helper.DataHelper;

public class TC_01 extends TestBase {
	@Test(description = "Verify that user can search and filter hotel successfully .")
	public void TC01() throws InterruptedException {
		DataHelper userHelp = new DataHelper(Constants.DATA_FOLDER + this.appName, "UserInfo");
		UserInfo user = userHelp.getDataObject(UserInfo.class);
		launchApp();

		user.showInfo();
		LogInPage login = new LogInPage();

		login.login(user);

	}
}
