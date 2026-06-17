package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.is;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

public class MovieControllerRA {

	private String adminToken;
	private String clientToken;
	private String invalidToken;

	private Long existingId;
	private Long nonExistingId;

	@BeforeEach
	void setUp() throws Exception {
		baseURI = "http://localhost:8080";

		existingId = 1L;
		nonExistingId = 100L;

		adminToken = TokenUtil.obtainAccessToken("alex@gmail.com", "123456");
		clientToken = TokenUtil.obtainAccessToken("maria@gmail.com", "123456");
		invalidToken = "invalid-token";
	}

	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
		given()
			.get("/movies")
		.then()
			.statusCode(200);
	}

	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {
		given()
			.queryParam("title", "Bob Esponja")
			.get("/movies")
		.then()
			.statusCode(200)
			.body("content.title", hasItems("Bob Esponja"));
	}

	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {
		given()
			.get("/movies/{id}", existingId)
		.then()
			.statusCode(200)
			.body("id", is(existingId.intValue()));
	}

	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {
		given()
			.get("/movies/{id}", nonExistingId)
		.then()
			.statusCode(404);
	}

	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {
		JSONObject newMovie = new JSONObject();
		newMovie.put("title", "");
		newMovie.put("score", 0.0);
		newMovie.put("count", 0);
		newMovie.put("image", "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg");

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType(ContentType.JSON)
			.body(newMovie.toString())
			.post("/movies")
		.then()
			.statusCode(422);
	}

	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
		JSONObject newMovie = new JSONObject();
		newMovie.put("title", "Test Movie");
		newMovie.put("score", 0.0);
		newMovie.put("count", 0);
		newMovie.put("image", "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg");

		given()
			.header("Authorization", "Bearer " + clientToken)
			.contentType(ContentType.JSON)
			.body(newMovie.toString())
			.post("/movies")
		.then()
			.statusCode(403);
	}

	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
		JSONObject newMovie = new JSONObject();
		newMovie.put("title", "Test Movie");
		newMovie.put("score", 0.0);
		newMovie.put("count", 0);
		newMovie.put("image", "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg");

		given()
			.header("Authorization", "Bearer " + invalidToken)
			.contentType(ContentType.JSON)
			.body(newMovie.toString())
			.post("/movies")
		.then()
			.statusCode(401);
	}
}
