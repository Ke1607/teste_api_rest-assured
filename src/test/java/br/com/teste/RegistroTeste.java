package br.com.teste;

import io.restassured.RestAssured;
import modelo.Cadastro;
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
public class RegistroTeste {

    @BeforeAll
    public static void configurar() {
        RestAssured.baseURI = "https://reqres.in/api";
    }


    @Test
    @Order(1)
    public void fazerCadastro(){
        Cadastro cadastro = new Cadastro("eve.holt@reqres.in", "pistol");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(cadastro)
                .when()
                .post("/register")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("token", notNullValue());
    }

    @Test
    @Order(1)
    public void fazerCadastroDadosInvalidos(){
        Cadastro cadastro = new Cadastro("sydney@fife", "");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(cadastro)
                .when()
                .post("/register")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("error", equalTo("Missing password"));
    }



}
