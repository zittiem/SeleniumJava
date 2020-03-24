package utils.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeHelper {

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
