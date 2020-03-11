package utils.common;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.logigear.control.common.imp.Element;
import com.logigear.driver.manager.Driver;

public class Common {

	public static String getRemoteInfo() {
		StringBuilder str = new StringBuilder();
		try {
			CommandExecutor ce = ((RemoteWebDriver) Driver.getWebDriver()).getCommandExecutor();
			URL url = ((HttpCommandExecutor) ce).getAddressOfRemoteServer();

			InetAddress host = InetAddress.getByName(url.getHost());
			Capabilities caps = ((RemoteWebDriver) Driver.getWebDriver()).getCapabilities();

			str.append("<br>Execution Information:");
			str.append("<br>- Machine IP: <b>" + host.getHostAddress() + " - " + host.getHostName() + "</b>");
			str.append("<br>- Browser: <b>" + caps.getBrowserName().toUpperCase());
			str.append(" - " + caps.getVersion() + "</b>");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	public static String convertImageToURI(String imagePath) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		BufferedImage img;
		File image = new File(imagePath);
		try {
			img = ImageIO.read(image);
			ByteArrayOutputStream convert = new ByteArrayOutputStream();
			ImageIO.write(img, "png", convert);
			String data = DatatypeConverter.printBase64Binary(convert.toByteArray());
			return data;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static String randomString() {
		String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String number = "0123456789";
		String string = "";
		Random rd = new Random();
		int indx = rd.nextInt(20);

		for (int i = 0; i < indx; i++) {
			int kt = rd.nextInt(2);
			if (kt == 0)// string will have letter(s)
			{
				int lt = rd.nextInt(52);
				string += Character.toString(letter.charAt(lt));
			} else // password will have number(s)
			{
				int lt = rd.nextInt(9);
				string += Character.toString(number.charAt(lt));
			}
		}
		return string;
	}

	public static String screenshotURI(String imagePath) {
		String randomPopUpId = "id" + UUID.randomUUID().toString();
		String randomButtonId = randomPopUpId + "button";
		String imageString = "data:image/png;base64," + convertImageToURI(imagePath);
		String htmlScript = "<script>$(document).ready(function(){$( \"#" + randomPopUpId
				+ "\" ).dialog({ autoOpen: false });$( \"#" + randomPopUpId
				+ "\" ).dialog({width:1000},{height:700});$( \"#" + randomButtonId + "\" ).click(function() {$( \"#"
				+ randomPopUpId + "\" ).dialog( \"open\" );});});</script></br><img id=\"" + randomButtonId
				+ "\" src=\"" + imageString
				+ "\" style=\"border: 4px solid #f6f7fa;width: 150px;cursor: zoom-in;display: block;margin-top: 15px;\"/></br><div style=\"width: 50%; margin: 0 auto;\" id=\""
				+ randomPopUpId + "\" > <a href=\"#" + randomPopUpId
				+ "\"  class=\"ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right\"></a><img style=\"width:800px;height:600;\"  src=\""
				+ imageString + "\"/></div>";
		return htmlScript;
	}

	public static String getNowTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getRandomString(String prefix) {
		return prefix.concat(new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date()));
	}

	public static void switchTab(String url, boolean isURLFull) {
		ArrayList<String> tabs = new ArrayList<String>(Driver.getWindowHandles());

		for (int i = 1; i < tabs.size(); i++) {
			Driver.switchTo(tabs.get(i));
			String fullURL = Driver.getCurrentUrl();
			if (!isURLFull & fullURL.toLowerCase().contains(url.toLowerCase())) {
				break;
			} else if (isURLFull & url.equals(fullURL)) {
				break;
			}
		}
	}

	public static void switchToMain(boolean doesCurrentBrowserClose) {
		ArrayList<String> tabs = new ArrayList<String>(Driver.getWindowHandles());
		Driver.close();
		Driver.switchTo(tabs.get(0));
	}

	public static void writeTextToFile(String fileName, String content) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void waitPageRefresh(Element eleLoading, int timeout) {
		eleLoading.waitForPresent(timeout);
		if (eleLoading.isDisplayed())
			eleLoading.waitForElementNotPresent(timeout);
	}

	public static void waitForPageRefresh(Element eleLoading, int timeout) {
		eleLoading.waitForPresent(timeout);
		if (eleLoading.isDisplayed())
			eleLoading.waitForElementNotPresent(timeout);
	}

	private static Alert switchToAlert() {
		new WebDriverWait(Driver.getWebDriver(), Constants.LOADING_TIME).until(ExpectedConditions.alertIsPresent());
		return Driver.switchTo().alert();
	}

	private static boolean isAlertPresent() {
		try {
			Driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	public static void acceptAlert() {
		if (isAlertPresent() == true) {
			switchToAlert().accept();
		}
	}
	
	public static boolean getRandomBoolean() {
	    Random random = new Random();
	    return random.nextBoolean();
	}
	
	public static int getRandomNumber() {
	    Random random = new Random();
	    return random.nextInt();
	}
	
	public static int getRandomNumber(int upperRange) {
	    Random random = new Random();
	    return random.nextInt(upperRange);
	}
	
	public static int getRandomNumber(int min, int max) {
	    Random r = new Random();
	    return r.nextInt((max - min) + 1) + min;
	}
}
