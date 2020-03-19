package utils.report;

import java.util.HashMap;
import java.util.Map;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
	static Map<String, ExtentTest> extentTestMap = new HashMap<String, ExtentTest>();
	static ExtentReports extent = ExtentReportManager.getInstance();

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get(String.valueOf(Thread.currentThread().getId()));
	}

	public static synchronized void endTest() {
		extent.flush();
	}
	
	public static synchronized ExtentTest startTest(String testName, ExtentTest parent) {
		try {
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
	
	public static synchronized boolean isTestExisted(String key) {
		return extentTestMap.containsKey(key);
	}
}
