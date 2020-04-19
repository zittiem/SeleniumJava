package pages.LGmail;

import java.io.File;
import driver.manager.DriverUtils;
import element.base.web.Element;
import element.wrapper.web.Button;
import helper.LocatorHelper;
import utils.constant.Constants;
import utils.helper.ResourceHelper;

public class UploadFilePage {
	LocatorHelper locator = new LocatorHelper(Constants.LOCATOR_FOLDER_PATH + ResourceHelper.SHARED_DATA.get().appName,
			UploadFilePage.class);
	
	// Static element
	protected Button btnChooseFiles = new Button(locator.getLocator("btnChooseFiles"));
	protected Button btnChooseMoreFiles = new Button(locator.getLocator("btnChooseMoreFiles"));
	protected Button btnInsert = new Button(locator.getLocator("btnInsert"));
	protected Button btnCancel = new Button(locator.getLocator("btnCancel"));
	protected Element ifmInsertFiles = new Element(locator.getLocator("ifmInsertFiles"));
	
	// Method

	 /**
     * Insert a single file
     *
     * @param	file
     * 			file path of the insert file
     * 
     */
	public void insertFile(String file) {
		if (!file.isEmpty()) {
			btnChooseFiles.generateDynamic(1).sendKeys(file);
		}
		btnInsert.click();
	}

	 /**
     * Insert multiple files
     *
     * @param	files
     * 			List of files that need to be inserted. Each file is separated by ";"
     * 
     */
	public void insertFiles(String[] files) {
		DriverUtils.switchToFrame("iFrameModalDlg");
		ifmInsertFiles.waitForDisplayed(Constants.SHORT_TIME);
		DriverUtils.switchToFrame(ifmInsertFiles.getElement());

		if (files != null) {
			File file = null;
			for (int i = 0; i < files.length; i++) {
				file = new File(Constants.DATA_FOLDER + ResourceHelper.SHARED_DATA.get().appName + "/" + files[i]);
				String filePath = file.getAbsolutePath();
				if (i != 0) {
					btnChooseMoreFiles.click();
				}
				Button element = btnChooseFiles.getWrapperButtons().get(i);
				element.waitForDisplayed(Constants.SHORT_TIME);
				element.sendKeys(filePath);
			}
		}
		btnInsert.click();
		
		DriverUtils.switchToDefaultFrame();
	}
}
