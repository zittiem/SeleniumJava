package pages.LGmail;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import datatype.LGmail.Email;
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
	
	// Static Elements
	protected Element eleMessageToolbar = new Element(locator.getLocator("eleMessageToolbar"));
	protected Element eleMailTreeItem = new Element(locator.getLocator("eleMailTreeItem"));
	protected Element eleMailSubject = new Element(locator.getLocator("eleMailSubject"));
	protected Element eleMailSubjectChild = new Element(locator.getLocator("eleMailSubjectChild"));
	protected Element eleNewEmailComingSignal = new Element(locator.getLocator("eleNewEmailComingSignal"));
	protected String mainWindow = DriverUtils.getWindowHandle();
	protected String composeWindow = null;

	// Methods

	public void selectMailTree(MailTreeItem option) {
		eleMailTreeItem.generateDynamic(option.getValue()).click();
	}

	public void selectMessageToolbar(MessageToolbarItem option) {
		eleMessageToolbar.generateDynamic(option.getID()).click();
	}
	
	/*public String openUntitledMessageWindow() throws InterruptedException {
		Set<String> listWindowsBefore = DriverUtils.getDriver().getWindowHandles();
		
		selectMessageToolbar(MessageToolbarItem.NEW);

		Set<String> listWindows = DriverUtils.getDriver().getWindowHandles();
		Iterator<String> ite=listWindows.iterator();
		
		System.out.print(listWindowsBefore.size() + ":" + listWindows.size() + "\n" );
		while(ite.hasNext())
		{
			String popupHandle1=ite.toString();
			String popupHandle2=ite.next().toString();
			
			DriverUtils.switchTo(popupHandle1);
			System.out.print("\n popupHandle1:" + DriverUtils.getTitle() + ":" + popupHandle1);
			DriverUtils.switchTo(popupHandle2);
			System.out.print("\n popupHandle2:" + DriverUtils.getTitle() + ":" + popupHandle2);
			listWindowsBefore = DriverUtils.getDriver().getWindowHandles();
			System.out.print("\n listWindowsBeforeSize:" + listWindowsBefore.size());
			DriverUtils.switchToLatest();
			System.out.print("\n popupHandleLatest:" + DriverUtils.getTitle() + ":" + DriverUtils.getWindowHandles().get(listWindowsBefore.size()-1));
			
			return popupHandle2;
		}
		return null;
	}*/

	public void selectAction(ComposeActions action) {
		ComposeEmailPage cp = new ComposeEmailPage();
		cp.selectAction(action);
	}
	
	public void openAMail(MailTreeItem folder, String subject) throws InterruptedException {
		if(eleNewEmailComingSignal.isDisplayed(Constants.SLEEP_TIME)) {
			eleNewEmailComingSignal.waitForNotDisplayed(Constants.SHORT_TIME);
		}
		eleMailTreeItem.generateDynamic(folder.getValue()).click();
		eleMailSubject.generateDynamic(subject).waitForDisplayed(Constants.SHORT_TIME);
		eleMailSubject.generateDynamic(subject).doubleClick();
		if(eleMailSubjectChild.isDisplayed(Constants.SLEEP_TIME)) {
			eleMailSubjectChild.doubleClick();
		}
	}

}
