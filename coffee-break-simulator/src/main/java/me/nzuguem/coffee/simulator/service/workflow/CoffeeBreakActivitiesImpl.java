package me.nzuguem.coffee.simulator.service.workflow;

import io.temporal.failure.ApplicationFailure;
import jakarta.enterprise.context.ApplicationScoped;
import me.nzuguem.coffee.simulator.clients.CoffeeMachineServiceClient;
import me.nzuguem.coffee.simulator.clients.GossipServiceClient;
import me.nzuguem.coffee.simulator.models.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CoffeeBreakActivitiesImpl implements  CoffeeBreakActivities {

    private final CoffeeMachineServiceClient coffeeMachineServiceClient;
    private final GossipServiceClient gossipServiceClient;

    public CoffeeBreakActivitiesImpl(
            @RestClient CoffeeMachineServiceClient coffeeMachineServiceClient,
            @RestClient GossipServiceClient gossipServiceClient) {
        this.coffeeMachineServiceClient = coffeeMachineServiceClient;
        this.gossipServiceClient = gossipServiceClient;
    }

    @Override
    public MakeCoffeeResponse makeCoffee(MakeCoffeeRequest makeCoffeeRequest) {

        try {
            return this.coffeeMachineServiceClient.makeCoffee(makeCoffeeRequest);
        } catch (Exception exception) {
            return switch (exception.getMessage()) {
                case "400" -> MakeCoffeeResponse.of(CoffeeQuality.NO_MILK, makeCoffeeRequest.coffeeType());
                case "500" -> throw ApplicationFailure.newNonRetryableFailureWithCause(CoffeeQuality.MACHINE_ERROR.name(), CoffeeQuality.MACHINE_ERROR.description(), exception);
                default -> throw ApplicationFailure.newFailureWithCause("Access Temporary Disallowed", "🧹 Le service de menage est en action...", exception);
            };
        }
    }

    @Override
    public GetGossipResponse getGossip(GetGossipRequest getGossipRequest) {

        try {
            return this.gossipServiceClient.getGossip(getGossipRequest.listener());
        } catch (Exception _) {
            return GetGossipResponse.of("🤔 Les gens sont bizarrement silencieux aujourd'hui...", getGossipRequest.listener());
        }
    }
}
