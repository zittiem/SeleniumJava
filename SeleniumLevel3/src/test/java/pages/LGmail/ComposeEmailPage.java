package pages.LGmail;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import datatype.LGmail.Email;
import datatype.LGmail.Enums.ComposeActions;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.setting.FindBy;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ImageHelper;
import utils.helper.ResourceHelper;

public class ComposeEmailPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			ComposeEmailPage.class);

	// Static Elements
	protected Element eleActions = new Element(locator.getLocator("eleActions"));
	protected TextBox txtTo = new TextBox(locator.getLocator("txtTo"));
	protected TextBox txtCC = new TextBox(locator.getLocator("txtCC"));
	protected TextBox txtSubject = new TextBox(locator.getLocator("txtSubject"));
	protected TextBox txtBody = new TextBox(locator.getLocator("txtBody"));
	protected Element eleAttachments = new Element(locator.getLocator("eleAttachments"));
	protected Element elesInsertedPicture = new Element(locator.getLocator("elesInsertedPicture"));

	// Methods
	
    /**
     * Wait for page load completely.
     */
	public void waitForPageLoad() {
		List<String> windows = DriverUtils.getWindowHandles();
		while(windows.size()==1){
			windows = DriverUtils.getWindowHandles();
		}
	}
	
    /**
     * Save the inserted picture in a email
     * 
     * @param	filePath
     * 			filePath to save the inserted picture
     * @param	fileName
     * 			file name of the saved picture
     * 
     * @throws	Exception
     */
	public List<String> saveImage(String filePath, String fileName) throws Exception {
		
		List<String> output = new ArrayList<String>();
		DriverUtils.switchToLatest();
		DriverUtils.maximizeBrowser();
		DriverUtils.switchToFrame("ifBdy");
		
		// Wait for picture loaded completely");
		Thread.sleep(5000);
		
		List<Element> eles = elesInsertedPicture.getWrapperElements();
		for (int i = 0; i < eles.size(); i++) {
			output.add(filePath + fileName + "_" + i + 1 + ".jpg");
			ImageHelper.saveImage(eles.get(i), filePath, fileName + "_" + i + 1, "jpg");
		}		
		DriverUtils.switchToDefaultFrame();
		return output;
	}
	
    /**
     * Attach File into the email
     * 
     * @param	files
     * 			list of files need to be attached, each file will be separated by ","
     * 
     * @throws	InterruptedException
     */
	private void attachFiles(String files) throws InterruptedException {
		if(files!=null) {
			UploadFilePage upFile = new UploadFilePage();
			eleActions.generateDynamic(ComposeActions.ATTACH_FILE.getID()).click();
			upFile.insertFiles(files);
		}
	}
	
    /**
     * Insert picture into the email
     * 
     * @param	files
     * 			list of pictures need to be attached, each file will be separated by ","
     * 
     * @throws	InterruptedException
     */
	private void insertPicture(String files) throws InterruptedException {
		if(files!=null) {
			UploadFilePage upFile = new UploadFilePage();
			eleActions.generateDynamic(ComposeActions.INSERT_IMAGE.getID()).click();
			upFile.insertFiles(files);
		}
	}

    /**
     * Get name of attachments
     * 
     * @return	a string of name of attachments
     * 
     */
	public String getAttachmentsName() {
		List<Element> eleAtt = eleAttachments.getWrapperElements();
		if(eleAtt.size()==0) {
			return null;
		}
		String attNam = "";
		for (Element e : eleAtt) {
			attNam += e.getAttribute("_attname") + ";";
		}
		return attNam.substring(0, attNam.length() - 1);
	}

    /**
     * Get email content
     * 
     * @return	a string of email content
     * 
     */
	public String getMailContent() {
		String curContent = null;
		DriverUtils.getDriver().switchTo().frame("ifBdy");
		curContent = txtBody.getAttribute("innerText").trim();
		DriverUtils.getDriver().switchTo().defaultContent();
		return curContent;
	}

    /**
     * Enter email content
     * 
     * @param	content
     * 			email content
     * 
     */
	private void enterContent(String content) {
		if(content!=null) {
			DriverUtils.getDriver().switchTo().frame("ifBdy");
			txtBody.enter(content);
			DriverUtils.getDriver().switchTo().defaultContent();
		}
	}

    /**
     * Compose a new email
     * 
     * @param	mail
     * 			Object mail
     * 
     * @throws	InterruptedException
     */
	public void composeNewMail(Email mail) throws InterruptedException {
		txtTo.enter(mail.getTo());
		txtCC.enter(mail.getCcList());
		txtSubject.enter(mail.getSubject());
		attachFiles(mail.getAttachedFiles());
		insertPicture(mail.getInsertedPictures());
		enterContent(mail.getContent());
	}

    /**
     * Get cc list
     * 
     * @return	a string of cc list
     * 
     */
	public String getCCList() {
		String cClist = null;
		Element eleCC = new Element(FindBy.xpath, txtCC.getLocator().toString().substring(10) + "//span[@title]");
		if (eleCC.isDisplayed()) {
			cClist = txtCC.getChildElement(FindBy.xpath, "//span[@title]").getAttribute("title");
		} else {
			if (!txtCC.getText().isEmpty()) {
				cClist = txtCC.getText();
			}
		}
		return cClist;
	}

    /**
     * Get information of email
     * 
     * @return	Object Email
     * 
     * @throws 	InterruptedException
     * 
     */
	public Email getMailInfo() throws InterruptedException {
		Email curEmail = new Email();
		curEmail.setTo(txtTo.getChildElement(FindBy.xpath, "//span[@title]").getAttribute("title"));
		curEmail.setCcList(getCCList());
		curEmail.setSubject(txtSubject.getAttribute("value"));
		curEmail.setAttachedFiles(getAttachmentsName());
		curEmail.setContent(getMailContent());
		return curEmail;
	}

	
    /**
     * Select compose actions. They are SEND|SAVE|ATTACH_FILE|INSERT_IMAGE|ADDRESS_BOOK|CHECK_NAMES
     * 
     * @param	action
     * 			They are SEND|SAVE|ATTACH_FILE|INSERT_IMAGE|ADDRESS_BOOK|CHECK_NAMES
     * 
     */
	public void selectAction(ComposeActions action) {
		eleActions.generateDynamic(action.getID()).click();
	}
	
	// Verify
	 /**
     * Return a Boolean value to indicate whether information of email is correct
     *
     * @param	expectedEmail
     *			expected email
     *
     * @return  true|false
     * 			true: Email information matches with expected
     * 			false: Email information does not match with expected
     * 
     */
	public Boolean isEmailInfoCorrect(Email expectedEmail) {
		System.out.print("To: " + txtTo.getChildElement(FindBy.xpath, "//span[@title]").getAttribute("title") + " vs " + expectedEmail.getTo() + " \n");
		System.out.print("CCList: " + getCCList() + " vs " + expectedEmail.getCcList() + " \n");
		System.out.print("Subject: " + txtSubject.getAttribute("value") + " vs " + expectedEmail.getSubject() + " \n");
		System.out.print("Attachments: " + getAttachmentsName() + " vs " + expectedEmail.getAttachedFiles() + " \n");
		System.out.print("MailContent: " + getMailContent() + " vs " + expectedEmail.getAttachedFiles() + " \n");
		
		return 	txtTo.getChildElement(FindBy.xpath, "//span[@title]").getAttribute("title").equals(expectedEmail.getTo())
				&& getCCList().equals(expectedEmail.getCcList())
				&& txtSubject.getAttribute("value").equals(expectedEmail.getSubject())
				&& getAttachmentsName().equals(expectedEmail.getAttachedFiles())
				&& getMailContent().equals(expectedEmail.getContent());
	}
	
}
