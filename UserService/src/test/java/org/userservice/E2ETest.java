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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
            .withExposedService("discoveryserver_" + RANDOM_SUFFIX, 8761)
            .withExposedService("postgres_" + RANDOM_SUFFIX, 5432)
            .withExposedService("rabbitmq_" + RANDOM_SUFFIX, 5672)
            .withStartupTimeout(Duration.ofDays(100))
            .withLogConsumer("apigateway_" + RANDOM_SUFFIX, outputFrame -> System.out.println("Apigateway: " + outputFrame.getUtf8String()))
            .withLogConsumer("userservice_" + RANDOM_SUFFIX, outputFrame -> System.out.println("UserService: " + outputFrame.getUtf8String()))
            .withLogConsumer("ticketservice_" + RANDOM_SUFFIX, outputFrame -> System.out.println("TicketService: " + outputFrame.getUtf8String()));

    @BeforeAll
    static void setUp() throws InterruptedException {
        System.setProperty("RANDOM_SUFFIX", RANDOM_SUFFIX); // تعيين المتغير في البيئة
        environment.start();
        Thread.sleep(10000); // انتظار 10 ثوانٍ إضافية
        RestAssured.baseURI = "http://localhost:8090";
    }

    @AfterAll
    static void tearDown() {
        environment.stop();
    }

    @Test
    void testUserRegistrationAndTicketBooking() {
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