package dtos;

public class UserToLogDTO {
	public String username;
	public String password;
	
	public UserToLogDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UserToLogDTO() {
		super();
	}
	
}
