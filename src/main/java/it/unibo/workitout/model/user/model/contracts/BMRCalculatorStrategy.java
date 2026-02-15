package it.unibo.workitout.model.user.model.contracts;

import it.unibo.workitout.model.user.model.impl.UserProfile;

/**
 * This interface defining the strategy for BMR (Basal Metabolic Rate) calculation.
 */
@FunctionalInterface
public interface BMRCalculatorStrategy {
    /**
     * Calculate the BMR based on the current user profile.
     * 
     * @param userProfile the current user Profile
     * @return the calculated BMR
     */
    double calculateBMR(UserProfile userProfile);
}
