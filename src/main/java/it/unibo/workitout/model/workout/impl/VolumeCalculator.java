package it.unibo.workitout.model.workout.impl;

/**
 * Utility class to calculate training data.
 */
final class VolumeCalculator {

    /**
     * Private constructor to prevent instantiation.
     */
    private VolumeCalculator() {

    }

    /**
     * A static method that calculate the volume of the exercise base on the parameters.
     * 
     * @param sets number of sets
     * 
     * @param reps number of repetition for each sets
     * 
     * @param weight the weight used for each sets and repetition (for the entire exercise)
     * 
     * @return the volume calculated based on the parameters gived.
     */
    static double calculateVolume(
        final Integer sets,
        final Integer reps,
        final double weight
    ) {
        return (double) sets * reps * weight;
    }

}
