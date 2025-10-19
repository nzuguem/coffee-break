package me.nzuguem.coffee.simulator.models;

import java.time.LocalDateTime;

public record GetGossipResponse(
        String gossip,
        String listener,
        String timestamp
) {

    public static GetGossipResponse of(String gossip, String listener) {
        return new GetGossipResponse(gossip, listener, LocalDateTime.now().toString());
    }
}
