package dtos;

public class ChangePassDTO {
	public String token, oldPass, newPass;

	public ChangePassDTO(String token, String oldPass, String newPass) {
		super();
		this.token = token;
		this.oldPass = oldPass;
		this.newPass = newPass;
	}

	public ChangePassDTO() {
		super();
	}
	
	

}
