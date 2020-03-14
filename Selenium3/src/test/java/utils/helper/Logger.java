package utils.helper;

import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;

import com.aventstack.extentreports.Status;

import utils.report.ExtentTestManager;

public class Logger {
	private static String sMethodName;
	private static String sClasName;
	private static List<String> currentLogs = new ArrayList<String>();

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(Logger.class);

	public static void info(String message) {
		saveLog(message);
		log.info(message);
		Reporter.log("<b>INFO: </b>" + message);
		ExtentTestManager.getTest().log(Status.INFO, "<span style=word-break:break-word>" + message + "</span>");
	}

	public static void bug(String bugId, String bugLink) {
		String bugInfo = String.format("The bug %s-%s is added", bugId, bugLink);
		log.error(bugInfo);
		saveLog(bugInfo);
		String msg = "<a target=\"_blank\" href=\"" + bugLink
				+ "\" style=\"color:#DF0101;font-size:14px;word-break:break-word;\">" + bugInfo + "</a>";
		Reporter.log(msg);
		ExtentTestManager.getTest().log(Status.WARNING, msg);
	}
	
	public static void warning(String message) {
		log.info("WARNING: " + message);
		saveLog("WARNING: " + message);
		message = "<b style=\"color: darkorange;word-break:break-word;\"><i>WARNING: </i>" + message + "</b>";
		Reporter.log(message);
		ExtentTestManager.getTest().log(Status.WARNING, message);
	}
	
	public static void verify(String message) {
		log.info("VERIFY POINT: " + message);
		saveLog("VERIFY POINT: " + message);
		message = "<b style=\"color: blue;word-break:break-word;\"><i style=\"color: #00af00\">VERIFY POINT: </i>" + message + "</b>";
		Reporter.log(message);
		ExtentTestManager.getTest().log(Status.INFO, message);
	}
	
	private static void saveLog(String message) {
		String currentMethod = Thread.currentThread().getStackTrace()[3]
				.getMethodName();
		String currentClass = Thread.currentThread().getStackTrace()[3]
				.getClassName();
		if (!currentMethod.equals(sMethodName)
				|| (currentMethod.equals(sMethodName) && !currentClass
						.equals(sClasName))) {
			currentLogs.clear();
		}
		sMethodName = currentMethod;
		sClasName = currentClass;
		currentLogs.add(message);
	}
	
	public static List<String> getCurrentLogs() {
		return currentLogs;
	}
}
