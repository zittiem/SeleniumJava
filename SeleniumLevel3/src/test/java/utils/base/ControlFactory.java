package utils.base;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class ControlFactory {
	
	private static Logger oLog = Logger.getLogger(ControlFactory.class);
	
	////////////////////////////////////////////////////////////////////////
	//
	// Create a new control with absolute locator that is got from JS file 
	// @Parameters:
	//	 .OType is Class of a created control
	//   .sName is name of an Element in a page that's stored in Json file. 
	//	 		It must have format "PageName.ControlName", Ex:"LoginPage.OKbtn"	
	//
	////////////////////////////////////////////////////////////////////////
	//@SuppressWarnings("unchecked")
	public static <T> T $(Class<T> oType, String sControl) {
		
		oLog.debug(String.format("Create %s control ", sControl));
		
		String arr[] = sControl.split(Pattern.quote("."));
		if (arr.length!=2) oLog.debug(String.format("%s control name is invalid syntax [page.control]", sControl));
		else  return $(oType,arr[1],arr[0]);
	
		return null;
	}	
	
	////////////////////////////////////////////////////////////////////////
	//
	//Create a new control with absolute locator that is got from JS file 
	//@Parameters:
	//	 .OType is Class of a created control
	//   .sControl is name of an Element that stores information of a control, in Json file
	//   .sPage is sPage.json file to store locators of the page
	//
	////////////////////////////////////////////////////////////////////////
	//@SuppressWarnings("unchecked")
	public static <T> T $(Class<T> oType, String sControl, String sPage ) {
		
		oLog.debug(String.format("Create %s control in %s page", sControl, sPage));
		Control oCon= getControl(sControl, sPage);
		if (oCon==null) oLog.error(String.format("%s control name is invalid ",sControl )); 
		else return createControl(oType, oCon);
		return null;
	}
	
	////////////////////////////////////////////////////////////////////////
	//
	//Create a new control with By.xPath of the locator control
	//@Parameters:
	//	 .OType is Class of a created control
	//   .oBy is By.xPath of the locator control
	//
	////////////////////////////////////////////////////////////////////////
	//@SuppressWarnings("unchecked")
	public static <T> T $(Class<T> oType, By oBy) {
		return createControl(oType, oBy);
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	// Create a Control whose type & value are suitable with specified oType & oValue
	// Note that, this function is used for control that has dynamic locator
	// @Parameters:  
	//		.oType is type of control
	//		.oCon is the control object that's stored information 
	//		.oValue is value that will be replaced to dynamic locator 
	//////////////////////////////////////////////////////////////////////////////////
	public static <T> T $(Class<T> oType, Control oCon, Object oValue)
	{
		return createControl(oType, oCon,oValue);
	}
	
	////////////////////////////////////////////////////////////////////////
	//
	// get Locator of a control in a page that is stored in Json file   
	// @Parameters:
	//	 .OType is Class of a created control
	//   .sControl is name of an Element that stores information of a control, in Json file
	//   .sPage is sPage.json file to store locators of the page
	//
	////////////////////////////////////////////////////////////////////////
	public static Control getControl(String sControl, String sPage )
	{
		if ((sControl.isEmpty()) || sPage.isEmpty()){
			oLog.debug(String.format("The specified control name or page name is empty"));
			return null;
		}
		
		if (sPage.contains("_"))
			sPage = sPage.split("_")[0];
		
		List<Control> lst =null;
		if (PageObjectHelper.loadPageControl(sPage)) lst = PageObjectHelper.getPageControls(sPage);
		if (lst==null) oLog.debug(String.format("%s page control is empty", sPage));
		else return findControl(lst, sControl);
		
		return null;
	}
	//////////////////////////////////////////////////////////////////////////////////
	//Create a control whose type is suitable with specified oType
	//@Parameters:  
	//		.oType is type of control
	//		.oBy is the By.Xpath of the locator control 
 	//////////////////////////////////////////////////////////////////////////////////
	public static <T> T createControl(Class<T> oType, By oBy) 
	{
		oLog.debug(String.format("CreatControl with Type:%s  and  BY:%s", oType.toString(), oBy.toString()));
		try
		{
			return (T)oType.getConstructor(String.class).newInstance(oBy);
			
		} catch (Exception e) {
			oLog.error(e.getMessage());
			return null;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////
	// Create a control whose type is suitable with specified oType
	// @Parameters:  
	//		.oType is type of control
	//		.oCon is the control object that's stored information 
 	//////////////////////////////////////////////////////////////////////////////////
	public static <T> T createControl(Class<T> oType, Control oCon) 
	{
		oLog.debug(String.format("CreatControl with Type:%s ", oType.toString()));
		try
		{
			String sTypeName = oType.getSimpleName().toLowerCase();
			if (!sTypeName.equals(oCon.getType().toLowerCase())) throw new Exception(String.format("Invalid specified Type and Type of Control"));
			else if (oCon.getLocator().isEmpty()) throw new Exception(String.format("Invalid locator of %s", oCon.getName()));
			else return (T)oType.getConstructor(String.class).newInstance(oCon.getLocator());	
			
		} catch (Exception e) {
			oLog.error(e.getMessage());
			return null;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////
	// Create a Control whose type & value are suitable with specified oType & oValue
	// Note that, this function is used for control that has dynamic locator
	// @Parameters:  
	//		.oType is type of control
	//		.oCon is the control object that's stored information 
	//		.oValue is value that will be replaced to dynamic locator 
	//////////////////////////////////////////////////////////////////////////////////
	public static <T> T createControl(Class<T> oType, Control oCon, Object oValue)
	{
		oLog.debug(String.format("CreatControl with Type:%s ", oType.toString()));
		T obj = null;
		try {
			String sTypeName = oType.getSimpleName();
			if (!sTypeName.equals(oCon.getType())) throw new Exception(String.format("Invalid specified Type and Type of Control"));
			else if (oCon.getLocator().isEmpty()) throw new Exception(String.format("Invalid locator of %s", oCon.getName()));
			else obj = (T)oType.getConstructor(String.class, Object.class).newInstance(oCon.getLocator(), oValue);
		}	
		catch(Exception e){
			oLog.error(e.getMessage());
		}
		return obj;
	}

	////////////////////////////////////////////////////////////////////////
	// Find a control by specified name in specified lstControl 
	// @Parameters: 
	//		.lstControls is list of control object
	//		.sName is name of a control  
	////////////////////////////////////////////////////////////////////////
	private static Control findControl(List<Control> lstControls, String sName) {
		
		if (lstControls==null) return null;
		try {
			Control ctl = lstControls.stream().filter(s -> s.getName().equals(sName)).findFirst().get();
			return ctl;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(String.format("Control: Name \"%s\" is not existed in json Data!", sName));
		}
	}	

	public static By getByLocator(String locator) {
		String body = locator.replaceAll("[\\w\\s]*=(.*)", "$1").trim();
		String type = locator.replaceAll("([\\w\\s]*)=.*", "$1").trim();
		switch (type) {
		case "css":
			return By.cssSelector(body);
		case "id":
			return By.id(body);
		case "link":
			return By.linkText(body);
		case "xpath":
			return By.xpath(body);
		case "text":
			return By.xpath(String.format("//*[contains(text(), '%s')]", body));
		case "name":
			return By.name(body);
		default:
			return By.xpath(locator);
		}
	}
}
