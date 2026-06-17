package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

public class ScoreControllerRA {

	private String clientToken;

	@BeforeEach
	void setUp() throws Exception {
		baseURI = "http://localhost:8080";
		clientToken = TokenUtil.obtainAccessToken("maria@gmail.com", "123456");
	}

	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {
		JSONObject score = new JSONObject();
		score.put("movieId", 100000L);
		score.put("score", 4.0);

		given()
			.header("Authorization", "Bearer " + clientToken)
			.contentType(ContentType.JSON)
			.body(score.toString())
			.put("/scores")
		.then()
			.statusCode(404);
	}

	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		JSONObject score = new JSONObject();
		score.put("score", 4.0);

		given()
			.header("Authorization", "Bearer " + clientToken)
			.contentType(ContentType.JSON)
			.body(score.toString())
			.put("/scores")
		.then()
			.statusCode(422);
	}

	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
		JSONObject score = new JSONObject();
		score.put("movieId", 1L);
		score.put("score", -1.0);

		given()
			.header("Authorization", "Bearer " + clientToken)
			.contentType(ContentType.JSON)
			.body(score.toString())
			.put("/scores")
		.then()
			.statusCode(422);
	}
}
