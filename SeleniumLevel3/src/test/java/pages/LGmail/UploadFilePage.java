package pages.LGmail;

import java.io.File;
import org.openqa.selenium.By;
import driver.manager.DriverUtils;
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
	public void insertFiles(String files) {
		DriverUtils.switchToFrame("iFrameModalDlg");
		DriverUtils.switchToFrame(By.xpath("//iframe[@class='wh100']"));

		if (!files.isEmpty()) {
			File file = null;
			String[] _files = files.split(";");

			for (int i = 0; i < _files.length; i++) {
				file = new File(Constants.DATA_FOLDER + ResourceHelper.SHARED_DATA.get().appName + "/" + _files[i]);
				String filePath = file.getAbsolutePath();
				if (i != 0) {
					btnChooseMoreFiles.click();
				}
				btnChooseFiles.getWrapperButtons().get(i).waitForDisplayed(Constants.SHORT_TIME);
				btnChooseFiles.getWrapperButtons().get(i).sendKeys(filePath);
			}
		}
		btnInsert.click();
		
		DriverUtils.switchToDefaultFrame();
	}
}
