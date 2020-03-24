package element.base.web;

public interface IAction {
	
	void click();
	
	void click(int x, int y);
	
	void clickByJS();
	
	void clickByAction();
	
	void doubleClick();
	
	void sendKeys(CharSequence... keysToEnter);
	
	void submit();
	
	void focus();
	
	void hover();
	
	void moveToElement();
	
	void scrollIntoView();
	
	void scrollIntoViewBottom();
	
}
