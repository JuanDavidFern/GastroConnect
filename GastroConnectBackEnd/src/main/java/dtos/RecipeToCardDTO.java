package dtos;

public class RecipeToCardDTO {
	public int id, user_id;
	public String title, description, difficulty,username;
	public byte[] photo,userPhoto;
	
	

	public RecipeToCardDTO(int id, int user_id, String title, String description, String difficulty, byte[] photo,
			byte[] userPhoto, String username) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.title = title;
		this.description = description;
		this.difficulty = difficulty;
		this.photo = photo;
		this.userPhoto = userPhoto;
		this.username = username;
	}



	public RecipeToCardDTO() {
		super();
	}

}
