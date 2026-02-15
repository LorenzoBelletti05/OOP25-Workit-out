package it.unibo.workitout.model.user.model.impl;

/**
 * Represent the possible objectives for the user.
 */
public enum UserGoal {
    LOSE_WEIGHT(0.4, 0.3, 0.3, "Weight Loss"),
    MAINTAIN_WEIGHT(0.5, 0.25, 0.25, "Maintain Weight"),
    GAIN_WEIGHT(0.55, 0.25, 0.20, "Weight Gain"),
    BUILD_MUSCLE(0.5, 0.3, 0.2, "Muscle Gain");

    private final double carbRatio;
    private final double proteinRatio;
    private final double fatRatio;
    private final String description;

    /**
     * Constrcutor of UserGoal enum.
     * 
     * @param carbRatio is the ratio of carbs to consume, based on the final goal of the user
     * @param proteinRatio is the ratio of proteins to consume, based on the final goal of the user
     * @param fatRatio is the ratio of fats to consume, based on the final goal of the user
     * @param description is the description of the user goal
     */
    UserGoal(final double carbRatio, final double proteinRatio, final double fatRatio, final String description) {
        this.carbRatio = carbRatio;
        this.proteinRatio = proteinRatio;
        this.fatRatio = fatRatio;
        this.description = description;
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
    public double getFatRatio() {
        return fatRatio;
    }

    /**
     * @return the description of the goal
     */
    @Override
    public String toString() {
        return description;
    }
}
