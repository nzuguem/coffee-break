package me.nzuguem.coffee.machine.service;

import io.quarkus.logging.Log;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import me.nzuguem.coffee.machine.models.CoffeeQuality;
import me.nzuguem.coffee.machine.models.CoffeeType;
import me.nzuguem.coffee.machine.models.MakeCoffeeRequest;
import me.nzuguem.coffee.machine.models.MakeCoffeeResponse;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Path("/coffee-machine")
public class CoffeeMachineService {


    @POST
    public RestResponse<MakeCoffeeResponse> makeCoffee(MakeCoffeeRequest makeCoffeeRequest) throws InterruptedException {
        Log.infof("☕ Préparation d'un %s pour %s", makeCoffeeRequest.coffeeType(), makeCoffeeRequest.developerName());

        Thread.sleep(ThreadLocalRandom.current().nextInt(2000) + 1000L); // 1-3 seconds

        var ramdomQuality = CoffeeQuality.values()[ThreadLocalRandom.current().nextInt(CoffeeQuality.values().length)];

        if (Objects.equals(ramdomQuality, CoffeeQuality.MACHINE_ERROR)) {
            return RestResponse.serverError();
        }

        if (Objects.equals(ramdomQuality, CoffeeQuality.NO_MILK)
                && Objects.equals(makeCoffeeRequest.coffeeType(), CoffeeType.CAPPUCCINO)) {
            return RestResponse.status(Response.Status.BAD_REQUEST);
        }

        return RestResponse.ok(MakeCoffeeResponse.of(ramdomQuality, makeCoffeeRequest.coffeeType()));
    }
}
