package dtos;

public class UserDetailsDTO {
	public String username, email;
	public byte[] userPhoto;
	
	public UserDetailsDTO(String username, String email, byte[] userPhoto) {
		super();
		this.username = username;
		this.email = email;
		this.userPhoto = userPhoto;
	}

	public UserDetailsDTO() {
		super();
	}

	
}
