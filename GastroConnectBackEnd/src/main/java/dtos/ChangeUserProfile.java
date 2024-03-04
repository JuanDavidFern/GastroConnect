package dtos;

public class ChangeUserProfile {
	public String token, username, email;
	public byte[] avatar;
	
	public ChangeUserProfile(String token, String username, String email, byte[] avatar) {
		super();
		this.token = token;
		this.username = username;
		this.email = email;
		this.avatar = avatar;
	}
	public ChangeUserProfile() {
		super();
	}
	
	

}
