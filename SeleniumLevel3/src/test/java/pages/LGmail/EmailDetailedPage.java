package pages.LGmail;

import element.base.web.Element;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class EmailDetailedPage extends ComposeEmailPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			EmailDetailedPage.class);

	// Static Elements
	protected Element eleSubject = new Element(locator.getLocator("eleSubject"));
	protected Element eleSender = new Element(locator.getLocator("eleSender"));

	// Methods
	
	/**
     * Get email subject
     * 
     * @return	a string of email subject
     * 
     */
	@Override
	public String getMailSubject() {
		return eleSubject.getText();
	}
	
}
