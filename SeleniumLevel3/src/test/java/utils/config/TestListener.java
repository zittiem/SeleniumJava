package utils.config;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.helper.Logger;
import utils.report.ExtentReportManager;
import utils.report.ExtentTestManager;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import driver.manager.DriverUtils;;

public class TestListener implements ITestListener {
	
	private static Map<String, ExtentTest> testSuite = new HashMap<String, ExtentTest>();
	
	public void onStart(ITestContext context) {
		System.out.println("*** Test Suite " + context.getName() + " started ***");
		// Handle Report
		if (!ExtentTestManager.isTestExisted(context.getName())) {
			ExtentTest tmpSuite = ExtentTestManager.startTest(context.getName(), null);
			testSuite.put(context.getName(), tmpSuite);
		}
	}

	public void onTestStart(ITestResult result) {
		System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));
		
		ExtentTestManager.startTest(result.getMethod().getMethodName(), testSuite.get(result.getTestContext().getName()));
		ExtentTestManager.getTest().assignCategory(result.getTestContext().getName());
		Logger.info(String.format("TEST CASE: %s.%s", result.getTestClass().getName(), result.getName()).replace("_",
				"_ "));
	}
	
	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentReportManager.getInstance().flush();
	}

	public void onTestSuccess(ITestResult result) {
		Logger.info("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
		ExtentTestManager.getTest().log(Status.PASS, "Test passed");
	}

	public void onTestFailure(ITestResult result) {
		Logger.info("*** Test execution " + result.getMethod().getMethodName() + " failed...");
		
		// capture screenshot
		String screenshotFileName = UUID.randomUUID().toString();
		String screenshotFilePath = "";
		try {
			screenshotFilePath = DriverUtils.takeScreenShot(screenshotFileName, ExtentReportManager.getScreenshotFolder());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// attach screenshots to report
		try {
				ExtentTestManager.getTest().fail(result.getThrowable().getMessage(),
							MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());
		} catch (IOException e) {
		Logger.info("An exception occured while taking screenshot " + e.getCause());
		}
	}

	public void onTestSkipped(ITestResult result) {
		Logger.info("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Logger.info("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}
}
