package pages.Agoda;

import java.util.List;

import datatype.Agoda.UserInfo;
import element.base.web.Element;
import element.wrapper.web.Button;
import element.wrapper.web.DropDown;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import tests.TestBase;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class SignInPopUp extends TestBase {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			SignInPopUp.class);
	
	// Static Elements
	protected TextBox txtEmail = new TextBox(locator.getLocator("txtEmail"));
	protected TextBox txtPassword = new TextBox(locator.getLocator("txtPassword"));
	protected Button btnSignIn = new Button(locator.getLocator("btnSignIn"));
	protected Element eleSignInPopUp = new Button(locator.getLocator("eleSignInPopUp"));
	
	// Methods
    /**
     * Sign in Agoda
     *
     * @param  email
     *         Email used to sign in Agoda
     *         
     * @param	password
     * 			Password used to sign in Agoda        
     *
     */
	public void signIn(UserInfo account) {
		txtEmail.waitForDisplayed(30);
		txtEmail.enter(account.getEmail());
		txtPassword.enter(account.getPassword());
		btnSignIn.click();
	}
	
	// Verify
	
    /**
     * Return a Boolean value to indicate whether Login popup appears
     *
	 * @return  true|false
	 * 			true: Login popup appears
	 * 			false: Login popup does not appear     
     *
     */

	public boolean isDisplayed() {
		eleSignInPopUp.waitForDisplayed(30);
		return eleSignInPopUp.isDisplayed();
	}

}
