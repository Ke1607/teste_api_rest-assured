package br.com.teste;

import io.restassured.RestAssured;
import modelo.Login;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTeste {

    @BeforeAll
    public static void configurar() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    @Order(1)
    public void fazerLogin(){
        Login login = new Login("eve.holt@reqres.in", "cityslicka");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("token", notNullValue());


    }

    @Test
    @Order(1)
    public void validarLoginInvalido(){
        Login login = new Login("teste", "cityslicka");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("error", notNullValue());

    }
}
