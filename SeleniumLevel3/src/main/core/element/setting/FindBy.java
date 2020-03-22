package element.setting;

public enum FindBy {
	css("css"), id("id"), linkText("linkText"), xpath("xpath"), text("text"), name("name");

	private String by;

	FindBy(String by) {
		this.by = by;
	}

	public String getValue() {
		return by;
	}
	
	public static FindBy fromString(String by) {
        for (FindBy item : FindBy.values()) {
			if (item.getValue().equalsIgnoreCase(by))
				return item;
		}
        return FindBy.xpath;
    }

}
