package driver.setting;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import helper.JsonHelper;

public class DriverProperty {

	private Platform platform;
	private DriverType driverType;
	private RunningMode mode;
	private String driverExecutable;
	private URL remoteUrl;
	private List<String> arguments;
	private Map<String, Object> userProfilePreference;
	private DesiredCapabilities capabilities;
	private int pageTimeOut = 60; // seconds
	private int elementTimeOut = 0; // seconds
	
	public Platform getPlatform() {
		return platform;
	}
	
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
	public void setPlatform(String platform) throws Exception {
		this.platform = Platform.fromString(platform);
	}
	
	public DriverType getDriverType() {
		return driverType;
	}
	
	public void setDriverType(DriverType driverType) {
		this.driverType = driverType;
	}
	
	public void setDriverType(String driverType) throws Exception {
		this.driverType = DriverType.fromString(driverType);
	}
	
	public RunningMode getMode() {
		return mode;
	}
	
	public void setMode(RunningMode mode) {
		this.mode = mode;
	}
	
	public void setMode(String mode) throws Exception {
		this.mode = RunningMode.fromString(mode);
	}
	
	public String getDriverExecutable() {
		return driverExecutable;
	}
	
	public void setDriverExecutable(String driverExecutable) {
		this.driverExecutable = driverExecutable;
	}
	
	public URL getRemoteUrl() {
		return remoteUrl;
	}
	
	public void setRemoteUrl(String remoteUrl) throws MalformedURLException {
		if (StringUtils.isNotBlank(remoteUrl))
			this.remoteUrl = new URL(remoteUrl);
	}
	
	public List<String> getArguments() {
		return arguments;
	}
	
	public String[] getArgumentsAsArray() {
		return (String[])arguments.toArray();
	}
	
	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}
	
	public void setArguments(String arguments) throws Exception {
		this.arguments = JsonHelper.convertJsonToList(arguments);
	}
	
	public Map<String, Object> getUserProfilePreference() {
		return userProfilePreference;
	}
	
	public void setUserProfilePreference(Map<String, Object> userProfilePreference) {
		this.userProfilePreference = userProfilePreference;
	}
	
	public void setUserProfilePreference(String userProfilePreference) throws Exception {
		this.userProfilePreference = JsonHelper.convertJsonToMap(userProfilePreference);
	}
	
	public DesiredCapabilities getCapabilities() {
		return capabilities;
	}
	
	public void setCapabilities(DesiredCapabilities capabilities) {
		this.capabilities = capabilities;
	}
	
	public void setCapabilities(String capabilities) throws Exception {
		this.capabilities = JsonHelper.convertJsonToCapabilities(capabilities);
	}
	
	public int getPageTimeOut() {
		return pageTimeOut;
	}
	
	public void setPageTimeOut(int pageTimeOut) {
		this.pageTimeOut = pageTimeOut;
	}
	
	public void setPageTimeOut(String pageTimeOut) {
		if (StringUtils.isNumeric(pageTimeOut))
			this.pageTimeOut = Integer.parseInt(pageTimeOut);
	}
	
	public int getElementTimeOut() {
		return elementTimeOut;
	}
	
	public void setElementTimeOut(int elementTimeOut) {
		this.elementTimeOut = elementTimeOut;
	}
	
	public void setElementTimeOut(String elementTimeOut) {
		if (StringUtils.isNumeric(elementTimeOut))
			this.elementTimeOut = Integer.parseInt(elementTimeOut);
	}
}
