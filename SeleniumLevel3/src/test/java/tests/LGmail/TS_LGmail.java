package tests.LGmail;

import org.testng.annotations.Test;
import datatype.LGmail.Email;
import datatype.LGmail.Enums.ComposeActions;
import datatype.LGmail.Enums.MailTreeItem;
import datatype.LGmail.Enums.MessageToolbarItem;
import driver.manager.DriverUtils;
import pages.LGmail.ComposeEmailPage;
import pages.LGmail.LogInPage;
import pages.LGmail.MainPage;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.constant.Constants;
import utils.helper.DataHelper;
import utils.helper.Logger;

public class TS_LGmail extends TestBase {
	@Test(description = "Verify that user can compose and save draft email successfully.")
	public void TC01() throws InterruptedException {

		SoftAssertion softAssert = new SoftAssertion();
		
		Logger.info("Precondition: Initial Data");
		
		// Pages
		LogInPage loginPage = new LogInPage();
		MainPage mainPage = new MainPage();
		ComposeEmailPage composeEmailPage = new ComposeEmailPage();
		
		//Test Data
		DataHelper testDataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC01");
		
		String username = testDataHelper.getDataObject(String.class, "UserName");
		String password = testDataHelper.getDataObject(String.class, "Password");
		Email mail = testDataHelper.getDataObject(Email.class).compileData();

		Logger.info("1. Navigate to https://sgmail.logigear.com/");
		// This step is included in @BeforeMethod

		Logger.info("2. Log in with your account");
		loginPage.login(username,password);

		Logger.info("3. Compose new email with attachment");
		mainPage.selectMessageToolbar(MessageToolbarItem.NEW);
		DriverUtils.switchToLatest();
		composeEmailPage.composeNewMail(mail);

		Logger.info("4. Save the email and close the composing email pop up");
		composeEmailPage.selectAction(ComposeActions.SAVE);
		DriverUtils.close();

		Logger.verify(
				"The email is save to Draft folder successfully with correct info(receiver, subject, attachment, content)");
		DriverUtils.switchToFirst();
		mainPage.openAMail(MailTreeItem.DRAFTS, mail.getSubject());
		DriverUtils.switchToLatest();
		Email draftEmail = composeEmailPage.getMailInfo();
		softAssert.assertTrue(draftEmail.toString().equals(mail.toString()), "Something missmatch, please check");

		softAssert.assertAll();
	}
	
}
