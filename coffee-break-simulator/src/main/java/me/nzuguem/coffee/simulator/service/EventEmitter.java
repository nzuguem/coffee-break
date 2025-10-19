package me.nzuguem.coffee.simulator.service;

import io.quarkus.arc.Unremovable;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import me.nzuguem.coffee.simulator.models.Event;
import org.eclipse.microprofile.reactive.messaging.Channel;

@Unremovable
@ApplicationScoped
public class EventEmitter {

    @Channel("events")
    private MutinyEmitter<Event> eventMutinyEmitter;

    public void emit(Event event) {
        this.eventMutinyEmitter.sendAndForget(event);
    }
}
