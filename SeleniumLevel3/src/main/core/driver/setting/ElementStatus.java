package driver.setting;

public enum ElementStatus {
	PRESENT("presenceOfElementLocated"),
	VISIBLE("visibilityOf"),
	DISPLAY("visibilityOfElementLocated"),
	CLICKABLE("elementToBeClickable");
	
	private String status;

	ElementStatus(String status) {
		this.status = status;
	}

	public String getValue() {
		return status;
	}
}
