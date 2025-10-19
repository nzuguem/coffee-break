package me.nzuguem.coffee.simulator.models;

public record Event(
        String developerName,
        String message
) {

    public static Event of(String developerName, String message) {
        return new Event(developerName, message);
    }
}
