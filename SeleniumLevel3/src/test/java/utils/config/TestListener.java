package utils.config;
import java.io.IOException;
import java.util.UUID;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.helper.Logger;
import utils.report.ExtentReportManager;
import utils.report.ExtentTestManager;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import driver.manager.DriverUtils;

public class TestListener implements ITestListener {

	public void onStart(ITestContext context) {
		System.out.println("*** Test Suite " + context.getName() + " started ***");
	}

	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentTestManager.endTest();
		ExtentReportManager.getInstance().flush();
	}

	public void onTestStart(ITestResult result) {
		System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));
		ExtentTestManager.startTest(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
		ExtentTestManager.getTest().log(Status.PASS, "Test passed");
	}

	public void onTestFailure(ITestResult result) {
		Logger.info("*** Test execution " + result.getMethod().getMethodName() + " failed...");
		Logger.info((result.getMethod().getMethodName() + " failed!"));
		
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
				ExtentTestManager.getTest().fail("Screenshot",
							MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());
		} catch (IOException e) {
		Logger.info("An exception occured while taking screenshot " + e.getCause());
		}
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}
}
