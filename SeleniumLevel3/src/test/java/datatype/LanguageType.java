package datatype;

public enum LanguageType {
	US("US", "English"), VI("VI", "Tiếng Việt");

	private String code;
	private String text;

	private LanguageType() {
		this.code = "US";
		this.text = "English";
	}

	private LanguageType(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public static LanguageType getLanguageByCode(String code) {
		for (LanguageType lang : LanguageType.values()) {
			if (lang.code.equals(code)) {
				return lang;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}