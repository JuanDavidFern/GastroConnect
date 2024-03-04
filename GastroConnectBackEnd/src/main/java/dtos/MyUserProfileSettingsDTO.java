package dtos;

import java.sql.Timestamp;

public class MyUserProfileSettingsDTO {
	public String email, username;
	public Timestamp registration_date;
	public byte[] avatar;
	
	public MyUserProfileSettingsDTO(String username, String email, Timestamp registration_date, byte[] avatar) {
		super();
		this.username = username;
		this.email = email;
		this.registration_date = registration_date;
		this.avatar = avatar;
	}
	public MyUserProfileSettingsDTO() {
		super();
	}
	
	

}
