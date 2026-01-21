package it.unibo.workitout.model.workout.impl;

/**
 * Utility class in calculating training data.
 */
public class VolumeCalculator {

    /**
     * A static method that calculate the volume of the exercise base on the parameters.
     * @param sets number of sets.
     * @param reps number of repetition for each sets.
     * @param weight the weight used for each sets and repetition (for the entire exercise).
     * @return the volume calculated based on the parameters gived.
     */
    public static double calculateVolume(
        final Integer sets, 
        final Integer reps, 
        final double weight
    ) {
        return (double) sets * reps * weight;
    }

}
