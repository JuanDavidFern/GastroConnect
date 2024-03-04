package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Recipe;
import com.example.model.User;
import com.example.repositories.RecipeRepository;
import com.example.repositories.UserRepository;
import com.example.services.JwtService;

import dtos.OtherUserProfileDTO;
import dtos.RecipeToCardDTO;
import dtos.RecipeToSaveDTO;
import dtos.RecipeToViewDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin
@RestController
@Tag(name = "Recipes", description = "Operaciones con las recetas")
@RequestMapping("/recipes")
public class RecipeController {

	@Autowired
	RecipeRepository recipeRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@PostMapping("/save")
	@Operation(summary = "Crear una nueva receta o actualizarla", parameters = {
			@Parameter(name = "lang", in = ParameterIn.HEADER, allowEmptyValue = false, description = "Language", schema = @Schema(type = "string", defaultValue = "es")) }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data to send", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeToSaveDTO.class), examples = {
					@ExampleObject(value = "{\r\n" + "\"id\": \"0\",\r\n"
							+ "\"title\": \"Receta para insertar en swagger\",\r\n"
							+ "\"description\": \"Receta de prueba para insertar en swagger, esta receta solo es de prueba para insertar en el swagger\",\r\n"
							+ "\"ingredients\": \"java$springgboot$tomcat$sql\",\r\n"
							+ "\"preparation_steps\": \"Muchas horas seguidas de mucha documentacion y algunos videos, lo peor el jwt\",\r\n"
							+ "\"difficulty\": \"Medio\",\r\n"
							+ "\"user_token\": \"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0OCIsImlhdCI6MTcwNzcyNDgwNCwiZXhwIjoxNzM5MzQ3MjA0fQ.TMMNUfQFOkSuG5oq_YrcOX95oVUWcEurZ_ls8IZuK1M\",\r\n"
							+ "\"preparation_time\": \"50\",\r\n"
							+ "\"photo\": \"/9j/4AAQSkZJRgABAQEBLAEsAAD/4QB0RXhpZgAASUkqAAgAAAADAA4BAgAqAAAAMgAAABoBBQABAAAAXAAAABsBBQABAAAAZAAAAAAAAABNdXN0YWNoaW9lZCBjaGVmIEljb24uIENvb2sgYXZhdGFyIHByb2ZpbGUsAQAAAQAAACwBAAABAAAA/+EFOmh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8APD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4KPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyI+Cgk8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPgoJCTxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyIgeG1sbnM6SXB0YzR4bXBDb3JlPSJodHRwOi8vaXB0Yy5vcmcvc3RkL0lwdGM0eG1wQ29yZS8xLjAveG1sbnMvIiAgIHhtbG5zOkdldHR5SW1hZ2VzR0lGVD0iaHR0cDovL3htcC5nZXR0eWltYWdlcy5jb20vZ2lmdC8xLjAvIiB4bWxuczpkYz0iaHR0cDovL3B1cmwub3JnL2RjL2VsZW1lbnRzLzEuMS8iIHhtbG5zOnBsdXM9Imh0dHA6Ly9ucy51c2VwbHVzLm9yZy9sZGYveG1wLzEuMC8iICB4bWxuczppcHRjRXh0PSJodHRwOi8vaXB0Yy5vcmcvc3RkL0lwdGM0eG1wRXh0LzIwMDgtMDItMjkvIiB4bWxuczp4bXBSaWdodHM9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9yaWdodHMvIiBwaG90b3Nob3A6Q3JlZGl0PSJHZXR0eSBJbWFnZXMiIEdldHR5SW1hZ2VzR0lGVDpBc3NldElEPSI4NDI0MzMxNjYiIHhtcFJpZ2h0czpXZWJTdGF0ZW1lbnQ9Imh0dHBzOi8vd3d3LmlzdG9ja3Bob3RvLmNvbS9sZWdhbC9saWNlbnNlLWFncmVlbWVudD91dG1fbWVk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\"\r\n"
							+ "}") })), responses = {
									@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
									@ApiResponse(responseCode = "500", description = "Server Error", content = @Content(mediaType = "application/json")) })
	public ResponseEntity saveRecipe(@RequestBody RecipeToSaveDTO r) {
		try {
			Recipe recipe = new Recipe();
			recipe.setId(r.id);
			recipe.setTitle(r.title);
			recipe.setDescription(r.description);
			recipe.setIngredients(r.ingredients);
			recipe.setPreparationSteps(r.preparation_steps);
			recipe.setPreparationTime(r.preparation_time);
			recipe.setDifficulty(r.difficulty);
			recipe.setUser(userRepository.findById(Integer.parseInt(jwtService.extractId(r.user_token.substring(7)))));
			recipe.setPhoto(r.photo);
			recipeRepository.save(recipe);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(true, HttpStatus.OK);

	}

	@GetMapping("/getRecipeById/{recipeId}")
	@Operation(summary = "Obtener una receta, se usa para ver su vista general", parameters = {
			@Parameter(name = "lang", in = ParameterIn.HEADER, allowEmptyValue = false, description = "Language", schema = @Schema(type = "string", defaultValue = "es")) }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data to send", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeToViewDTO.class), examples = {
					@ExampleObject(value = "{\r\n" + "\"recipeId\": \"42\",\r\n"
							+ "\"token\": \"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0OCIsImlhdCI6MTcwNzcyNDgwNCwiZXhwIjoxNzM5MzQ3MjA0fQ.TMMNUfQFOkSuG5oq_YrcOX95oVUWcEurZ_ls8IZuK1M\"\r\n"
							+ "}") })), responses = {
									@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
									@ApiResponse(responseCode = "500", description = "Server Error", content = @Content(mediaType = "application/json")) })
	public ResponseEntity getRecipeById(@PathVariable(value = "recipeId") int recipeId,
			@RequestParam(value = "token", required = false, defaultValue = "") String token) {
		RecipeToViewDTO recipeToView = new RecipeToViewDTO();
		try {
			Recipe recipe = recipeRepository.getRecipeById(recipeId);
			recipeToView.description = recipe.getDescription();
			recipeToView.difficulty = recipe.getDifficulty();
			recipeToView.id = recipe.getId();
			recipeToView.ingredients = recipe.getIngredients();
			recipeToView.title = recipe.getTitle();
			recipeToView.preparation_time = recipe.getPreparationTime();
			recipeToView.preparation_steps = recipe.getPreparationSteps();
			recipeToView.photo = recipe.getPhoto();
			if (!token.equals("Bearer")) {
				recipeToView.editable = recipe.getUser().getId() == Integer
						.parseInt(jwtService.extractId(token.substring(7)));
			} else
				recipeToView.editable = false;
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(recipeToView, HttpStatus.OK);
	}

	@GetMapping("/getRecipesByUserId/{userId}")
	@Operation(summary = "Obtener todas las recetas de un usuario, se usa al entrar al perfil de dicho usuario", parameters = {
			@Parameter(name = "lang", in = ParameterIn.HEADER, allowEmptyValue = false, description = "Language", schema = @Schema(type = "string", defaultValue = "es")) }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data to send", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeToCardDTO.class), examples = {
					@ExampleObject(value = "{\r\n" + "\"userId\": \"48\"\r\n"+ "}") })), responses = {
									@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
									@ApiResponse(responseCode = "500", description = "Server Error", content = @Content(mediaType = "application/json")) })
	public ResponseEntity getRecipesByUserId(@PathVariable(value = "userId") int userId) {
		List<RecipeToCardDTO> listOfRecipes = new ArrayList<RecipeToCardDTO>();
		try {
			List<Recipe> recipes = recipeRepository.getAllRecipesByUserId(userId);
			for (Recipe recipe : recipes) {
				RecipeToCardDTO recipeToList = new RecipeToCardDTO();
				recipeToList.id = recipe.getId();
				recipeToList.description = recipe.getDescription();
				recipeToList.difficulty = recipe.getDifficulty();
				recipeToList.photo = recipe.getPhoto();
				recipeToList.title = recipe.getTitle();
				User user = recipe.getUser();
				recipeToList.user_id = user.getId();
				recipeToList.username = user.getUsername();
				recipeToList.userPhoto = user.getAvatar();
				listOfRecipes.add(recipeToList);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(listOfRecipes, HttpStatus.OK);
	}

	@GetMapping("/getMyRecipes/{token}")
	@Operation(summary = "Obtener mis recetas, usado en el apartado de setting", parameters = {
			@Parameter(name = "lang", in = ParameterIn.HEADER, allowEmptyValue = false, description = "Language", schema = @Schema(type = "string", defaultValue = "es")) }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data to send", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeToCardDTO.class), examples = {
					@ExampleObject(value = "{\r\n"
							+ "\"token\": \"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0OCIsImlhdCI6MTcwNzcyNDgwNCwiZXhwIjoxNzM5MzQ3MjA0fQ.TMMNUfQFOkSuG5oq_YrcOX95oVUWcEurZ_ls8IZuK1M\"\r\n"
							+ "}") })), responses = {
									@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
									@ApiResponse(responseCode = "500", description = "Server Error", content = @Content(mediaType = "application/json")) })
	public ResponseEntity getMyRecipes(@PathVariable(value = "token") String token) {
		List<RecipeToCardDTO> listOfRecipes = new ArrayList<RecipeToCardDTO>();
		try {
			if (!token.isBlank()) {
				if (token != null && token.startsWith("Bearer ")) {
					List<Recipe> recipes = recipeRepository
							.getAllRecipesByUserId(Integer.parseInt(jwtService.extractId(token.substring(7))));
					for (Recipe recipe : recipes) {
						RecipeToCardDTO recipeToList = new RecipeToCardDTO();
						recipeToList.id = recipe.getId();
						recipeToList.description = recipe.getDescription();
						recipeToList.difficulty = recipe.getDifficulty();
						recipeToList.photo = recipe.getPhoto();
						recipeToList.title = recipe.getTitle();
						User user = recipe.getUser();
						recipeToList.user_id = user.getId();
						recipeToList.username = user.getUsername();
						recipeToList.userPhoto = user.getAvatar();
						listOfRecipes.add(recipeToList);
					}
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(listOfRecipes, HttpStatus.OK);
	}

	@DeleteMapping("/deleteRecipe/{recipeId}/{token}")
	@Operation(summary = "Borra una receta", parameters = {
			@Parameter(name = "lang", in = ParameterIn.HEADER, allowEmptyValue = false, description = "Language", schema = @Schema(type = "string", defaultValue = "es")) }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data to send", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeToCardDTO.class), examples = {
					@ExampleObject(value = "{\r\n" + "\"recipeId\": \"44\",\r\n"
							+ "\"token\": \"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0OCIsImlhdCI6MTcwNzcyNDgwNCwiZXhwIjoxNzM5MzQ3MjA0fQ.TMMNUfQFOkSuG5oq_YrcOX95oVUWcEurZ_ls8IZuK1M\"\r\n" +"}") })), responses = {
									@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
									@ApiResponse(responseCode = "409", description = "Conflict ", content = @Content(mediaType = "application/json")),
									@ApiResponse(responseCode = "500", description = "Server Error", content = @Content(mediaType = "application/json")) })
	public ResponseEntity deleteRecipe(@PathVariable(value = "recipeId") int recipeId, @PathVariable(value = "token") String token) {
		try {
			Recipe r = recipeRepository.getRecipeById(recipeId);
			if (Integer.parseInt(jwtService.extractId(token.substring(7))) == r.getUser().getId()) {
				recipeRepository.delete(r);
			}else {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@GetMapping("/getRecipesByTitleAndDifficulty")
	@Operation(summary = "Obtener todas las recetas con un filtro doble, de dificultad y titulo", parameters = {
			@Parameter(name = "lang", in = ParameterIn.HEADER, allowEmptyValue = false, description = "Language", schema = @Schema(type = "string", defaultValue = "es")) }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data to send", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeToViewDTO.class), examples = {
					@ExampleObject(value = "{\r\n" + "\"difficulty\": \"Principiante, Medio, Avanzado\",\r\n"
							+ "\"title\": \"ci\"\r\n"
							+ "}") })), responses = {
									@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
									@ApiResponse(responseCode = "500", description = "Server Error", content = @Content(mediaType = "application/json")) })
	public ResponseEntity getRecipesByTitleAndDifficulty(
			@RequestParam(value = "difficulty", required = false, defaultValue = "") String difficulty,
			@RequestParam(value = "title", required = false, defaultValue = "") String title) {
		List<RecipeToCardDTO> listOfRecipes = new ArrayList<RecipeToCardDTO>();

		if (difficulty.isBlank())
			difficulty = null;
		List<Recipe> recipes = recipeRepository.findByTitleAndDifficulty(title.trim(), difficulty);
		for (Recipe recipe : recipes) {
			RecipeToCardDTO recipeToList = new RecipeToCardDTO();
			recipeToList.id = recipe.getId();
			recipeToList.description = recipe.getDescription();
			recipeToList.difficulty = recipe.getDifficulty();
			recipeToList.photo = recipe.getPhoto();
			recipeToList.title = recipe.getTitle();
			User user = recipe.getUser();
			recipeToList.user_id = user.getId();
			recipeToList.username = user.getUsername();
			recipeToList.userPhoto = user.getAvatar();
			listOfRecipes.add(recipeToList);
		}
		return new ResponseEntity<>(listOfRecipes, HttpStatus.OK);
	}

//	@GetMapping("/getAllRecipesByUserId/{id}")
//	public List<RecipeToCardDTO> getAllRecipesByUserId(@PathVariable(value = "id") int id) {
//		List<RecipeToCardDTO> listOfRecipes = new ArrayList<RecipeToCardDTO>();
//		List<Recipe> recipes = recipeRepository.getAllRecipesByUserId(id);
//		for (Recipe recipe : recipes) {
//			RecipeToCardDTO recipeToList = new RecipeToCardDTO();
//			recipeToList.id = recipe.getId();
//			recipeToList.description = recipe.getDescription();
//			recipeToList.difficulty = recipe.getDifficulty();
//			recipeToList.photo = recipe.getPhoto();
//			recipeToList.title = recipe.getTitle();
//			User user = recipe.getUser();
//			recipeToList.user_id = user.getId();
//			recipeToList.username = user.getUsername();
//			recipeToList.userPhoto = user.getAvatar();
//			listOfRecipes.add(recipeToList);
//		}
//		return listOfRecipes;
//	}
//	@GetMapping("/getAllRecipes")
//	public List<RecipeToCardDTO> getAllRecipes() {
//		List<RecipeToCardDTO> listOfRecipes = new ArrayList<RecipeToCardDTO>();
//		List<Recipe> recipes = recipeRepository.findAll();
//		for (Recipe recipe : recipes) {
//			RecipeToCardDTO recipeToList = new RecipeToCardDTO();
//			recipeToList.id = recipe.getId();
//			recipeToList.description = recipe.getDescription();
//			recipeToList.difficulty = recipe.getDifficulty();
//			recipeToList.photo = recipe.getPhoto();
//			recipeToList.title = recipe.getTitle();
//			User user = recipe.getUser();
//			recipeToList.user_id = user.getId();
//			recipeToList.username = user.getUsername();
//			recipeToList.userPhoto = user.getAvatar();
//			listOfRecipes.add(recipeToList);
//		}
//		return listOfRecipes;
//	}
}
