package dtos;

public class ChangeFollowDTO {
	public int userId;
	public String token;
	public boolean follow;
	
	public ChangeFollowDTO(int userId, String token, boolean follow) {
		super();
		this.userId = userId;
		this.token = token;
		this.follow = follow;
	}
	public ChangeFollowDTO() {
		super();
	}
	
	

}
