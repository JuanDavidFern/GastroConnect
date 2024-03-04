package com.example.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Follower;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Integer> {
	public List<Follower> getAllByUser2Id(int id);
	public List<Follower> getAllByUser1Id(int id);
	public Follower findByUser1IdAndUser2Id(int id1, int id2);
}
