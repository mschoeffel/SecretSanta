package de.mschoeffel.secretsanta.controller;

import de.mschoeffel.secretsanta.SecretSantaApplication;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = SecretSantaApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class BackendControllerTest {

    @LocalServerPort
    private int port;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void saysHello() {
        when()
                .get("/api/v0/hello")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body(is(equalTo("test")));
    }

    /*
    @Test
    public void user_api_should_give_http_404_not_found_when_user_not_present_in_db() {
        Long someId = 200L;
        given()
                .pathParam("id", someId)
                .when()
                .get("/api/user/{id}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
     */

    @Test
    public void secured_api_should_react_with_unauthorized_per_default() {

        given()
                .when()
                .get("/api/v0/secured")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void secured_api_should_give_http_200_when_authorized() {

        given()
                .auth().basic("sina", "miller")
                .when()
                .get("/api/v0/secured")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body(is(equalTo(BackendController.SECURED_TEXT)));
    }


}
