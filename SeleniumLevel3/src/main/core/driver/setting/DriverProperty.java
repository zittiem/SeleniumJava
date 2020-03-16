package driver.setting;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

import helper.JsonHelper;

public class DriverProperty {

	private Platform platform;
	private DriverType driverType;
	private int timeOut;
	private String driverExecutable;
	private URL remoteUrl;
	private RunningMode mode;
	private List<String> arguments;
	private Map<String, Object> userProfilePreference;
	private DesiredCapabilities capabilities;
	
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
	
	public int getTimeOut() {
		return timeOut;
	}
	
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
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
		this.remoteUrl = new URL(remoteUrl);
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
	
	public List<String> getArguments() {
		return arguments;
	}
	
	public String[] getArgumentsAsArray() {
		return (String[])arguments.toArray();
	}
	
	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}
	
	public void setArguments(String arguments) {
		this.arguments = JsonHelper.convertJsonToList(arguments);
	}
	
	public Map<String, Object> getUserProfilePreference() {
		return userProfilePreference;
	}
	
	public void setUserProfilePreference(Map<String, Object> userProfilePreference) {
		this.userProfilePreference = userProfilePreference;
	}
	
	public void setUserProfilePreference(String userProfilePreference) {
		this.userProfilePreference = JsonHelper.convertJsonToMap(userProfilePreference);
	}
	
	public DesiredCapabilities getCapabilities() {
		return capabilities;
	}
	
	public void setCapabilities(DesiredCapabilities capabilities) {
		this.capabilities = capabilities;
	}
	
	public void setCapabilities(String capabilities) {
		this.capabilities = JsonHelper.convertJsonToCapabilities(capabilities);
	}
}
