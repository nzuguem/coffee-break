package me.nzuguem.coffee.simulator.models;

import java.time.LocalDateTime;

public record GetGossipRequest(
        String listener
) {

    public static GetGossipRequest of(String listener) {
        return new GetGossipRequest(listener);
    }
}
