package datatype.Agoda;

public class UserInfo {

	private String email = null;
	private String password = null;

	public String getEmail() {
		return email;
	}

	public void setUserName(String userName) {
		this.email = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserInfo [email=" + email + ", password=" + password + "]";
	}

	public void showInfo() {
		System.out.println(this.toString());
	}
}
