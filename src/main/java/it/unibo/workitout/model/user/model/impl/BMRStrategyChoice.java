package it.unibo.workitout.model.user.model.impl;

import it.unibo.workitout.model.user.model.contracts.BMRCalculatorStrategy;

public enum BMRStrategyChoice {
    MIFFLIN("Mifflin-St Jeor (More precise formula)", new MifflinStJeorStrategy()),
    HARRIS("Harris Benedict (Classic formula)", new HarrisBenedictStrategy());

    private final String description;
    private final BMRCalculatorStrategy strategy;

    private BMRStrategyChoice(final String description, final BMRCalculatorStrategy strategy) {
        this.description = description;
        this.strategy=strategy;
    }

    public BMRCalculatorStrategy getStrategy(){
        return strategy;
    }

    @Override
    public String toString() {
        return description;
    }

}
