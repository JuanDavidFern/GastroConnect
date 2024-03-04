package dtos;

import java.sql.Timestamp;

public class OtherUserProfileDTO {
	public int id;
	public String username, email;
	public Timestamp registration_date;
	public byte[] avatar;
	public boolean follow, autoFollow = false;
	
	
	
	

	public OtherUserProfileDTO(int id, String username, String email, Timestamp registration_date, byte[] avatar,
			boolean follow, boolean autoFollow) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.registration_date = registration_date;
		this.avatar = avatar;
		this.follow = follow;
		this.autoFollow = autoFollow;
	}





	public OtherUserProfileDTO() {
		super();
	}
	
	

}
