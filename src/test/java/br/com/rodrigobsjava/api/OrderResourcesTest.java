package br.com.rodrigobsjava.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class OrderResourcesTest {

    @Test
    void shouldCreatedOrderUpdateStatusAndFilter(){

        // Create customer
        UUID customerId =
        given()
            .contentType("application/json")
            .body("{\"name\":\"Test User\",\"email\":\"test.user+" + System.currentTimeMillis() + "@mail.com\"}")
        .when()
            .post("/clients")
        .then()
            .statusCode(201)
            .body("id",notNullValue())
            .extract()
            .jsonPath().getUUID("id");

        // Create Order
        UUID orderId =
        given()
            .contentType("application/json")
            .body("{\"customerId\":\""+customerId+"\",\"amount\":129.90}")
        .when()
            .post("/orders")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("customerId",is(customerId.toString()))
            .body("status", is("CREATED"))
        .extract()
            .jsonPath().getUUID("id");

        // Patch status
        given()
            .contentType("application/json")
            .body("{\"status\":\"PAID\"}")
        .when()
            .patch("/orders/"+orderId+"/status")
        .then()
            .statusCode(200)
            .body("status",is("PAID"));

        // Filter by status
        given()
        .when()
            .get("/orders/by-status/PAID")
        .then()
            .statusCode(200)
            .body("id",hasItem(orderId.toString()));

        // Filter by status
        given()
        .when()
            .get("/orders/by-customer/" + customerId)
        .then()
            .statusCode(200)
            .body("id",hasItem(orderId.toString()));

    }
}
