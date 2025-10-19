package me.nzuguem.coffee.simulator.service.workflow;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import me.nzuguem.coffee.simulator.models.GetGossipRequest;
import me.nzuguem.coffee.simulator.models.GetGossipResponse;
import me.nzuguem.coffee.simulator.models.MakeCoffeeRequest;
import me.nzuguem.coffee.simulator.models.MakeCoffeeResponse;

@ActivityInterface
public interface CoffeeBreakActivities {

    @ActivityMethod(name = "makeCoffee")
    MakeCoffeeResponse makeCoffee(MakeCoffeeRequest makeCoffeeRequest);

    @ActivityMethod(name = "getGossip")
    GetGossipResponse getGossip(GetGossipRequest getGossipRequest);
}
