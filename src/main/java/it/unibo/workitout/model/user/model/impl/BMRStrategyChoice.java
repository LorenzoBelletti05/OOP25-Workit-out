package it.unibo.workitout.model.user.model.impl;

import it.unibo.workitout.model.user.model.contracts.BMRCalculatorStrategy;

/**
 * Represents the user strategy choice.
 */
public enum BMRStrategyChoice {
    MIFFLIN("Mifflin-St Jeor (More precise formula)", new MifflinStJeorStrategy()),
    HARRIS("Harris Benedict (Classic formula)", new HarrisBenedictStrategy());

    private final String description;
    private final BMRCalculatorStrategy strategy;

    /**
     * Constructor of the enum BMRStrategyChoice.
     * 
     * @param description is the description of strategy
     * @param strategy is the selected strategy
     */
    BMRStrategyChoice(final String description, final BMRCalculatorStrategy strategy) {
        this.description = description;
        this.strategy = strategy;
    }

    /**
     * Return the strategy used for BMR calculation.
     * 
     * @return the BMR strategy calculator
     */
    public BMRCalculatorStrategy getStrategy() {
        return strategy;
    }

    /**
     * @return the description of selected strategy
     */
    @Override
    public String toString() {
        return description;
    }

}
