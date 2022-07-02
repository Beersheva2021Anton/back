package telran.pulse.monitoring.security;

public class Account {

	private final String userName;
	private String hashPassword;
	private String role;
	
	public Account(String userName, String hashPassword, String role) {
		this.userName = userName;
		this.hashPassword = hashPassword;
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public String getRole() {
		return role;
	}

	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
