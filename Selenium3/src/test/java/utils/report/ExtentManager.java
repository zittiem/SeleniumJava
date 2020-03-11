package utils.report;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {

	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter(String outputResult) throws Exception {
		if (extent == null) {
			
			String[] path = outputResult.split("\\" + System.getProperty("file.separator"));
			//for (int i = aStrings.length - 3; i <= aStrings.length - 1; i++) {
			File output = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + path[path.length-3] + System.getProperty("file.separator") + path[path.length-2]);
			output.mkdir();
			//}
			if (outputResult == null || outputResult.trim().length() == 0)
				throw new Exception("Output folder result is required");
			ExtentHtmlReporter htmlReport = new ExtentHtmlReporter(outputResult);
			htmlReport.setAppendExisting(true);
			// htmlReport.loadXMLConfig("config.xml");
			extent = new ExtentReports();
			extent.attachReporter(htmlReport);
		}
		return extent;
	}

	public synchronized static ExtentReports getReporter() throws Exception {
		try {
			return getReporter("");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static boolean isReportCreated() {
		return (extent != null) ? true : false;
	}
}
