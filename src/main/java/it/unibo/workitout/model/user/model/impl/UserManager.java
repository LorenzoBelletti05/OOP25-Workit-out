package it.unibo.workitout.model.user.model.impl;

import it.unibo.workitout.model.user.model.contracts.BMRCalculatorStrategy;

/**
 * Manage the user profile.
 */
public final class UserManager {

    private final BMRCalculatorStrategy strategy;
    private final UserProfile currentUser;

    /**
     * Creates a new User Manager.
     * 
     * @param strategy the strategy for calculate the BMR.
     * @param currentUser the user profile to manage.
     */
    public UserManager(final BMRCalculatorStrategy strategy, final UserProfile currentUser) {
        this.strategy = strategy;
        this.currentUser = currentUser;
    }

    /**
     * @return the BMR.
     */
    public double getBMR() {
        return strategy.calculateBMR(currentUser);
    }
}
