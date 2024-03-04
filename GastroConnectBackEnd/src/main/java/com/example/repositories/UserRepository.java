package com.example.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	public User findById(int id);
	public User findByUsernameAndPassword(String username, String password); 
	public User findByUsername(String username);
	public User findByEmail(String email);  
}
