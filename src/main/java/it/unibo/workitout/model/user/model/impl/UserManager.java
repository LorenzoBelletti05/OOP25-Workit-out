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
    public double getTDEE() {
        return getBMR() * currentUser.getActivityLevel().getMultiplier();
    }

    public UserProfile getUserProfile() {
        return this.currentUser;
    }

    /**
     * @return the target calories calculated based on the UserGoal
     */
    public double getDailyCalories() {
        double totalCalories = getTDEE();
        final UserGoal goal = currentUser.getUserGoal();
        final int calories = 500;
        final double halfCalories = calories / 2;

        switch (goal) {
            case LOSE_WEIGHT:
                totalCalories -= calories;
                break;
            case GAIN_WEIGHT:
                totalCalories += calories;
                break;
            case BUILD_MUSCLE:
                totalCalories += halfCalories;
                break;
            default:
                break;
        }
        totalCalories += this.currentUser.getBurnedCalories();
        return totalCalories;
    }

    /**
     * Calclate the grams for each macronutrients.
     * 
     * @return the toal of nutrients.
     */
    public NutritionalTarget getMacronutrients() {
        final double totalCalories = getDailyCalories();
        final UserGoal goal = currentUser.getUserGoal();
        final double carbsGrams = totalCalories * goal.getCarbRatio() / 4;
        final double proteinsGrams = totalCalories * goal.getProteinRatio() / 4;
        final double fatsGrams = totalCalories * goal.getFatRatio() / 9;

        return new NutritionalTarget(carbsGrams, proteinsGrams, fatsGrams);
    }

    /**
     * Add calories to the total burned.
     * 
     * @param burnedCalories the total calories to add
     */
    public void addBurnedCalories(final double burnedCalories) {
       final double totalCalories = this.currentUser.getBurnedCalories();
        this.currentUser.setBurnedCalories(totalCalories + burnedCalories);
    }
}
