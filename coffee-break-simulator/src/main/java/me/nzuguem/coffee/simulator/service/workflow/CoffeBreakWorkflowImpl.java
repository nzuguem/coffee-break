package me.nzuguem.coffee.simulator.service.workflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.failure.ApplicationFailure;
import io.temporal.failure.TemporalFailure;
import io.temporal.workflow.Workflow;
import jakarta.enterprise.inject.spi.CDI;
import me.nzuguem.coffee.simulator.models.CoffeeBreakRequest;
import me.nzuguem.coffee.simulator.models.Event;
import me.nzuguem.coffee.simulator.service.EventEmitter;

import java.time.Duration;

public class CoffeBreakWorkflowImpl implements CoffeBreakWorkflow {

    private final CoffeeBreakActivities coffeeBreakActivities = Workflow.newActivityStub(
            CoffeeBreakActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(30))
                    .setRetryOptions(
                            io.temporal.common.RetryOptions.newBuilder()
                                    .setBackoffCoefficient(2)
                                    .setMaximumAttempts(5)
                                    .build()
                    )
                    .build());

    private final EventEmitter eventEmitter = CDI.current().select(EventEmitter.class).get();

    @Override
    public void coffeeBreak(CoffeeBreakRequest coffeeBreakRequest) {

        var developerName = coffeeBreakRequest.developerName();
        var coffeType = coffeeBreakRequest.coffeeType();

        this.sendEvent(developerName, "🚶 " + developerName + " se dirige vers la machine à café...");
        Workflow.sleep(Duration.ofSeconds(1));

        this.sendEvent(developerName, "☕ Préparation d'un " + coffeType + "...");

        try {
            var makeCoffeeResponse = coffeeBreakActivities.makeCoffee(coffeeBreakRequest.toMakeCoffeeRequest());

            this.sendEvent(developerName, makeCoffeeResponse.message());
            this.sendEvent(developerName, "👂 Pendant ce temps, aux nouvelles du bureau...");
            Workflow.sleep(Duration.ofSeconds(1));

            var gossipResponse = coffeeBreakActivities.getGossip(coffeeBreakRequest.toGetGossipRequest());
            this.sendEvent(developerName, "💬 " + gossipResponse.gossip());

            Workflow.sleep(Duration.ofSeconds(1));
            this.sendEvent(developerName, "😋 " + developerName + " déguste son café...");
            this.sendEvent(developerName, "✨ Pause café terminée ! Energie rechargée.");

        } catch (TemporalFailure temporalFailure) {
            if (temporalFailure.getCause() instanceof ApplicationFailure applicationFailure) {
                this.sendEvent(developerName, applicationFailure.getType());
            }
            this.sendEvent(developerName, "😢 Retour au bureau sans café...");
            throw temporalFailure;
        }
    }

    private void sendEvent(String developerName, String message) {
        Workflow.sideEffect(Void.class, () -> {
            this.eventEmitter.emit(Event.of(developerName, message));
            return null;
        });
    }
}
