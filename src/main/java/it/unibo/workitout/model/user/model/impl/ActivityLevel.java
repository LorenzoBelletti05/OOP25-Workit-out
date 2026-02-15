package it.unibo.workitout.model.user.model.impl;

/**
 * Represents the user's physical activity level.
 */
public enum ActivityLevel {
    VERY_LOW(1.2, "Sedentary (No exercise)"),                       //Sedentary 0 days/week
    LOW(1.375, "Lightly Active (Exercise 1-2 days/week)"),          //Light Exercise 1-2 days/week
    MODERATE(1.55, "Moderate Active (Exercise 3-5 days/week)"),     //Moderate Exercise 3-5 days/week
    HIGH(1.725, "Very Active (Exercise 6-7 days/week)"),            //Heavy Exercise 6-7 days/week
    VERY_HIGH(1.9, "Extremely Active (Exercise 2x day)");           //Athlete 2x days

    private final double multiplier;
    private final String description;

    /**
     * Constructor of the enum Activitylevel.
     * 
     * @param multiplier is the multiplier for activity Level
     * @param description is the description of the current level
     */
    ActivityLevel(final double multiplier, final String description) {
        this.multiplier = multiplier;
        this.description = description;
    }

    /**
     * @return the multiplier of the activity level.
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * @return the description of activity level
     */
    @Override
    public String toString() {
        return description;
    }
}
