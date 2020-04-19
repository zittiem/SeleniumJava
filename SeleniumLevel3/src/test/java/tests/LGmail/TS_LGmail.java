package tests.LGmail;

import java.io.File;

import org.testng.annotations.Test;
import datatype.LGmail.Email;
import datatype.LGmail.Enums.ComposeActions;
import datatype.LGmail.Enums.MailTreeItem;
import datatype.LGmail.Enums.MessageToolbarItem;
import datatype.LGmail.UserInfo;
import driver.manager.DriverUtils;
import pages.LGmail.ComposeEmailPage;
import pages.LGmail.LogInPage;
import pages.LGmail.MainPage;
import pages.LGmail.EmailDetailedPage;
import tests.TestBase;
import utils.assertion.SoftAssertion;
import utils.constant.Constants;
import utils.helper.DataHelper;
import utils.helper.ImageHelper;
import utils.helper.Logger;

public class TS_LGmail extends TestBase {
	@Test(description = "Verify that user can compose and save draft email successfully.")
	public void TC01() throws Exception {

		SoftAssertion softAssert = new SoftAssertion();
		
		Logger.info("Precondition: Initial Data");
		
		// Pages
		LogInPage loginPage = new LogInPage();
		MainPage mainPage = new MainPage();
		ComposeEmailPage composeEmailPage = new ComposeEmailPage();
		
		//Test Data
		DataHelper testDataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC01");
		
		Email mail = testDataHelper.getDataObject(Email.class).compileData();
		UserInfo userInfo = testDataHelper.getDataObject(UserInfo.class);

		Logger.info("1. Navigate to https://sgmail.logigear.com/");
		// This step is included in @BeforeMethod

		Logger.info("2. Log in with your account");
		loginPage.login(userInfo);

		Logger.info("3. Compose new email with attachment");
		mainPage.selectMessageToolbar(MessageToolbarItem.NEW);
		composeEmailPage.waitForPageLoad();
		DriverUtils.switchToLatest();
		composeEmailPage.composeNewMail(mail);

		Logger.info("4. Save the email and close the composing email pop up");
		composeEmailPage.selectAction(ComposeActions.SAVE);
		DriverUtils.close();

		Logger.verify(
				"The email is save to Draft folder successfully with correct info(receiver, subject, attachment, content)");
		DriverUtils.switchToFirst();
		mainPage.openMail(MailTreeItem.DRAFTS, mail.getSubject());
		composeEmailPage.waitForPageLoad();
		DriverUtils.switchToLatest();
		softAssert.assertTrue(composeEmailPage.isEmailInfoCorrect(mail));
		
		// Clean up mail
		try {
			DriverUtils.switchToFirst();
			mainPage.deleteMail(MailTreeItem.DRAFTS, mail.getSubject());
		}
		catch (Exception e) {}

		softAssert.assertAll();
	}
	
	@Test(description = "Verify that user can compose and save draft email successfully.")
	public void TC02() throws Exception {

		SoftAssertion softAssert = new SoftAssertion();
		
		Logger.info("Precondition: Initial Data");
		
		// Pages
		LogInPage loginPage = new LogInPage();
		MainPage mainPage = new MainPage();
		ComposeEmailPage composeEmailPage = new ComposeEmailPage();
		EmailDetailedPage emailDetailedPage = new EmailDetailedPage();
		
		//Test Data
		DataHelper testDataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC02");
		
		Email mail = testDataHelper.getDataObject(Email.class).compileData();
		UserInfo userInfo = testDataHelper.getDataObject(UserInfo.class);
		
		Logger.info("1. Navigate to https://sgmail.logigear.com/");
		// This step is included in @BeforeMethod

		Logger.info("2. Log in with your account");
		loginPage.login(userInfo);

		Logger.info("3. Compose new email with image inserted in content");
		mainPage.selectMessageToolbar(MessageToolbarItem.NEW);
		composeEmailPage.waitForPageLoad();
		DriverUtils.switchToLatest();
		composeEmailPage.composeNewMail(mail);

		Logger.info("4. Send the email to your email");
		composeEmailPage.selectAction(ComposeActions.SEND);

		Logger.verify(
				"The email is sent successfully with correct info(receiver, subject, attachment, content)");
		DriverUtils.switchToFirst();
		mainPage.openMail(MailTreeItem.INBOX, mail.getSubject());
		emailDetailedPage.waitForPageLoad();
		DriverUtils.switchToLatest();
		softAssert.assertTrue(emailDetailedPage.isEmailInfoCorrect(mail));
		
		Logger.info(
				"5. Open the new received email and download the image in this email");
		
		String downloadedInsertedPicturePath = new File(Constants.DATA_FOLDER + this.appName + "/Downloaded").getAbsolutePath() + "\\";
		String actualInsertedPicturePath = emailDetailedPage.saveImage(downloadedInsertedPicturePath, "ActualInsertedPicture").get(0);
		
		Logger.verify(
				"Download the image successfully");
		String expectedInsertedPicturePath = new File(Constants.DATA_FOLDER + this.appName + "/" + mail.getInsertedImages()[0]).getAbsolutePath();
		softAssert.assertTrue(ImageHelper.compareImages(actualInsertedPicturePath, expectedInsertedPicturePath), "Downloaded image is incorrect: " + actualInsertedPicturePath);
		
		// Clean up mail
		try {
			DriverUtils.switchToFirst();
			mainPage.deleteMail(MailTreeItem.INBOX, mail.getSubject());
		}
		catch (Exception e) {}
		
		softAssert.assertAll();
		
	}
}
