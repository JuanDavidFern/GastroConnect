package com.example.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the comments database table.
 * 
 */
@Entity
@Table(name="comments")
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="comment_date")
	private Timestamp commentDate;

	@Lob
	private String content;

	//bi-directional many-to-one association to Recipe
	@ManyToOne
	@JsonIgnore
	private Recipe recipe;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JsonIgnore
	private User user;

	public Comment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCommentDate() {
		return this.commentDate;
	}

	public void setCommentDate(Timestamp commentDate) {
		this.commentDate = commentDate;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Recipe getRecipe() {
		return this.recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}