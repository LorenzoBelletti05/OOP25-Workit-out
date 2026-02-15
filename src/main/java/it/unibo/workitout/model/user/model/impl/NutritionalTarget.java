package it.unibo.workitout.model.user.model.impl;

/**
 * This class represents the daily target of macronutrients.
 */
public class NutritionalTarget {
    private final double carbsG;
    private final double proteinsG;
    private final double fatsG;

    /**
     * The constructor to set the macro-nutrients.
     * 
     * @param carbsG grams of carbs to consume
     * @param proteinsG grams of proteins to consume
     * @param fatG grams of fats to consume
     */
    public NutritionalTarget(final double carbsG, final double proteinsG, final double fatG) {
        this.carbsG = carbsG;
        this.proteinsG = proteinsG;
        this.fatsG = fatG;
    }

    /**
     * @return the amount of carbs to consume
     */
    public double getCarbsG() {
        return this.carbsG;
    }

    /**
     * @return the amount of proteins to consume
     */
    public double getProteinsG() {
        return this.proteinsG;
    }

    /**
     * @return the amount of fats to consume
     */
    public double getFatsG() {
        return this.fatsG;
    }

    /**
     * @return string of daily target
     */
    @Override
    public String toString() {
        return "Target: " + this.carbsG + "g carbs, " + this.proteinsG + "g proteins, " + this.fatsG + "g fats.";
    } 
}
