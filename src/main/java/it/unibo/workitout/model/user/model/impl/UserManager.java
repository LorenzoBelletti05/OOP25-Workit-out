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
     * @param strategy the strategy for calculate the BMR
     * @param currentUser the user profile to manage
     */
    public UserManager(final BMRCalculatorStrategy strategy, final UserProfile currentUser) {
        this.strategy = strategy;
        if (currentUser != null) {
            this.currentUser = new UserProfile(currentUser);
        } else {
            this.currentUser = null;
        }
    }

    /**
     * Sets the strategy for the BMR calculator.
     * 
     * @param strategy the strategy of the BMR calculator
     */
    public void setStrategy(final BMRCalculatorStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Calculates the BMR with the selected strategy of the current user.
     * 
     * @return the calculate value of BMR, Basal Methabolic Rate
     */
    public double getBMR() {
        return this.strategy.calculateBMR(currentUser);
    }

    /**
     * @return the TDEE, Total Daily Energy Expenditure.
     */
    public double getTDEE() {
        return getBMR() * currentUser.getActivityLevel().getMultiplier();
    }

    /**
     * @return the user profile managed by this class
     */
    public UserProfile getUserProfile() {
        if (this.currentUser == null) {
            return null;
        }
        return new UserProfile(this.currentUser);
    }

    /**
     * Calculates the daily calories target.
     * 
     * @return the target calories calculated based on the User Goal
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
     * Calculate the grams for each macronutrients.
     * 
     * @return the total amount of nutrients
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

    /**
     * Adds the nutritional values of food consumed by the user.
     * 
     * @param calories the amount of calories to add
     * @param carbs the amount of carbs to add
     * @param proteins the amount of proteins to add
     * @param fats the amount of fats to add
     */
    public void addConsumedFood(final double calories, final double carbs, final double proteins, final double fats) {
        final double currentCalories = this.currentUser.getConsumedCalories();
        this.currentUser.setConsumedCalories(currentCalories + calories);

        final double currentCarbs = this.currentUser.getConsumedCarbs();
        this.currentUser.setConsumedCarbs(currentCarbs + carbs);

        final double currentProteins = this.currentUser.getConsumedProteins();
        this.currentUser.setConsumedProteins(currentProteins + proteins);

        final double currentFats = this.currentUser.getConsumedFats();
        this.currentUser.setConsumedFats(currentFats + fats);
    }

    /**
     * @return the total consumed calories from the UserProfile
     */
    public double getConsumedCalories() {
        return this.currentUser.getConsumedCalories();
    }

    /**
     * @return the total consumed carbs from the UserProfile
     */
    public double getConsumedCarbs() {
        return this.currentUser.getConsumedCarbs();
    }

    /**
     * @return the total consumed proteins from the UserProfile
     */
    public double getConsumedProteins() {
        return this.currentUser.getConsumedProteins();
    }

    /**
     * @return the total consumed fats from the UserProfile
     */
    public double getConsumedFats() {
        return this.currentUser.getConsumedFats();
    }
}
