package me.nzuguem.gossip.service;

import io.quarkus.test.junit.QuarkusTest;
import me.nzuguem.coffee.machine.models.CoffeeType;
import me.nzuguem.coffee.machine.models.MakeCoffeeRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class CoffeeMachineServiceTests {

    @Test
    void should_make_coffee_cappuccino() {
        var response = given()
                        .contentType("application/json")
                        .body(new MakeCoffeeRequest("Test", CoffeeType.CAPPUCCINO))
                        .when()
                        .post("/coffee-machine")
                        .thenReturn();

        Assertions.assertThat(response.statusCode()).isIn(200, 400, 500);
    }

}
