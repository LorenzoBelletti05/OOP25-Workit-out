package it.unibo.workitout.model.user.model.impl;

/**
 * Represents the user's physical activity level.
 */
public enum ActivityLevel {
    VERY_LOW(1.2),      //Sedentary 0 days/week
    LOW(1.375),         //Light Exercise 1-2 days/week
    MODERATE(1.55),     //Moderate Exercise 3-5 days/week
    HIGH(1.725),        //Heavy Exercise 6-7 days/week
    VERY_HIGH(1.9);     //Athlete >7 days/week

    private final double multiplier;

    ActivityLevel(final double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
