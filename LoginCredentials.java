package pro;


public class LoginCredentials {
	private String userName;
	private String password;
	private String subject;
	public String getUserName() {
		return userName;
	}
	public String getSubject() {
		return subject;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginCredentials(String userName, String password,String subject){
		this.userName = userName;
		this.password = password;
		this.subject=subject;
	}
}
