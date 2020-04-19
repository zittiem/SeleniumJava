package pages.LGmail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import datatype.LGmail.Email;
import datatype.LGmail.Enums.ComposeActions;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.setting.FindBy;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ImageHelper;
import utils.helper.RandomHelper;
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
		StopWatch sw = new StopWatch();
		List<String> windows = DriverUtils.getWindowHandles();
		sw.start();
		while(windows.size()==1 && sw.getTime(TimeUnit.SECONDS) < Constants.SHORT_TIME){
			windows = DriverUtils.getWindowHandles();
		}
	}
	
	/**
     * Get To list
     * 
     * @return	a string of To list
     * 
     */
	public String getToList() {
		return txtTo.getChildElement(FindBy.xpath, ".//span[@title]").getAttribute("title");
	}
    
	/**
     * Get Cc list
     * 
     * @return	a string of Cc list
     * 
     */
	public String getCcList() {
		String cClist = null;
		Element eleCC = new Element(FindBy.xpath, txtCC.getLocator().toString().substring(10) + "//span[@title]");
		if (eleCC.isDisplayed()) {
			cClist = txtCC.getChildElement(FindBy.xpath, ".//span[@title]").getAttribute("title");
		} else if (txtCC.isDisplayed()) {
			if (!txtCC.getText().isEmpty()) {
				cClist = txtCC.getText();
			}
		}
		return cClist;
	}
	
	/**
     * Get email subject
     * 
     * @return	a string of email subject
     * 
     */
	public String getMailSubject() {
		return txtSubject.getAttribute("value");
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
		List<String> attNam = new ArrayList<String>();
		for (Element e : eleAtt) {
			attNam.add(e.getAttribute("_attname"));
		}
		
		return Arrays.toString(attNam.toArray());
	}

    /**
     * Get email content
     * 
     * @return	a string of email content
     * 
     */
	public String getMailContent() {
		String curContent = null;
		DriverUtils.switchToFrame("ifBdy");
		curContent = txtBody.getAttribute("innerText").trim();
		DriverUtils.switchToDefaultFrame();
		return curContent;
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
	public List<String> saveImage(String folderPath, String fileName) throws Exception {
		File directory = new File(folderPath);
	    if (!directory.exists()){
	        directory.mkdirs();
	    }
		List<String> output = new ArrayList<String>();
		DriverUtils.switchToLatest();
		DriverUtils.maximizeBrowser();
		DriverUtils.switchToFrame("ifBdy");
		
		List<Element> eles = elesInsertedPicture.getWrapperElements();
		for (int i = 0; i < eles.size(); i++) {
			String filePath = folderPath + fileName + "_" + RandomHelper.randomString() + ".jpg";
			output.add(filePath);
			ImageHelper.saveAsImage(eles.get(i), filePath);
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
	private void attachFiles(String[] files) throws InterruptedException {
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
	private void insertPictures(String[] files) throws InterruptedException {
		if(files!=null) {
			UploadFilePage upFile = new UploadFilePage();
			eleActions.generateDynamic(ComposeActions.INSERT_IMAGE.getID()).click();
			upFile.insertFiles(files);
		}
	}

    /**
     * Enter email content
     * 
     * @param	content
     * 			email content
     * @throws InterruptedException 
     * 
     */
	private void enterContent(String[] content) throws InterruptedException {
		if(content!=null) {
			for (String item : content) {
				if (Email.isImage(item)) {
					DriverUtils.switchToDefaultFrame();
					insertPictures(new String[] {Email.getImageName(item)});
				}
				else {
					DriverUtils.switchToFrame("ifBdy");
					DriverUtils.wait(0.5);
					DriverUtils.getActions().sendKeys(txtBody.getElement(), item).build().perform();
				}
			}
			
			DriverUtils.switchToDefaultFrame();
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
		attachFiles(mail.getAttachFiles());
		enterContent(mail.getContent());
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
	public Boolean areInsertedPicturesDisplayed(String[] pictures) throws IOException, InterruptedException {
		if (pictures == null)
			return true;
		DriverUtils.switchToFrame("ifBdy");
		DriverUtils.maximizeBrowser();
		boolean result = true;
		elesInsertedPicture.waitForDisplayed(Constants.SHORT_TIME);
		for (int i = 0; i < pictures.length; i++) {
			File file = new File(Constants.DATA_FOLDER + ResourceHelper.SHARED_DATA.get().appName + "/" + pictures[i]);
			Element image = elesInsertedPicture.getWrapperElements().get(i);
			boolean isSame = ImageHelper.compareImages(image, file.getAbsolutePath());
			System.out.print("InsertedPicture (" + pictures[i] + "): " + isSame + " \n");
			if (!isSame) result = false;
		}
		
		DriverUtils.switchToDefaultFrame();
		return result;
	}
	
	 /**
     * Return a Boolean value to indicate whether information of email is correct
     *
     * @param	expectedEmail
     *			expected email
     *
     * @return  true|false
     * 			true: Email information matches with expected
     * 			false: Email information does not match with expected
	 * @throws InterruptedException 
	 * @throws IOException 
     * 
     */
	public Boolean isEmailInfoCorrect(Email expectedEmail) throws IOException, InterruptedException {
		String actualTo = getToList();
		String actualCCList = getCcList();
		String actualSubject = getMailSubject();
		String actualContent = getMailContent().replaceAll("\n", "");
		String actualAttachments = getAttachmentsName();
		
		System.out.print("To: " + actualTo + " vs " + expectedEmail.getTo() + " \n");
		System.out.print("CCList: " + actualCCList + " vs " + expectedEmail.getCcList() + " \n");
		System.out.print("Subject: " + actualSubject + " vs " + expectedEmail.getSubject() + " \n");
		System.out.print("MailContent: " + actualContent + " vs " + expectedEmail.getContentAsString().replaceAll("\n", "") + " \n");
		System.out.print("Attachments: " + actualAttachments + " vs " + expectedEmail.getAttachFilesAsString() + " \n");
		
		return 	areInsertedPicturesDisplayed(expectedEmail.getInsertedImages())
				&& StringUtils.equals(actualTo, expectedEmail.getTo())
				&& StringUtils.equals(actualCCList, getCcList())
				&& StringUtils.equals(actualSubject, expectedEmail.getSubject())
				&& StringUtils.equals(actualContent, expectedEmail.getContentAsString().replaceAll("\n", ""))
				&& StringUtils.equals(actualAttachments, expectedEmail.getAttachFilesAsString());
	}
	
}
