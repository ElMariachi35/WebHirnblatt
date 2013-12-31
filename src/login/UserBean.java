package login;

public class UserBean {
	private String username;
	private String password;
	private String email;
	private String id;
	public boolean valid;



	public String getEmail() {
		return email;
	}

	public void setEmail(String newEmail) {
		email = newEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String newPassword) {
		password = newPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUserName(String newUsername) {
		username = newUsername;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean newValid) {
		valid = newValid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}