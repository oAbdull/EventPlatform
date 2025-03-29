//package org.example.userservice;
//
//import io.restassured.RestAssured;
//import org.example.userservice.model.User;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.testcontainers.containers.RabbitMQContainer;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
//public class E2ETest {
//
//    @LocalServerPort
//    private int port;
//
//    @Container
//    private static final RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3-management")
//            .withExposedPorts(5672, 15672)
//            .withUser("guest", "guest");
//
//    @Container
//    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest")
//            .withExposedPorts(27017);
//
//    @BeforeAll
//    public static void setUp() {
//        // Set environment variables for RabbitMQ and MongoDB
//        System.setProperty("SPRING_RABBITMQ_HOST", rabbitMQContainer.getHost());
//        System.setProperty("SPRING_RABBITMQ_PORT", rabbitMQContainer.getMappedPort(5672).toString());
//        System.setProperty("SPRING_DATA_MONGODB_URI", mongoDBContainer.getReplicaSetUrl("userDB"));
//    }
//
//    @Test
//    public void testUserCreationFlow() {
//        RestAssured.baseURI = "http://localhost:" + port;
//
//        User user = new User("3", "test_user", "test@example.com");
//
//        given()
//                .contentType("application/json")
//                .body(user)
//                .when()
//                .post("/api/users")
//                .then()
//                .statusCode(200)
//                .body("username", equalTo("test_user"))
//                .body("email", equalTo("test@example.com"));
//    }
//}