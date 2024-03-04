package dtos;

import java.sql.Timestamp;

public class UserToProfileDTO {
	public int id;
	public String username, email;
	public byte[] avatar;
	public Timestamp registration_date;

	public UserToProfileDTO(int id, String username, String email, byte[] avatar, Timestamp registration_date) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.avatar = avatar;
		this.registration_date = registration_date;
	}

	public UserToProfileDTO() {
		super();
	}

}
