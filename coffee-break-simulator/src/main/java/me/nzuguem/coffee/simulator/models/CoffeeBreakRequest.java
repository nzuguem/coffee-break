package me.nzuguem.coffee.simulator.models;

public record CoffeeBreakRequest(
        String developerName,
        CoffeeType coffeeType
) {

    public MakeCoffeeRequest toMakeCoffeeRequest() {
        return new MakeCoffeeRequest(developerName, coffeeType);
    }

    public GetGossipRequest toGetGossipRequest() {
        return new GetGossipRequest(developerName);
    }
}
