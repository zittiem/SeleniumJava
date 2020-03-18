package driver.setting;

public enum FindElementBy {
	css("css"), id("id"), link("link"), xpath("xpath"), text("text"), name("name");

	private String by;

	FindElementBy(String by) {
		this.by = by;
	}

	public String getValue() {
		return by;
	}

}
