package utils.helper;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;

import driver.manager.DriverUtils;
import element.base.web.Element;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class ImageHelper {

	public static void saveImage(Element ele, String filePath) throws Exception {
		File src = ele.getElement().getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(filePath));
	}
	
	public static void saveAsImage(Element ele, String filePath) throws Exception {
		DriverUtils.getActions().contextClick(ele.getElement()).build().perform();
		DriverUtils.wait(1);
		Robot rb = new Robot();
		// Open Save As dialog
		rb.keyPress(KeyEvent.VK_SHIFT);
		rb.keyPress(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_SHIFT);
		
		// Paste file path
		DriverUtils.wait(1);
		StringSelection stringSelection = new StringSelection(filePath);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		
		// Save image
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		DriverUtils.wait(2);
	}

	public static boolean compareImages(String actualImage, String expectedImage) throws IOException {
		BufferedImage expImage = ImageIO.read(new File(expectedImage));
		BufferedImage actImage = ImageIO.read(new File(actualImage));
		ImageDiffer iDiffer = new ImageDiffer();
		ImageDiff iDiff = iDiffer.makeDiff(expImage, actImage);
		return !iDiff.hasDiff();
	}

	public static boolean compareImages(Element element, String expectedImage) throws IOException {
		BufferedImage expImage = ImageIO.read(new File(expectedImage));
		File src = element.getElement().getScreenshotAs(OutputType.FILE);
		BufferedImage actImage = ImageIO.read(src);
		ImageDiffer iDiffer = new ImageDiffer();
		ImageDiff iDiff = iDiffer.makeDiff(expImage, actImage);
		return !iDiff.hasDiff();
	}
}
