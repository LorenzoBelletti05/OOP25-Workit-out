package it.unibo.workitout.model.user.model.contracts;

import it.unibo.workitout.model.user.model.impl.UserProfile;

/**
 * This interface defining the strategy for BMR (Basal Metaboli Rate) calculation.
 */
@FunctionalInterface
public interface BMRCalculatorStrategy {
    /**
     * Calculate the BMR based on the User Profile.
     * 
     * @param up the User Profile.
     * @return the calculated BMR.
     */
    double calculateBMR(UserProfile up);
}
