package pages.LGmail;

import java.util.Set;

import datatype.LGmail.EmailInfo;
import datatype.LGmail.Enums.ComposeActions;
import datatype.LGmail.Enums.MailTreeItem;
import datatype.LGmail.Enums.MessageToolbarItem;
import driver.manager.DriverUtils;
import element.base.web.Element;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class MainPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			MainPage.class);
	protected Element eleMessageToolbar = new Element(locator.getLocator("eleMessageToolbar"));
	protected Element eleMailTreeItem = new Element(locator.getLocator("eleMailTreeItem"));
	protected Element eleMailSubject = new Element(locator.getLocator("eleMailSubject"));

	protected String mainWindow = DriverUtils.getWindowHandle();
	protected String composeWindow = null;

	// Methods
	private String getNewWindow(String title) {
		Set<String> listWindows = DriverUtils.getDriver().getWindowHandles();
		for (String handle : listWindows) {
			String curTitle = DriverUtils.getDriver().switchTo().window(handle).getTitle();
			if (curTitle.contains(title)) {
				return handle;
			}
		}
		return null;
	}

	public void selectMailTree(MailTreeItem option) {
		eleMailTreeItem.generateDynamic(option.getValue()).click();
	}

	public void selectMessageToolbar(MessageToolbarItem option) {
		eleMessageToolbar.generateDynamic(option.getID());
	}

	public void composeMewMail(EmailInfo mail) throws InterruptedException {

		eleMessageToolbar.generateDynamic(MessageToolbarItem.NEW.getID()).click();
		// Wait 2s to make sure compose page display before getting getWindowHandles.
		Thread.sleep(Constants.LOADING_TIME);
		composeWindow = getNewWindow("Untitled Message");
		DriverUtils.getDriver().switchTo().window(composeWindow);
		ComposeEmailPage cp = new ComposeEmailPage();
		cp.composeNewMail(mail);
	}

	public void selectAction(ComposeActions action) {
		ComposeEmailPage cp = new ComposeEmailPage();
		cp.selectAction(action);
	}

	public void closeComposeNewMail() {
		DriverUtils.getDriver().switchTo().window(composeWindow);
		DriverUtils.getDriver().close();
		DriverUtils.getDriver().switchTo().window(mainWindow);
	}

	// Verify
	public EmailInfo getMailInfo(MailTreeItem folder, String subject) throws InterruptedException {
		EmailInfo curEmai = new EmailInfo();
		eleMailTreeItem.generateDynamic(folder.getValue()).click();
		eleMailSubject.generateDynamic(subject).doubleClick();
		// Wait 2s to make sure compose page display before getting getWindowHandles.
		Thread.sleep(Constants.LOADING_TIME);
		composeWindow = getNewWindow(subject);
		DriverUtils.getDriver().switchTo().window(composeWindow);
		ComposeEmailPage cp = new ComposeEmailPage();
		curEmai = cp.getMailInfo();
		return curEmai;
	}
}
