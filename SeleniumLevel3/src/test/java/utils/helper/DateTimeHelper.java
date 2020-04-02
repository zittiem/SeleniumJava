package utils.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;

import datatype.Agoda.Enums.WeekDays;

public class DateTimeHelper {

	public static String getNowTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static Date plusMonths(long months) {
		// Get current date
		Date currentDate = new Date();
		return plusMonths(currentDate, months);
	}

	public static Date plusMonths(Date date, long months) {
		// convert date to local date time
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				.plusMonths(months);
		// Revert to Date
		Date finalDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return finalDate;
	}

	public static Date plusYears(long years) {
		// Get current date
		Date currentDate = new Date();
		return plusYears(currentDate, years);
	}

	public static Date plusYears(Date date, long years) {
		// convert date to local date time
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				.plusYears(years);
		// Revert to Date
		Date finalDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return finalDate;
	}

	public static Date plusDays(long days) {
		// Get current date
		Date currentDate = new Date();
		return plusDays(currentDate, days);
	}

	public static Date plusDays(Date date, long days) {
		// convert date to local date time
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(days);
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
		return getDate(sDate, "dd/MM/yyyy EEE");
	}

	public static Date mapSDate(String sDate) {
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
				outDate = getDate(sDate);
		}
		return outDate;
	}

	public static Date mapDate(String sDate) {
		Date outDate = null;
		if (!sDate.isEmpty() && !sDate.equals("null")) {
			outDate = new Date();
			int unitIndex = 0;
			int numIndex = 1;
			if (sDate.contains(":")) {
				if (sDate.split(":").length == 3) {
					outDate = calcNextDayOfWeek(new Date(), sDate.split(":")[0]);
					unitIndex = 1;
					numIndex = 2;
				}
				String _unit = sDate.split(":")[unitIndex];
				long _number = Long.parseLong(sDate.split(":")[numIndex]);
				if (_unit.equals("Days")) {
					outDate = plusDays(outDate, _number);
				} else if (_unit.equals("Months")) {
					outDate = plusMonths(outDate, _number);
				} else if (_unit.equals("Years")) {
					outDate = plusYears(outDate, _number);
				}
			} else
				outDate = getDate(sDate);
		}
		return outDate;
	}

	public static Date calcNextDayOfWeek(Date sDate, String dayOfWeek) {
		LocalDateTime localDateTime = sDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				.with(TemporalAdjusters.next(
						DayOfWeek.valueOf(WeekDays.valueOf(dayOfWeek.toUpperCase()).getFullName().toUpperCase())));
		Date finalDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return finalDate;
	}

}
