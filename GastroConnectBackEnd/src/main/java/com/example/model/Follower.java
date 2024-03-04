package com.example.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


/**
 * The persistent class for the followers database table.
 * 
 */
@Entity
@Table(name="followers")
@NamedQuery(name="Follower.findAll", query="SELECT f FROM Follower f")
public class Follower implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="follower_user_id")
	@JsonIgnore
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="followed_user_id")
	@JsonIgnore
	private User user2;

	public Follower() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}