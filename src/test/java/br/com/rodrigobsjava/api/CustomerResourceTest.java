package br.com.rodrigobsjava.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class CustomerResourceTest {

    @Test
    void shouldCreateCustomer() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"Rodrigo\",\"email\":\"rodrigo@email.com\"}")
        .when()
                .post("/clients")
        .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", is("Rodrigo"))
                .body("email", is("rodrigo@email.com"));
    }

    @Test
    void shouldFailValidation() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"\",\"email\":\"invalid\"}")
        .when()
                .post("/clients")
        .then()
                .statusCode(400)
                .body("error",is("Validation Error"))
                .body("fieldErros.field", hasItems("name","email"));
    }

}
