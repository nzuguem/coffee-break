package me.nzuguem.coffee.simulator.clients;

import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import me.nzuguem.coffee.simulator.models.MakeCoffeeRequest;
import me.nzuguem.coffee.simulator.models.MakeCoffeeResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/coffee-machine")
@RegisterRestClient(configKey = "coffee-machine-service")
public interface CoffeeMachineServiceClient {

    @POST
    MakeCoffeeResponse makeCoffee(MakeCoffeeRequest makeCoffeeRequest);

    @ClientExceptionMapper
    static RuntimeException toException(Response response) {

        return switch (response.getStatus()) {
            case 400 -> new RuntimeException("400");
            case 500 -> new RuntimeException("500");
            default -> null;
        };
    }
}
