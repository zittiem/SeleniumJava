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
	protected TextBox txtUserName = new TextBox(locator.getLocator("txtUserName"));
	protected TextBox txtPassword = new TextBox(locator.getLocator("txtPassword"));
	protected Button btnSignIn = new Button(locator.getLocator("btnSignIn"));

	private void enterPassword(String encryptedPassword) {
		if (!encryptedPassword.isEmpty()) {
			txtPassword.enter(CryptionHelper.decryptText(encryptedPassword));
		}
	}

	public void enterLogIn(UserInfo user) {
		txtUserName.enter(user.getUserName());
		enterPassword(user.getPassword());
	}

	public void login(UserInfo user) {
		enterLogIn(user);
		btnSignIn.click();
	}
}
