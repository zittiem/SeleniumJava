package utils.base;

import java.util.List;
import java.util.Optional;

import com.logigear.driver.manager.Driver;

class Control {

	private String name;
	private String type;
	private List<String> locators = null;

	public Control(String name, String type, List<String> lst) {
		this.name = name;
		this.type = type;
		this.locators = lst;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocator() {

		if ((locators == null) || (locators.size() == 0))
			return "";
		if (locators.size() == 1) {
			return locators.get(0).split(":", 2)[1];
		}

		String browser = Driver.getDriverProperty().getBrowserName();
		String platform = Driver.getDriverProperty().getPlatform();

		String sKey1 = String.format("%s.%s", browser, platform);
		String sKey2 = String.format("%s._", browser);
		String sKey3 = String.format("_.%s", platform);
		String sKey4 = "_._";

		Optional<String> oVal = locators.stream().filter(s -> s.startsWith(sKey1)).findFirst();
		if (oVal.equals(Optional.empty()))
			oVal = locators.stream().filter(s -> s.startsWith(sKey2)).findFirst();
		if (oVal.equals(Optional.empty()))
			oVal = locators.stream().filter(s -> s.startsWith(sKey3)).findFirst();
		if (oVal.equals(Optional.empty()))
			oVal = locators.stream().filter(s -> s.startsWith(sKey4)).findFirst();

		return oVal.equals(Optional.empty()) ? "" : oVal.get().split(":", 2)[1];
	}

	public void setLocator(String sName, String sValue) {
		/*
		 * if (locators == null) locators = new ArrayList<String>();
		 * this.locators.add();
		 */
	}
}
