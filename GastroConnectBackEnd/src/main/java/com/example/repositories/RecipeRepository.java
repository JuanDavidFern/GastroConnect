package com.example.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>{
	public List<Recipe> getAllRecipesByUserId(int id);
	public Recipe getRecipeById(int id);
	//public List<Recipe> findAllByTitleContainingAndDifficulty(String title, String difficulty);
	
	@Query("SELECT r FROM Recipe r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%')) AND (:difficulty IS NULL OR r.difficulty = :difficulty)")
    List<Recipe> findByTitleAndDifficulty(@Param("title") String title, @Param("difficulty") String difficulty);
}
