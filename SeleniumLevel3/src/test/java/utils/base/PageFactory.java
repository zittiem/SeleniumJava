package utils.base;

import org.openqa.selenium.By;

public class PageFactory {

	private String sPageName = "";;
	
	//create instance of a page & load all locators of all controls  
	public PageFactory()
	{
		sPageName = getClass().getSimpleName();
		//Load all locators of the current page from JS file
		PageObjectHelper.loadPageControl(sPageName);
	}

	////////////////////////////////////////////////////////////////////////
	//Create a new control with absolute locator that is got from JS file 
	//@Parameters:
	//	 .OType is Class of a created control
	//   .sName is name of an Element that stores information of a control, in Json file
	////////////////////////////////////////////////////////////////////////
	//@SuppressWarnings("unchecked")
	protected <T> T $(Class<T> oType, String sControl) {
		if (sControl.contains(".")) {
			return ControlFactory.$(oType, sControl);
		}
		return ControlFactory.$(oType, sControl, sPageName);
	}	
	////////////////////////////////////////////////////////////////////////
	//Create a new control with By.xPath of the locator control
	//@Parameters:
	//	 .OType is Class of a created control
	//   .oBy is By.xPath of the locator control
	////////////////////////////////////////////////////////////////////////
	//@SuppressWarnings("unchecked")
	protected <T> T $(Class<T> oType, By oBy) {
		return ControlFactory.$(oType, oBy);
	}	
	/////////////////////////////////////////////////////////////////////////
	//Create a new control with dynamic locator that is got from Jason file.
	//@Parameters:
	//	 .oType is Class of a created control
	//   .sControl is name of an Element that stores information of a control, in Json file
	//	 .oValue is value that is replaced to the dynamic locator. 
	/////////////////////////////////////////////////////////////////////////
	//@SuppressWarnings("unchecked")
	protected <T> T $(Class<T> oType, String sControl, Object oValue)
	{
		Control oCon=  ControlFactory.getControl(sControl, sPageName);
		return ControlFactory.$(oType, oCon, oValue);
	}

	//Navigate to the current page with default path that is already assigned in the current page   
	public boolean navigateToMe(){return true;}
	//Navigate to the current page with specified path 
	public boolean navigateToMe(String sPath){return true;}
	//wait for all page controls being available  
	public boolean loadUIControlsAvailable(){return true;}
	//wait for all specified controls being available  
	public boolean loadUIControlsAvailable(Object oVar){return true;}
	// build Environment if it's necessary (backup the current environment and build a new environment for test )
	public boolean buildEnviroment(){return true;}
	// recovery the backup environment  
	public boolean recoverEnvironment(){return true;}

}
