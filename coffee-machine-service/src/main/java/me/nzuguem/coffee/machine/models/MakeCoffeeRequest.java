package me.nzuguem.coffee.machine.models;

public record MakeCoffeeRequest(
        String developerName,
        CoffeeType coffeeType
) {
}
