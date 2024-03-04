package dtos;

public class RecipeToViewDTO {
	public int id;
	public String title, description, ingredients, preparation_steps, difficulty;
	public boolean editable;
	public float preparation_time;
	public byte[] photo;
	
	

	public RecipeToViewDTO(int id, String title, String description, String ingredients, String preparation_steps,
			String difficulty, boolean editable, float preparation_time, byte[] photo) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.ingredients = ingredients;
		this.preparation_steps = preparation_steps;
		this.difficulty = difficulty;
		this.editable = editable;
		this.preparation_time = preparation_time;
		this.photo = photo;
	}



	public RecipeToViewDTO() {
		super();
	}
	
}
