package pages.LGmail;

import datatype.LGmail.Enums.MailTreeItem;
import datatype.LGmail.Enums.MessageToolbarItem;
import element.base.web.Element;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class MainPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			MainPage.class);
	
	// Static Elements
	protected Element eleMessageToolbar = new Element(locator.getLocator("eleMessageToolbar"));
	protected Element eleMailTreeItem = new Element(locator.getLocator("eleMailTreeItem"));
	protected Element eleMailSubject = new Element(locator.getLocator("eleMailSubject"));
	protected Element eleMailSubjectChild = new Element(locator.getLocator("eleMailSubjectChild"));
	protected Element eleNewEmailComingSignal = new Element(locator.getLocator("eleNewEmailComingSignal"));

	// Methods

	 /**
     * Select mail folder
     *
     * @param	option
     * 			It should be INBOX|DRAFTS|SENT_ITEMS|DELETED_ITEMS
     * 
     */
	public void selectMailTree(MailTreeItem option) {
		eleMailTreeItem.generateDynamic(option.getValue()).click();
	}

	 /**
     * Select message toolbar
     *
     * @param	option
     * 			It should be NEW|DELETE|MOVE|FILTER|VIEW
     * 
     */
	public void selectMessageToolbar(MessageToolbarItem option) {
		eleMessageToolbar.generateDynamic(option.getID()).waitForDisplayed(Constants.SHORT_TIME);
		eleMessageToolbar.click();
	}
	
	 /**
     * Open an email
     *
     * @param	folder
     * 			It should be INBOX|DRAFTS|SENT_ITEMS|DELETED_ITEMS
     * 
     * @param	subject
     * 			Email subject
     * 
     */
	public void openMail(MailTreeItem folder, String subject) {
		if(eleNewEmailComingSignal.isDisplayed(Constants.SLEEP_TIME)) {
			eleNewEmailComingSignal.waitForNotDisplayed(Constants.SHORT_TIME);
		}
		eleMailTreeItem.generateDynamic(folder.getValue()).click();
		eleMailSubject.generateDynamic(subject).waitForDisplayed(Constants.SHORT_TIME);
		eleMailSubject.doubleClick();
		if(eleMailSubjectChild.isDisplayed(Constants.SLEEP_TIME)) {
			eleMailSubjectChild.doubleClick();
		}
	}
	
	/**
     * Delete an email
     *
     * @param	folder
     * 			It should be INBOX|DRAFTS|SENT_ITEMS|DELETED_ITEMS
     * 
     * @param	subject
     * 			Email subject
     * 
     */
	public void deleteMail(MailTreeItem folder, String subject) {
		eleMailTreeItem.generateDynamic(folder.getValue()).click();
		eleMailSubject.generateDynamic(subject).waitForDisplayed(Constants.SHORT_TIME);
		eleMailSubject.click();
		if(eleMailSubjectChild.isDisplayed(Constants.SLEEP_TIME)) {
			eleMailSubjectChild.click();
		}
		selectMessageToolbar(MessageToolbarItem.DELETE);
	}

}
