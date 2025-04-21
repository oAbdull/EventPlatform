package com.apigateway;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class ApigatewayApplicationTests {

	private final String CLIENT_ID = "VokMgmeT5K6Pc2sCfMtxydgJc6sFpaXW";
	private final String CLIENT_SECRET = "JgV4XYbODxXuvHRj6LsJ2jx7_oyYNgdiJWo2cixIHrkqorw1nBTlDqAyhU1PKIKT";
	private final String AUDIENCE = "https://eventplatform/api";
	private final String GRANT_TYPE = "client_credentials";
	private final String OAUTH_URL = "https://eventplatform0.eu.auth0.com/oauth/token";

	private final String BASE_URI="http://localhost:8080";

	@Test
	void contextLoads() {
	}

	@Test
	void getAccessTokenFromAuth0(){
		String accessToken = given()
				.body("{\"client_id\":\"" + CLIENT_ID + "\",\"client_secret\":\"" + CLIENT_SECRET + "\",\"audience\":\"" + AUDIENCE + "\",\"grant_type\":\"" + GRANT_TYPE + "\"}")
				.contentType(ContentType.JSON)
				.when()
				.post(OAUTH_URL)
				.jsonPath().getString("access_token");

		System.out.println("Access Token: " + accessToken);
	}

	@Test
	void testGetEventsEndpoint() {
		RestAssured.baseURI = BASE_URI;
		String accessToken = given()
				.body("{\"client_id\":\"" + CLIENT_ID + "\",\"client_secret\":\"" + CLIENT_SECRET + "\",\"audience\":\"" + AUDIENCE + "\",\"grant_type\":\"" + GRANT_TYPE + "\"}")
				.contentType(ContentType.JSON)
				.when()
				.post(OAUTH_URL)
				.jsonPath().getString("access_token");

		given()
				.header("Authorization", "Bearer " + accessToken)
				.contentType(ContentType.JSON)
				.when()
				.get("/api/events")
				.then()
				.statusCode(200)
				.body("$", is(not(emptyArray())))
				.body("name", everyItem(notNullValue()))
				.log().all();
	}

	@Test
	void testSaveEvent(){
		RestAssured.baseURI = BASE_URI;
		String accessToken = given()
				.body("{\"client_id\":\"" + CLIENT_ID + "\",\"client_secret\":\"" + CLIENT_SECRET + "\",\"audience\":\"" + AUDIENCE + "\",\"grant_type\":\"" + GRANT_TYPE + "\"}")
				.contentType(ContentType.JSON)
				.when()
				.post(OAUTH_URL)
				.jsonPath().getString("access_token");

		given()
				.header("Authorization", "Bearer " + accessToken)
				.body("{\"name\":\"Test Event\",\"location\":\"New Delhi.\",\"price\":100,\"date\":\"2025-10-01\"}")
				.contentType(ContentType.JSON)
				.when()
				.post("/api/events")
				.then()
				.statusCode(200)
				.body(containsString("Event saved successfully"));
	}

	@Test
	void testDeleteEvent(){
		RestAssured.baseURI = BASE_URI;
		String accessToken = given()
				.body("{\"client_id\":\"" + CLIENT_ID + "\",\"client_secret\":\"" + CLIENT_SECRET + "\",\"audience\":\"" + AUDIENCE + "\",\"grant_type\":\"" + GRANT_TYPE + "\"}")
				.contentType(ContentType.JSON)
				.when()
				.post(OAUTH_URL)
				.jsonPath().getString("access_token");


	}

	@Test
	void testSaveTicket(){
		RestAssured.baseURI = BASE_URI;
		String accessToken = given()
				.body("{\"client_id\":\"" + CLIENT_ID + "\",\"client_secret\":\"" + CLIENT_SECRET + "\",\"audience\":\"" + AUDIENCE + "\",\"grant_type\":\"" + GRANT_TYPE + "\"}")
				.contentType(ContentType.JSON)
				.when()
				.post(OAUTH_URL)
				.jsonPath().getString("access_token");

		given()
				.header("Authorization", "Bearer " + accessToken)
				.body("{\"eventId\":\"eventid1\",\"userid\":\"test@gmail.com\",\"bookingTime\":\"2025-10-10T05:00:00\",\"quantity\":10}")
				.contentType(ContentType.JSON)
				.when()
				.post("/api/tickets")
				.then()
				.statusCode(200)
				.body(containsString("Ticket booked successfully"));
	}

}
