package element.mobile;

import element.base.IAction;
import element.setting.SwipeDirection;

public interface IMobileAction extends IAction {
	
	void tap();
	
	void longTap(int timeInSeconds);
	
	void dobuleTap();
	
	void swipe(SwipeDirection direction, int range);
	
	void zoom();
	
	void zoom(int x, int y);
	
	void setValue(Object value);
}
