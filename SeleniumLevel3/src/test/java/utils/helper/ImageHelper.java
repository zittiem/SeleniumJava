package utils.helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;
import element.base.web.Element;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class ImageHelper {

	public static void saveImage(Element ele, String filePath, String fileName, String fileExtension) throws Exception {
		File src = ele.getElement().getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(filePath + fileName + "." + fileExtension));
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
