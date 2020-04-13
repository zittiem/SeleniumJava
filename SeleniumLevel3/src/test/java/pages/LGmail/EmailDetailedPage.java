package pages.LGmail;

import java.io.IOException;
import datatype.LGmail.Email;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.setting.FindBy;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ImageHelper;
import utils.helper.ResourceHelper;

public class EmailDetailedPage extends ComposeEmailPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			EmailDetailedPage.class);

	// Static Elements
	protected Element eleSubject = new Element(locator.getLocator("eleSubject"));
	protected Element eleSender = new Element(locator.getLocator("eleSender"));

	// Methods
	
	// Verify
	
	 /**
     * Return a Boolean value to indicate whether inserted picture is displayed
     *
     * @param	expectedInsertedPicturePath
     * 			expected inserted picture file path
     *
     * @return  true|false
     * 			true: The inserted picture is displayed
     * 			false: The inserted picture is not displayed
     * 
     */
	public Boolean isInsertedPictureDisplayed(String expectedInsertedPicturePath) throws IOException, InterruptedException {
		DriverUtils.switchToFrame("ifBdy");
		DriverUtils.maximizeBrowser();
		//Thread.sleep(10000);
		elesInsertedPicture.waitForDisplayed(Constants.SHORT_TIME);
		Boolean result = ImageHelper.compareImages(elesInsertedPicture, expectedInsertedPicturePath);
		DriverUtils.switchToDefaultFrame();
		return result;
	}
	
	 /**
     * Return a Boolean value to indicate whether information of email is correct
     *
     * @param	expectedEmail
     *			expected email
     *
     * @param	expectedInsertedPicturePath
     * 			expected inserted picture file path
     *
     * @return  true|false
     * 			true: Email information matches with expected
     * 			false: Email information does not match with expected
     * 
     */
	public Boolean isEmailInfoCorrect(Email expectedEmail, String expectedInsertedPicturePath) throws IOException, InterruptedException {
		String expectedContent = "";
		if(expectedEmail.getContent() != null) {
			expectedContent = expectedEmail.getContent();
		}
		
		System.out.print("To: " + txtTo.getChildElement(FindBy.xpath, "//span[@title]").getAttribute("title") + " vs " + expectedEmail.getTo() + " \n");
		System.out.print("Subject: " + eleSubject.getText() + " vs " + expectedEmail.getSubject() + " \n");
		System.out.print("MailContent: " + getMailContent() + " vs " + expectedContent + " \n");
		System.out.print("CompareInsertedPicture: " + isInsertedPictureDisplayed(expectedInsertedPicturePath) + " \n");
		
		return 	txtTo.getChildElement(FindBy.xpath, "//span[@title]").getAttribute("title").equals(expectedEmail.getTo())
				&& eleSubject.getText().equals(expectedEmail.getSubject())
				&& getMailContent().equals(expectedContent)
				&& isInsertedPictureDisplayed(expectedInsertedPicturePath);

	}
}
