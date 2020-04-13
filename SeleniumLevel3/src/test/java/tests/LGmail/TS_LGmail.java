package tests.LGmail;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.Test;
import datatype.LGmail.Email;
import datatype.LGmail.Enums.ComposeActions;
import datatype.LGmail.Enums.MailTreeItem;
import datatype.LGmail.Enums.MessageToolbarItem;
import driver.manager.DriverUtils;
import pages.LGmail.ComposeEmailPage;
import pages.LGmail.LogInPage;
import pages.LGmail.MainPage;
import pages.LGmail.EmailPage;
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
	
	@Test(description = "Verify that user can compose and save draft email successfully.")
	public void TC02() throws InterruptedException, IOException {

		SoftAssertion softAssert = new SoftAssertion();
		
		Logger.info("Precondition: Initial Data");
		
		// Pages
		LogInPage loginPage = new LogInPage();
		MainPage mainPage = new MainPage();
		ComposeEmailPage composeEmailPage = new ComposeEmailPage();
		EmailPage emailPage = new EmailPage();
		
		//Test Data
		DataHelper testDataHelper = new DataHelper(Constants.DATA_FOLDER + this.appName, "TC02");
		
		String username = testDataHelper.getDataObject(String.class, "UserName");
		String password = testDataHelper.getDataObject(String.class, "Password");
		Email mail = testDataHelper.getDataObject(Email.class).compileData();

		Logger.info("1. Navigate to https://sgmail.logigear.com/");
		// This step is included in @BeforeMethod

		Logger.info("2. Log in with your account");
		loginPage.login(username,password);

		Logger.info("3. Compose new email with image inserted in content");
		mainPage.selectMessageToolbar(MessageToolbarItem.NEW);
		DriverUtils.switchToLatest();
		composeEmailPage.composeNewMail(mail);
		int expectedTotalInsertedPictures = composeEmailPage.getTotalInsertedPicture();

		Logger.info("4. Send the email to your email");
		composeEmailPage.selectAction(ComposeActions.SEND);
		Thread.sleep(5000);

		Logger.verify(
				"The email is sent successfully with correct info(receiver, subject, attachment, content)");
		DriverUtils.switchToFirst();

		mainPage.openAMail(MailTreeItem.INBOX, mail.getSubject());
		DriverUtils.switchToLatest();
		softAssert.assertTrue(emailPage.isEmailInfoCorrect(mail, expectedTotalInsertedPictures));
		
		Logger.info(
				"Open the new received email and download the image in this email ");
		
		emailPage.saveInsertedImage();
		
		Logger.verify(
				"Download the image successfully");
		softAssert.assertAll();
		
		// Check Image is insert into Mail
				System.out.println("Image is insert from Data file (TestAttachFile.jpg): ?"
						+ emailDetail.compareImages(email.getInsertedPictures()));

				// Download insert Image
				// Handle for multi-Images
				System.out.println("Insert Image is saved as: " + emailDetail.saveImage("C:\\temp\\", "SavedFile").get(0));

				// Check Image Downloaded
				System.out.println("Are downloaded image same as (TestAttachFile.jpg)?? "
						+ emailDetail.compareImages("C:\\temp\\SavedFile_01.jpg", email.getInsertedPictures()));

	}
}
