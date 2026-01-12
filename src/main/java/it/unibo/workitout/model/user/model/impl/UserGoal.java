package it.unibo.workitout.model.user.model.impl;

/**
 * Represent the primary objective of the user.
 */
public enum UserGoal {
    LOSE_WEIGHT(0.4, 0.3, 0.3),
    MAINTAIN_WEIGHT(0.5, 0.25, 0.25),
    GAIN_WEIGHT(0.55, 0.25, 0.20),
    BUILD_MUSCLE(0.5, 0.3, 0.2);

    private final double carbRatio;
    private final double proteinRatio;
    private final double fatRatio;

    UserGoal(final double carbRatio, final double proteinRatio, final double fatRatio) {
        this.carbRatio = carbRatio;
        this.proteinRatio = proteinRatio;
        this.fatRatio = fatRatio;
    }

    /**
     * @return the Ratio of the carbs
     */
    public double getCarbRatio() {
        return carbRatio;
    }

    /**
     * @return the Ratio of the proteins
     */
    public double getProteinRatio() {
        return proteinRatio;
    }

    /**
     * @return the Ratio of the fats
     */
    public double getFatratio() {
        return fatRatio;
    }
}
