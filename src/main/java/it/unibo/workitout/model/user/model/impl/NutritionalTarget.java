package it.unibo.workitout.model.user.model.impl;

/**
 * This class represents the daily target of macronutrients.
 */
public class NutritionalTarget {
    private final double carbsG;
    private final double proteinsG;
    private final double fatsG;

    /**
     * The constructor for the Nutritional Target of macro-nutrients.
     * 
     * @param carbsG        grams of carbs to consume
     * @param proteinsG     grams of proteins to consume
     * @param fatG          grams of fats to consume
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
        return carbsG;
    }

    /**
     * @return the amount of proteins to consume
     */
    public double getProteinsG() {
        return proteinsG;
    }

    /**
     * @return the amount of fats to consume
     */
    public double getFatsG() {
        return fatsG;
    }
}
