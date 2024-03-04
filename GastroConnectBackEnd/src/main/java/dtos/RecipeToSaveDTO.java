package dtos;


public class RecipeToSaveDTO {
	public int id;
	public String title, description, ingredients, preparation_steps, difficulty, user_token;
	public float preparation_time;
	public byte[] photo;
	
	

	public RecipeToSaveDTO(int id, String title, String description, String ingredients, String preparation_steps,
			String difficulty, String user_token, float preparation_time, byte[] photo) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.ingredients = ingredients;
		this.preparation_steps = preparation_steps;
		this.difficulty = difficulty;
		this.user_token = user_token;
		this.preparation_time = preparation_time;
		this.photo = photo;
	}



	public RecipeToSaveDTO() {
		super();
	}
	
	
	
}
