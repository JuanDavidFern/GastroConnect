package com.example.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the recipes database table.
 * 
 */
@Entity
@Table(name="recipes")
@NamedQuery(name="Recipe.findAll", query="SELECT r FROM Recipe r")
public class Recipe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String description;

	private String difficulty;

	private String ingredients;

	private byte[] photo;

	@Column(name="preparation_steps")
	private String preparationSteps;

	@Column(name="preparation_time")
	private float preparationTime;

	private String title;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="recipe")
	@JsonIgnore
	private List<Comment> comments;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JsonIgnore
	private User user;

	public Recipe() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getIngredients() {
		return this.ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public byte[] getPhoto() {
		return this.photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPreparationSteps() {
		return this.preparationSteps;
	}

	public void setPreparationSteps(String preparationSteps) {
		this.preparationSteps = preparationSteps;
	}

	public float getPreparationTime() {
		return this.preparationTime;
	}

	public void setPreparationTime(float preparationTime) {
		this.preparationTime = preparationTime;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setRecipe(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setRecipe(null);

		return comment;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}