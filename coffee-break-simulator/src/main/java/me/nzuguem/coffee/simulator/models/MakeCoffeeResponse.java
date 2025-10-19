package me.nzuguem.coffee.simulator.models;

import java.util.concurrent.ThreadLocalRandom;

public record MakeCoffeeResponse(
        CoffeeQuality coffeeQuality,
        String message,
        CoffeeType coffeeType,
        String temperature
) {
    private MakeCoffeeResponse(CoffeeQuality coffeeQuality, CoffeeType coffeeType) {
        this(
                coffeeQuality,
                coffeeQuality.description(),
                coffeeType,
                ThreadLocalRandom.current().nextInt(10) + 65 + "°C"
        );
    }

    public static MakeCoffeeResponse of(CoffeeQuality coffeeQuality, CoffeeType coffeeType) {
        return new MakeCoffeeResponse(coffeeQuality, coffeeType);
    }
}
