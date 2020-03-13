package utils.helper;

import org.testng.Reporter;

public class Logger {

	public static void info(String message) {
		Reporter.log("<b>INFO: </b>" + message);
	}

	public static void bug(String bugId, String bugDesc) {
		String bugInfo = String.format("The bug %s-%s is added", bugId, bugDesc);
		String msg = "<a target=\"_blank\" href=\"" + bugDesc
				+ "\" style=\"color:#DF0101;font-size:14px;word-break:break-word;\">" + bugInfo + "</a>";
		Reporter.log(msg);
	}
	
	public static void warning(String message) {
		message = "<b style=\"color: darkorange;word-break:break-word;\"><i>WARNING: </i>" + message + "</b>";
		Reporter.log(message);
	}
	
	public static void verify(String message) {
		message = "<b style=\"color: blue;word-break:break-word;\"><i style=\"color: #00af00\">VERIFY POINT: </i>" + message + "</b>";
		Reporter.log(message);
	}
}
