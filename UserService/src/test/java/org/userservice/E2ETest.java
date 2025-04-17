package org.userservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.StringContains.containsString;

@Testcontainers
@SpringBootTest
public class E2ETest {

    private static final String RANDOM_SUFFIX = UUID.randomUUID().toString().substring(0, 8);

    @Container
    private static final DockerComposeContainer<?> environment = new DockerComposeContainer<>(
            new File("../docker-compose.yml"))
            .withLocalCompose(true)
            .withEnv("RANDOM_SUFFIX", RANDOM_SUFFIX)
            .withExposedService("apigateway_" + RANDOM_SUFFIX, 8080)
            .withExposedService("userservice_" + RANDOM_SUFFIX, 8081)
            .withExposedService("ticketservice_" + RANDOM_SUFFIX, 8083)
         //   .withExposedService("discoveryserver_" + RANDOM_SUFFIX, 8761)
            .withExposedService("postgres_" + RANDOM_SUFFIX, 5432)
            .withExposedService("rabbitmq_" + RANDOM_SUFFIX, 5672)
            .withStartupTimeout(Duration.ofDays(100))
            .withLogConsumer("apigateway_" + RANDOM_SUFFIX, outputFrame -> System.out.println("Apigateway: " + outputFrame.getUtf8String()))
            .withLogConsumer("userservice_" + RANDOM_SUFFIX, outputFrame -> System.out.println("UserService: " + outputFrame.getUtf8String()))
            .withLogConsumer("ticketservice_" + RANDOM_SUFFIX, outputFrame -> System.out.println("TicketService: " + outputFrame.getUtf8String()));

    private static String accessToken;

    @BeforeAll
    static void setUp() throws InterruptedException {
        System.setProperty("RANDOM_SUFFIX", RANDOM_SUFFIX);
        environment.start();
        Thread.sleep(10000); // انتظار 10 ثوانٍ إضافية

        // الحصول على رمز JWT من Auth0
        RestTemplate restTemplate = new RestTemplate();
        String auth0TokenUrl = "https://dev--yrfqzl5.us.auth0.com/oauth/token";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "testuser1");
        params.add("password", "password123");
        params.add("audience", "https://your-api-audience"); // استبدل بـ audience الخاص بك
        params.add("client_id", "S3KFQTMFmv8GkDILXK7wVNM7l1OtLLT8");
        params.add("client_secret", "suDdS85SPY6UKa__NLBpvvLD_YWlAYxGrwfSA9pZP3lqZr6_IVGjv6zD8QHGWTcm");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(auth0TokenUrl, entity, Map.class);
        accessToken = (String) response.getBody().get("access_token");

        RestAssured.baseURI = "http://localhost:8081";
    }

    @AfterAll
    static void tearDown() {
        environment.stop();
    }

    @Test
    void registerUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        Map<String, String> user = new HashMap<>();
        user.put("userid", "testuser2");
        user.put("pwd", "password456");
        user.put("role", "USER");
        user.put("username", "Test User 2");

        given()
                .contentType("application/json")
                .headers(headers)
                .body(user)
                .when()
                .post("/api/users/register")
                .then()
                .statusCode(200)
                .body(equalTo("User registered successfully"));
    }

    @Test
    void loginWithCorrectCredentials() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        Map<String, String> login = new HashMap<>();
        login.put("userid", "testuser1");
        login.put("pwd", "password123");

        given()
                .contentType("application/json")
                .headers(headers)
                .body(login)
                .when()
                .post("/api/users/login")
                .then()
                .statusCode(200)
                .body(containsString("Login successful"));
    }

    @Test
    void loginWithWrongCredentials() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        Map<String, String> login = new HashMap<>();
        login.put("userid", "testuser1");
        login.put("pwd", "wrongpassword");

        given()
                .contentType("application/json")
                .headers(headers)
                .body(login)
                .when()
                .post("/api/users/login")
                .then()
                .statusCode(401)
                .body(containsString("Invalid credentials"));
    }

    @Test
    void fetchUserList() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        given()
                .headers(headers)
                .when()
                .get("/api/users/list")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}