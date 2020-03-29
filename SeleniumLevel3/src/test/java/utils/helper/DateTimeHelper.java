package utils.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import utils.constants.Constants;

public class DateTimeHelper {

	public static String getNowTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static Date plusMonths(long months) {
		// Get current date
		Date currentDate = new Date();
		// convert date to local date time
		LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				.plusMonths(months);
		// Revert to Date
		Date finalDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return finalDate;
	}

	public static Date plusYears(long years) {
		// Get current date
		Date currentDate = new Date();
		// convert date to local date time
		LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				.plusYears(years);
		// Revert to Date
		Date finalDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return finalDate;
	}

	public static Date plusDays(long days) {

		// Get current date
		Date currentDate = new Date();
		// convert date to local date time
		LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				.plusDays(days);
		// Revert to Date
		Date finalDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return finalDate;
	}

	public static String getDateString(Date date, String format) {
		String pattern = format;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	public static String getDateString(Date date) {
		return getDateString(date, "dd/MM/yyyy EEE");
	}

	public static Date tomorrow() {
		return plusDays(1);
	}

	public static Date dateAfter(Date date, int days) {
		// convert date to local date time
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(days);
		// Revert to Date
		Date finalDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return finalDate;
	}

	public static Date getDate(String sDate, String format) {
		try {
			return new SimpleDateFormat(format).parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date getDate(String sDate) {
		return getDate(sDate, Constants.DATETIME_FORMAT);
	}

	public static Date generateDynamicDate(String sDate, String dateTimeFormat) {
		Date outDate = null;
		if (!sDate.isEmpty() && !sDate.equals("null")) {
			if (sDate.contains(":")) {
				String _unit = sDate.split(":")[0];
				long _number = Long.parseLong(sDate.split(":")[1]);
				if (_unit.equals("Days")) {
					outDate = plusDays(_number);
				} else if (_unit.equals("Months")) {
					outDate = plusMonths(_number);
				} else if (_unit.equals("Years")) {
					outDate = plusYears(_number);
				}
			} else
				outDate = getDate(sDate, dateTimeFormat);
		}
		return outDate;
	}
	
	public static String generateDynamicDateString(String sDate, String dateTimeFormat) {
		Date outDate = null;
		if (!sDate.isEmpty() && !sDate.equals("null")) {
			if (sDate.contains(":")) {
				String _unit = sDate.split(":")[0];
				long _number = Long.parseLong(sDate.split(":")[1]);
				if (_unit.equals("Days")) {
					outDate = plusDays(_number);
				} else if (_unit.equals("Months")) {
					outDate = plusMonths(_number);
				} else if (_unit.equals("Years")) {
					outDate = plusYears(_number);
				}
				return getDateString(outDate, dateTimeFormat);
			}
		}
		return sDate;
	}
}
