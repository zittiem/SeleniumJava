package utils.report;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.common.Common;

public class ExtentTestManager {
	static String workingDir = System.getProperty("user.dir");
	static String resultFileName = "ReportResults.html";

	static Map<String, ExtentTest> extentTestMap = new HashMap<String, ExtentTest>();
	static String outputFolder;
	static ExtentReports extent;

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get(String.valueOf(Thread.currentThread().getId()));
	}
	
	public static synchronized ExtentTest getTest(String key) {
		return (ExtentTest) extentTestMap.get(key);
	}

	public static synchronized boolean isTestExisted(String key) {
		return extentTestMap.containsKey(key);
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		return startTest(testName, desc, null);
	}
	
	public static synchronized ExtentTest startTest(String testName, String desc, ExtentTest parent) {
		try {
			if (ExtentManager.isReportCreated())
				extent = ExtentManager.getReporter();
			else {
				outputFolder = "test-output" + System.getProperty("file.separator") +"Report - ".concat(testName).concat(" - ").concat(Common.getNowTime("MM.dd.yyyy - HH.mm.ss"));
				String path = workingDir + System.getProperty("file.separator") + outputFolder + System.getProperty("file.separator") + resultFileName;
				extent = ExtentManager.getReporter(path);
			}

			ExtentTest test;
			if (parent != null) {
				test = parent.createNode(testName);
				extentTestMap.put(String.valueOf(Thread.currentThread().getId()), test);
			}
			else {
				test = extent.createTest(testName);
				extentTestMap.put(testName, test);
			}
			return test;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static synchronized String getScreenshotFolder() {
		String path = outputFolder + System.getProperty("file.separator") + "screenshots";
		File output = new File(path);
		if (!output.exists())
			output.mkdir();
		return path;
	}
	
	public static synchronized String getOutputFolder() {
		return outputFolder;
	}
}