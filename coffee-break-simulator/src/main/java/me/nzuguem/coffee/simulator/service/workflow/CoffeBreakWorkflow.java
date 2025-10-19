package me.nzuguem.coffee.simulator.service.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import me.nzuguem.coffee.simulator.models.CoffeeBreakRequest;

@WorkflowInterface
public interface CoffeBreakWorkflow {

    @WorkflowMethod(name = "coffeeBreak")
    void coffeeBreak(CoffeeBreakRequest coffeeBreakRequest);
}
