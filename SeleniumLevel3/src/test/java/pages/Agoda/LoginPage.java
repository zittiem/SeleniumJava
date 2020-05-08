package pages.Agoda;

import datatype.Agoda.Account;
import element.base.web.Element;
import element.wrapper.web.Button;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class LoginPage extends GeneralPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			LoginPage.class);
	
	// Static Elements
	protected Element eleLoginPopup = new Element(locator.getLocator("eleLoginPopup"));
	protected TextBox txtSigninEmail = new TextBox(locator.getLocator("txtSigninEmail"));
	protected TextBox txtSigninPassword = new TextBox(locator.getLocator("txtSigninPassword"));
	protected Button btnSignIn = new Button(locator.getLocator("btnSignIn"));
	
	// Method
	/**
     * Wait for page load completely. Time out is Constants.LONG_TIME.
     */
	public void waitForPageLoad() {
		eleLoginPopup.waitForDisplayed(Constants.LONG_TIME);
	}
	
    /**
     * Sign in to web page
     *
     * @param  account
     *         user login info (Account)
     *
     */
	public void signIn(Account account) {
		txtSigninEmail.enter(account.getEmail());
		txtSigninPassword.enter(account.getPassword());
		btnSignIn.click();
		eleLoginPopup.waitForNotDisplayed(Constants.SHORT_TIME);
	}
	
	// Verify
	/**
     * Return a Boolean value to indicate whether Login page is displayed or not
     *
     * @return  true|false
     * 			true: the Login page is displayed
     * 			false: the Login page is not displayed
     **/
	public boolean isPageDisplayed() {
		return eleLoginPopup.isDisplayed();
	}
}
