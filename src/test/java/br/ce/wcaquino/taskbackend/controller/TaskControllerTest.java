package br.ce.wcaquino.taskbackend.controller;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class TaskControllerTest {

    @BeforeClass
    public static void setup() {
        baseURI = "http://tomcat:8080/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        given()
        .when()
            .get("/todo")
        .then()
            .statusCode(200)
        ;
    }

    @Test
    public void deveAdicionarTarefaComSucesso() {
        given()
            .body("{\"task\": \"Teste via API\", \"dueDate\": \"2020-12-30\"}")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .log().all()
            .statusCode(201)
        ;
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida() {
        given()
                .body("{\"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\"}")
                .contentType(ContentType.JSON)
        .when()
                .post("/todo")
        .then()
                .log().all()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"))
        ;
    }
}