package utils.assertion;

import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.google.gson.Gson;

import driver.manager.DriverUtils;
import utils.helper.Logger;
import utils.report.ExtentReportManager;
import utils.report.ExtentTestManager;
import java.io.IOException;
import java.util.UUID;
import org.testng.asserts.IAssert;

public class SoftAssertion extends SoftAssert {
	@Override
	public void onAssertSuccess(IAssert<?> assertCommand) {
		Logger.passedAssertion("PASS");
	}

	@Override
	public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
		String _mess = "FAIL -";
		for (StackTraceElement ste : ex.getStackTrace()) {
			if (!ste.toString().toLowerCase().contains("assert")) {
				_mess += " Line# " + ste.getLineNumber() + " - " + ex.getMessage();
				break;
			}
		}
		Logger.failedAssertion(_mess);
		// capture screenshot
		String screenshotFileName = UUID.randomUUID().toString();
		String screenshotFilePath = "";
		try {
			screenshotFilePath = DriverUtils.takeScreenShot(screenshotFileName,
					ExtentReportManager.getScreenshotFolder());
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

	public void assertObjectEquals(Object objA, Object objB, String message)
	{
		assertEquals(new Gson().toJson(objA), new Gson().toJson(objB), message);
	}
	
	public void assertObjectEquals(Object objA, Object objB)
	{
		assertEquals(new Gson().toJson(objA), new Gson().toJson(objB), "");
	}
}