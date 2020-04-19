package datatype.LGmail;

public class UserInfo {

	private String username = null;
	private String password = null;

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserInfo [username=" + username + ", password=" + password + "]";
	}

	public void showInfo() {
		System.out.println(this.toString());
	}
}
