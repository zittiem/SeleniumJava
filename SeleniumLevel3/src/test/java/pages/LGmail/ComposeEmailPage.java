package pages.LGmail;

import java.util.List;

import org.jsoup.select.Elements;
import org.openqa.selenium.By;

import datatype.LGmail.Email;
import datatype.LGmail.Enums.ComposeActions;
import datatype.LGmail.Enums.MailTreeItem;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.setting.FindBy;
import element.wrapper.web.TextBox;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class ComposeEmailPage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			ComposeEmailPage.class);

	protected Element eleActions = new Element(locator.getLocator("eleActions"));
	protected TextBox txtTo = new TextBox(locator.getLocator("txtTo"));
	protected TextBox txtCC = new TextBox(locator.getLocator("txtCC"));
	protected TextBox txtSubject = new TextBox(locator.getLocator("txtSubject"));
	protected TextBox txtBody = new TextBox(locator.getLocator("txtBody"));
	protected Element eleAttachments = new Element(locator.getLocator("eleAttachments"));

	// Methods
	private void attachFiles(String files) throws InterruptedException {

		UploadFilePage upFile = new UploadFilePage();
		eleActions.generateDynamic(ComposeActions.ATTACH_FILE.getID()).click();
		Thread.sleep(Constants.LOADING_TIME);
		DriverUtils.getDriver().switchTo().frame("iFrameModalDlg");
		DriverUtils.switchToFrame(By.xpath("//iframe[@class='wh100']"));
		upFile.insertFiles(files);
		DriverUtils.getDriver().switchTo().defaultContent();
	}

	public String getAttachmentsName() {
		List<Element> eleAtt = eleAttachments.getWrapperElements();
		System.out.print("Attachments: " + eleAtt.size());
		String attNam = "";
		for (Element e : eleAtt) {
			attNam += e.getAttribute("_attname") + ";";
		}
		return attNam.substring(0, attNam.length() - 1);
	}

	public String getMailContent() {
		String curContent = null;
		DriverUtils.getDriver().switchTo().frame("ifBdy");
		curContent = txtBody.getAttribute("innerText").trim();
		DriverUtils.getDriver().switchTo().defaultContent();
		return curContent;
	}

	private void enterContent(String content) {
		DriverUtils.getDriver().switchTo().frame("ifBdy");
		txtBody.enter(content);
		DriverUtils.getDriver().switchTo().defaultContent();
	}

	public void composeNewMail(Email mail) throws InterruptedException {
		txtTo.enter(mail.getTo());
		txtCC.enter(mail.getCcList());
		txtSubject.enter(mail.getSubject());
		attachFiles(mail.getAttachedFiles());
		enterContent(mail.getContent());
	}

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

	public Email getMailInfo() throws InterruptedException {
		Email curEmail = new Email();
		curEmail.setTo(txtTo.getChildElement(FindBy.xpath, "//span[@title]").getAttribute("title"));
		curEmail.setCcList(getCCList());
		curEmail.setSubject(txtSubject.getAttribute("value"));
		curEmail.setAttachedFiles(getAttachmentsName());
		curEmail.setContent(getMailContent());
		return curEmail;
	}

	public void selectAction(ComposeActions action) {
		eleActions.generateDynamic(action.getID()).click();
	}
	
	
}
