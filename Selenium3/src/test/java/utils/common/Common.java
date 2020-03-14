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
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Common {

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
