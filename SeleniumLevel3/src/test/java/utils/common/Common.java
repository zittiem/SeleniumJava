package utils.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

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

	public static String getRandomString(String prefix) {
		return prefix.concat(new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date()));
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

	public static String getNowTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static Date plusDays(long days) {

		// Get current date
		Date currentDate = new Date();
		// convert date to localdatetime
		LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				.plusDays(days);
		// Revert to Date
		Date finalDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return finalDate;
	}

	public static String getDate(Date date, String format) {
		String pattern = format;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	public static String getDate(Date date) {
		return getDate(date, "dd/MM/yyyy EEE");
	}
}
