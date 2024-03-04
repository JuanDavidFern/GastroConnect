package dtos;


public class UserToRegisterDTO {
	public String username, email, password;
	public byte[] photo;

	public UserToRegisterDTO(String username, String email, String password, byte[] photo) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.photo = photo;
	}

	
}
