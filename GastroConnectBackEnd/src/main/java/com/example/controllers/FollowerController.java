package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Follower;
import com.example.model.User;
import com.example.repositories.FollowerRepository;
import com.example.services.JwtService;

import dtos.FollowCardDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@Tag(name = "Followers", description = "")
@RequestMapping("/follower")
public class FollowerController {

	@Autowired
	FollowerRepository followerRepository;
	@Autowired
	private JwtService jwtService;

	@GetMapping("/getFollowers/{tokenUrl}")
	@Operation(summary = "Obtener seguidores de un usuario, usado en la pestaña followers", parameters = {
			@Parameter(name = "lang", in = ParameterIn.HEADER, allowEmptyValue = false, description = "Language", schema = @Schema(type = "string", defaultValue = "es")) }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data to send", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FollowCardDTO.class), examples = {
					@ExampleObject(value = "{\r\n"
							+ "\"token\": \"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0OCIsImlhdCI6MTcwNzcyNDgwNCwiZXhwIjoxNzM5MzQ3MjA0fQ.TMMNUfQFOkSuG5oq_YrcOX95oVUWcEurZ_ls8IZuK1M\"\r\n"
							+ "}") })), responses = {
									@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
									@ApiResponse(responseCode = "500", description = "Server Error", content = @Content(mediaType = "application/json")) })
	public ResponseEntity getFollowers(@PathVariable(value = "tokenUrl") String tokenUrl) {
		List<FollowCardDTO> listOfFollowersDTO = new ArrayList<FollowCardDTO>();
		try {
			if (tokenUrl != null && tokenUrl.startsWith("Bearer ")) {
				String token = tokenUrl.substring(7);
				int id = Integer.parseInt(jwtService.extractId(token));
				List<Follower> listOfFollowers = followerRepository.getAllByUser2Id(id);
				for (Follower followerTable : listOfFollowers) {
					User follower = followerTable.getUser1();
					FollowCardDTO dtoToAdd = new FollowCardDTO();
					dtoToAdd.avatar = follower.getAvatar();
					dtoToAdd.id = follower.getId();
					dtoToAdd.email = follower.getEmail();
					dtoToAdd.registration_date = follower.getRegistrationDate();
					dtoToAdd.username = follower.getUsername();
					listOfFollowersDTO.add(dtoToAdd);
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(listOfFollowersDTO, HttpStatus.OK);
	}

	@GetMapping("/getFolloweds/{tokenUrl}")
	@Operation(summary = "Obtener seguidos de un usuario, usado en la pestaña following", parameters = {
			@Parameter(name = "lang", in = ParameterIn.HEADER, allowEmptyValue = false, description = "Language", schema = @Schema(type = "string", defaultValue = "es")) }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data to send", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FollowCardDTO.class), examples = {
					@ExampleObject(value = "{\r\n"
							+ "\"token\": \"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0OCIsImlhdCI6MTcwNzcyNDgwNCwiZXhwIjoxNzM5MzQ3MjA0fQ.TMMNUfQFOkSuG5oq_YrcOX95oVUWcEurZ_ls8IZuK1M\"\r\n"
							+ "}") })), responses = {
									@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
									@ApiResponse(responseCode = "500", description = "Server Error", content = @Content(mediaType = "application/json")) })
	public ResponseEntity getFolloweds(@PathVariable(value = "tokenUrl") String tokenUrl) {
		List<FollowCardDTO> listOfFollowersDTO = new ArrayList<FollowCardDTO>();
		try {
			if (tokenUrl != null && tokenUrl.startsWith("Bearer ")) {
				String token = tokenUrl.substring(7);
				int id = Integer.parseInt(jwtService.extractId(token));
				List<Follower> listOfFollowers = followerRepository.getAllByUser1Id(id);
				for (Follower followerTable : listOfFollowers) {
					User follower = followerTable.getUser2();
					FollowCardDTO dtoToAdd = new FollowCardDTO();
					dtoToAdd.avatar = follower.getAvatar();
					dtoToAdd.id = follower.getId();
					dtoToAdd.email = follower.getEmail();
					dtoToAdd.registration_date = follower.getRegistrationDate();
					dtoToAdd.username = follower.getUsername();
					listOfFollowersDTO.add(dtoToAdd);
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(listOfFollowersDTO, HttpStatus.OK);
	}
}
