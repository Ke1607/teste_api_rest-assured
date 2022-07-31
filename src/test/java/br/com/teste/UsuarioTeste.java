package br.com.teste;

import io.restassured.RestAssured;
import modelo.Usuario;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioTeste {

    @BeforeAll
    public static void configurar() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    @Order(1)
    public void criarUsuario() {
        Usuario usuario = new Usuario("Keila", "QA");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(usuario)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .body("name", equalTo("Keila"),
                        "job", equalTo("QA"),
                        "createdAt", notNullValue());
    }

    @Test
    @Order(2)
    public void buscarUsuario() {
        when()
                .get("/users/{id}", 2)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body(
                        "data.id", equalTo(2),
                        "data.first_name", equalTo("Janet"),
                        "data.last_name", notNullValue(),
                        "data.email", notNullValue());

    }

    @Test
    @Order(3)
    public void atualizarDadosUsuario() {
        Usuario usuario = new Usuario("Keila Suellen", "Analista de Testes");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(usuario)
                .when()
                .put("/users/2")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("name", equalTo(usuario.getName()), "job", equalTo(usuario.getJob()));

    }

    @Test
    @Order(4)
    public void listarUsuario() {
        given().queryParam("page", 2)
                .get("/users")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);


    }


    @Test
    @Order(5)
    public void excluirUsuario() {
        when()
                .delete("/users/2")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
