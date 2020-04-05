package tests.LGmail;

import org.testng.annotations.Test;

import datatype.LGmail.EmailInfo;
import datatype.LGmail.Enums.ComposeActions;
import datatype.LGmail.Enums.MailTreeItem;
import datatype.LGmail.UserInfo;
import pages.LGmail.LogInPage;
import pages.LGmail.MainPage;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.constant.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;

public class TC_01 extends TestBase {
	@Test(description = "Verify that user can compose and save draft email successfully.")
	public void TC01() throws InterruptedException {

		SoftAssertion softAssert = new SoftAssertion();
		Logger.info("Precondition: Initial Data");
		DataHelper userHelp = new DataHelper(Constants.DATA_FOLDER + this.appName, "UserInfo");
		DataHelper testDataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC01");
		UserInfo user = userHelp.getDataObject(UserInfo.class);
		user.showInfo();
		EmailInfo mail = testDataHelper.getDataObject(EmailInfo.class).compileData();
		mail.showInfo();

		Logger.info("1. Navigate to https://sgmail.logigear.com/");
		// launchApp();

		Logger.info("2. Log in with your account");
		LogInPage login = new LogInPage();
		login.login(user);

		Logger.info("3. Compose new email with attachment");
		MainPage main = new MainPage();
		main.composeMewMail(mail);

		Logger.info("4. Save the email and close the composing email pop up");
		main.selectAction(ComposeActions.SAVE);
		main.closeComposeNewMail();

		EmailInfo currenMailtInfo = main.getMailInfo(MailTreeItem.DRAFTS, mail.getSubject());
		Logger.verify(
				"The email is save to Draft folder successfully with correct info(receiver, subject, attachment, content)");
		softAssert.assertTrue(currenMailtInfo.toString().equals(mail.toString()), "Something missmatch, please check");

	}
}
