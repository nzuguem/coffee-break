package me.nzuguem.coffee.machine.models;

public enum CoffeeQuality {
    PERFECT("☕ Café parfait ! La température est idéale, la mousse est crémeuse. Chef d'œuvre !"),
    PERFECT_BARISTA("✨ Magnifique ! On dirait un café de barista professionnel."),
    GOOD("👍 Bon café, rien à redire. Solide performance de la machine."),
    GOOD_OK("😊 Café correct, ça fera l'affaire pour tenir jusqu'au lunch."),
    BURNT("🔥 Oups... un peu cramé. Quelqu'un a oublié de détartrer la machine ?"),
    BURNT_BITTER("😬 Café amer... très amer. Presque du charbon liquide."),
    WATERY("💧 C'est... de l'eau chaude colorée ? La machine manque de café."),
    WATERY_LIGHT("😕 Café ultra-léger. On pourrait lire le journal à travers."),
    MACHINE_ERROR("⚠️ La machine fait un bruit bizarre et tremble !"),
    NO_MILK("🥛 Quelqu'un a fini le lait ! Espresso forcé."),
    PERFECT_TIMING("⏰ Timing parfait ! Le café est prêt pile au bon moment."),
    CELEBRITY_MOMENT("🌟 Le CEO passe justement et complimente ton café !");

    private final String description;

    CoffeeQuality(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }
}
