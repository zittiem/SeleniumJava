package pages.LGmail;

import java.io.File;

import element.wrapper.web.Button;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class UploadFilePage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			UploadFilePage.class);
	protected Button btnChooseFiles = new Button(locator.getLocator("btnChooseFiles"));
	protected Button btnChooseMoreFiles = new Button(locator.getLocator("btnChooseMoreFiles"));
	protected Button btnInsert = new Button(locator.getLocator("btnInsert"));
	protected Button btnCancel = new Button(locator.getLocator("btnCancel"));

	public void showInfo() {
		System.out.println(locator.getLocator("btnChooseFiles"));
		System.out.println(locator.getLocator("btnChooseMoreFiles"));
		System.out.println(locator.getLocator("btnInsert"));
		System.out.println(locator.getLocator("btnCancel"));

	}

	public void insertFile(String file) {
		if (!file.isEmpty()) {
			btnChooseFiles.generateDynamic(1).sendKeys(file);
		}
		btnInsert.click();
	}

	public void insertFiles(String files) {
		if (!files.isEmpty()) {
			File file = null;
			String[] _files = files.split(";");

			for (int i = 0; i < _files.length; i++) {
				file = new File(Constants.DATA_FOLDER + ResourceHelper.SHARED_DATA.get().appName + "/" + _files[i]);
				String filePath = file.getAbsolutePath();
				if (i != 0) {
					btnChooseMoreFiles.click();
				}
				System.out.println(filePath);
				btnChooseFiles.getWrapperButtons().get(i).sendKeys(filePath);
			}
		}
		btnInsert.click();
	}
}
