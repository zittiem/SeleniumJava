package datatype.LGmail;

public class UserInfo {

	private String userName = null;
	private String password = null;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserInfo [userName=" + userName + ", password=" + password + "]";
	}

	public void showInfo() {
		System.out.println(this.toString());
	}
}
