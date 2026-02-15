package it.unibo.workitout.model.workout.impl;

import it.unibo.workitout.model.workout.contracts.StrengthPlannedExercise;

/**
 * Specific exercise type class, which extends the abstract class PlannedExercise with his behavior.
 */
public final class StrengthPlannedExerciseImpl extends AbstractPlannedExerciseImpl implements StrengthPlannedExercise {

    private final int sets;
    private final int reps;
    private final double weight;

    /**
     * Construtctor to set data and set the super class {@link PlannedExerciseImpl} data required from his construtor.
     * 
     * @param exercise the raw exercise
     * 
     * @param minutes of the exercise
     * 
     * @param sets for Strenght exercise
     * 
     * @param reps for Strenght exercise
     * 
     * @param weight Strenght exercise
     */
    public StrengthPlannedExerciseImpl(
        final Exercise exercise, 
        final int minutes,
        final int sets,
        final int reps,
        final double weight
    ) {
        super(exercise, minutes);

        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    /** {@inheritDoc} */
    @Override
    public int getSets() {
        return this.sets;
    }

    /** {@inheritDoc} */
    @Override
    public int getReps() {
        return this.reps;
    }

    /** {@inheritDoc} */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /** {@inheritDoc} */
    @Override
    public double getVolume() {
        return VolumeCalculator.calculateVolume(sets, reps, weight);
    }

}
