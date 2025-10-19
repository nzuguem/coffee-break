package me.nzuguem.coffee.simulator.service;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import me.nzuguem.coffee.simulator.models.CoffeeBreakRequest;
import me.nzuguem.coffee.simulator.models.Event;
import me.nzuguem.coffee.simulator.service.workflow.CoffeBreakWorkflow;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestStreamElementType;

import java.time.Duration;
import java.util.UUID;

@Path("/coffee-break")
public class CoffeeBreakSimulator {

    @ConfigProperty(name = "quarkus.temporal.worker.task-queue")
    private String taskQueue;

    private final Sse sse;
    private final WorkflowClient workflowClient;
    private final EventEmitter eventEmitter;

    public CoffeeBreakSimulator(Sse sse, WorkflowClient workflowClient, EventEmitter eventEmitter) {
        this.sse = sse;
        this.workflowClient = workflowClient;
        this.eventEmitter = eventEmitter;
    }

    @Channel("events")
    Multi<Event> events;

    @Path("/sse")
    @GET
    @RestStreamElementType(MediaType.TEXT_PLAIN)
    public Multi<OutboundSseEvent> stream() {
        return events
                .onItem().transformToUniAndConcatenate(event ->
                        Uni.createFrom().item(event)
                                .onItem().delayIt().by(Duration.ofSeconds(1))
                )
                .map(event -> sse.newEventBuilder()
                    .name("event")
                    .id(event.developerName())
                    .mediaType(MediaType.TEXT_PLAIN_TYPE)
                    .data(event.message())
                    .build()
                );
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<Void> coffeeBreak(CoffeeBreakRequest coffeeBreakRequest) {

        try {
            var coffeBreakWorkflow = this.workflowClient.newWorkflowStub(CoffeBreakWorkflow.class,
                    WorkflowOptions.newBuilder()
                            .setWorkflowId("%s-%s".formatted(coffeeBreakRequest.developerName(), UUID.randomUUID().toString()))
                            .setTaskQueue(taskQueue)
                            .build());

            WorkflowClient.start(coffeBreakWorkflow::coffeeBreak, coffeeBreakRequest);

            return RestResponse.accepted();
        } catch (Exception e) {
            Log.error("Error of coffe break", e);
            this.eventEmitter.emit(Event.of(coffeeBreakRequest.developerName(), "💥 Error of coffe break"));
            return RestResponse.serverError();
        }
    }
}
