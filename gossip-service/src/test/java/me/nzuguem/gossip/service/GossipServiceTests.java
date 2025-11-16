package me.nzuguem.gossip.service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GossipServiceTests {

    @LocalServerPort
    int serverPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.serverPort;
    }

    @Test
    void should_get_gossip() {
        given()
                .queryParam("developerName", "Test")
                .when()
                .get("/gossip")
                .then()
                .statusCode(200);
    }
}
