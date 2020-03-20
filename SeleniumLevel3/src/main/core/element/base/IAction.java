package element.base;

public interface IAction {
	
	void click();
	
	void click(int x, int y);
	
	void clickByJS();
	
	void clickByAction();
	
	void doubleClick();
	
	void sendKeys(CharSequence... keysToEnter);
	
	void clear();
	
	void submit();
	
	void focus();
	
	void hover();
	
	void moveToElement();
	
	void scrollIntoView();
	
	void scrollIntoViewBottom();
	
}
