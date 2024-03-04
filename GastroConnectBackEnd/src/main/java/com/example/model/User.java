package com.example.model;

import java.io.Serializable;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private byte[] avatar;

	private String email;

	private String password;

	@Column(name="registration_date")
	private Timestamp registrationDate;

	private String username;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Comment> comments;

	//bi-directional many-to-one association to Follower
	@OneToMany(mappedBy="user1")
	@JsonIgnore
	private List<Follower> followers;

	//bi-directional many-to-one association to Follower
	@OneToMany(mappedBy="user2")
	@JsonIgnore
	private List<Follower> followerds;

	//bi-directional many-to-one association to Recipe
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Recipe> recipes;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getAvatar() {
		return this.avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setUser(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setUser(null);

		return comment;
	}

	public List<Follower> getFollowers() {
		return this.followers;
	}

	public void setFollowers(List<Follower> followers) {
		this.followers = followers;
	}

	public Follower addFollower(Follower follower) {
		getFollowers().add(follower);
		follower.setUser1(this);

		return follower;
	}

	public Follower removeFollower(Follower follower) {
		getFollowers().remove(follower);
		follower.setUser1(null);

		return follower;
	}

	public List<Follower> getFollowerds() {
		return this.followerds;
	}

	public void setFollowerds(List<Follower> followerds) {
		this.followerds = followerds;
	}

	public Follower addFollowerd(Follower followerd) {
		getFollowerds().add(followerd);
		followerd.setUser2(this);

		return followerd;
	}

	public Follower removeFollowerd(Follower followerd) {
		getFollowerds().remove(followerd);
		followerd.setUser2(null);

		return followerd;
	}

	public List<Recipe> getRecipes() {
		return this.recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public Recipe addRecipe(Recipe recipe) {
		getRecipes().add(recipe);
		recipe.setUser(this);

		return recipe;
	}

	public Recipe removeRecipe(Recipe recipe) {
		getRecipes().remove(recipe);
		recipe.setUser(null);

		return recipe;
	}

}