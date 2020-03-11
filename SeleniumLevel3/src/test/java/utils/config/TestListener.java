package utils.config;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.logigear.driver.manager.Driver;

import utils.common.Common;
import utils.helper.Logger;
import utils.helper.PropertiesHelper;
import utils.integration.Bug;
import utils.integration.jira.JiraIssueReporterHandler;
import utils.report.ExtentManager;
import utils.report.ExtentTestManager;

public class TestListener implements ITestListener {
	
	private static Map<String, ExtentTest> testSuite = new HashMap<String, ExtentTest>();
	private JiraIssueReporterHandler jiraHandler;
	private boolean autoReportBug;

	public void onTestFailure(ITestResult result) {
		// Get screenshot
		String screenshotFileName = UUID.randomUUID().toString();
		String path = Driver.captureScreenshot(screenshotFileName, ExtentTestManager.getScreenshotFolder());

		String script = Common.screenshotURI(path);
		Reporter.log(script);

		// Get Description
		List<String> logs = Logger.getCurrentLogs();
		String description = "";
		for (String log : logs) 
			description += log + "\n";

		// Handle for ExtentReports
		String executionInfo = Common.getRemoteInfo();
		try {
			ExtentTest detailFailed = ExtentTestManager.getTest();

			if (result.getThrowable().toString().contains("Assert")) {
				detailFailed.fail("<b>This test case check failed.</b>" + executionInfo);
				detailFailed.fail(result.getThrowable().getMessage());
			} else {
				detailFailed.error("<b>This test case failed by error.</b>" + executionInfo);
				detailFailed.error(result.getThrowable());
			}

			detailFailed.addScreenCaptureFromPath("screenshots\\" + screenshotFileName + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (autoReportBug) {
			boolean hasBug = result.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(Bug.class);
			if (hasBug) {
				System.out.println("Updating the bug status as Reopened");
				Annotation[] annotations = result.getMethod().getConstructorOrMethod().getMethod()
						.getDeclaredAnnotations();
				String bugID = "";
				for (Annotation annotation : annotations) {
					if (annotation.annotationType() == Bug.class) {
						Bug bug = (Bug) annotation;
						bugID = bug.value();
						jiraHandler.maskBugStatus(bug.value(), "Reopened");
					}
				}
				jiraHandler.updateIssue(bugID, description, path);
			} else {
				jiraHandler.createNewBug(result.getMethod().getDescription(), description, path,
						result.getMethod().getTestClass().getName(), result.getMethod().getMethodName());
			}
		}
	}

	public void onTestStart(ITestResult result) {
		Object value = result.getTestContext().getAttribute("autoLogBug");
		autoReportBug = value == null ? false : (boolean) value;
		
		ExtentTestManager.startTest(result.getMethod().getMethodName(), "",
				testSuite.get(result.getTestContext().getName()));
		ExtentTestManager.getTest().assignCategory(result.getTestContext().getName());
		Logger.info(String.format("TEST CASE: %s.%s", result.getTestClass().getName(), result.getName()).replace("_",
				"_ "));
	}

	public void onTestSuccess(ITestResult result) {
		ExtentTest detailInfo = ExtentTestManager.getTest();
		String executionInfo = Common.getRemoteInfo();
		detailInfo.pass("Test passed." + executionInfo);
		
		if (!autoReportBug)
			return;

		boolean hasBug = result.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(Bug.class);
		if (hasBug) {
			System.out.println("Updating the bug status as Closed");
			Annotation[] annotations = result.getMethod().getConstructorOrMethod().getMethod().getDeclaredAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType() == Bug.class) {
					Bug bug = (Bug) annotation;
					jiraHandler.maskBugStatus(bug.value(), "Closed");
				}
			}
		}
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		if (!autoReportBug)
			return;
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		if (!autoReportBug)
			return;
	}

	public void onStart(ITestContext context) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		// Setting for JIRA
		String jiraURL = PropertiesHelper.getPropValue("jira.url");
		String jiraUS = PropertiesHelper.getPropValue("jira.user.name");
		String jiraPWD = PropertiesHelper.getPropValue("jira.user.password");
		String jiraPrjKey = PropertiesHelper.getPropValue("jira.project.key");
		jiraHandler = new JiraIssueReporterHandler(jiraURL, jiraUS, jiraPWD, jiraPrjKey);
		
		// Handle Report
		if (!ExtentTestManager.isTestExisted(context.getName())) {
			ExtentTest tmpSuite = ExtentTestManager.startTest(context.getName(), "", null);
			testSuite.put(context.getName(), tmpSuite);
		}
		System.setProperty("org.uncommons.reportng.escape-output", "false");
	}

	public void onFinish(ITestContext context) {
		try {
			ExtentManager.getReporter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
