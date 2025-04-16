package org.userservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest
public class E2ETest {

    @Container
    private static final DockerComposeContainer<?> environment = new DockerComposeContainer<>(
            new File("../docker-compose.yml"))
            .withLocalCompose(true)
            .withExposedService("apigateway_1", 8080)
            .withExposedService("userservice_1", 8081)
            .withExposedService("ticketservice_1", 8083)
            .withExposedService("discoveryserver_1", 8761)
            .withExposedService("postgres_1", 5432)
            .withExposedService("rabbitmq_1", 5672);

    @BeforeAll
    static void setUp() {
        environment.start();
        RestAssured.baseURI = "http://localhost:8090"; // Apigateway port
    }

    @Test
    void testUserRegistrationAndTicketBooking() {
        // تسجيل مستخدم
        Map<String, String> user = new HashMap<>();
        user.put("userid", "testuser1");
        user.put("pwd", "password123");
        user.put("role", "USER");
        user.put("username", "Test User");

        given()
                .contentType("application/json")
                .body(user)
                .when()
                .post("/api/users/register")
                .then()
                .statusCode(200)
                .body(equalTo("User registered successfully"));

        // حجز تذكرة
        Map<String, Object> ticket = new HashMap<>();
        ticket.put("userid", "testuser1");
        ticket.put("eventid", "event123");
        ticket.put("bookingTime", LocalDateTime.now().toString());

        given()
                .contentType("application/json")
                .body(ticket)
                .when()
                .post("/api/tickets/book")
                .then()
                .statusCode(200)
                .body(equalTo("Ticket booked successfully"));
    }
}