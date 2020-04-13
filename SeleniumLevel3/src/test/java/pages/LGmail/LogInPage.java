package pages.LGmail;

import datatype.LGmail.UserInfo;
import element.wrapper.web.Button;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.CryptionHelper;
import utils.helper.ResourceHelper;

public class LogInPage {

	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			LogInPage.class);
	
	// Elements
	protected TextBox txtUserName = new TextBox(locator.getLocator("txtUserName"));
	protected TextBox txtPassword = new TextBox(locator.getLocator("txtPassword"));
	protected Button btnSignIn = new Button(locator.getLocator("btnSignIn"));

	// Methods
	private void enterPassword(String encryptedPassword) {
		if (!encryptedPassword.isEmpty()) {
			txtPassword.enter(CryptionHelper.decryptText(encryptedPassword));
		}
	}

	public void login(UserInfo user) {
		txtUserName.enter(user.getUserName());
		enterPassword(user.getPassword());
		btnSignIn.click();
	}
	
	public void login(String username, String password) {
		txtUserName.enter(username);
		txtPassword.enter(password);
		btnSignIn.click();
	}
}
