package it.unibo.workitout.model.user.model.impl;

import it.unibo.workitout.model.user.model.contracts.BMRCalculatorStrategy;

/**
 * Manage the user profile.
 */
public final class UserManager {

    private BMRCalculatorStrategy strategy;
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
     * @param strategy set the strategy of the BMR calculator.
     */
    public void setStrategy(final BMRCalculatorStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * @return the BMR.
     */
    public double getBMR() {
        return strategy.calculateBMR(currentUser);
    }

    /**
     * @return the TDEE, Total Daily Energy Expenditure.
     */
    public double getTDEEE() {
        return getBMR() * currentUser.getActivityLevel().getMultiplier();
    }

    /**
     * @return the target calories calculated based on the UserGoal
     */
    public double getDailyCalories() {
        final double tdee = getTDEEE();
        final UserGoal goal = currentUser.getGoal();
        final int calories = 500;
        final int halfCalories = calories / 2;

        switch (goal) {
            case LOSE_WEIGHT:
                return tdee - calories;
            case GAIN_WEIGHT:
                return tdee + calories;
            case BUILD_MUSCLE:
                return tdee + halfCalories;
            default:
                return tdee;
        }
    }

    /**
     * Calclate the grams for each macronutrients.
     * 
     * @return the toal of nutrients.
     */
    public NutritionalTarget getMacronutrients() {
        final double totalCalories = getDailyCalories();
        final UserGoal goal = currentUser.getGoal();
        final double carbsGrams = totalCalories * goal.getProteinRatio() / 4;
        final double proteinsGrams = totalCalories * goal.getProteinRatio() / 4;
        final double fatsGrams = totalCalories * goal.getProteinRatio() / 9;

        return new NutritionalTarget(carbsGrams, proteinsGrams, fatsGrams);
    }
}
