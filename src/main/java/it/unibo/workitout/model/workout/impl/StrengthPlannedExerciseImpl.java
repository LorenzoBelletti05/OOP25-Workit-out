package it.unibo.workitout.model.workout.impl;

import it.unibo.workitout.model.workout.contracts.StrengthPlannedExercise;

/**
 * Specific class for strenght based exercise
 */
public final class StrengthPlannedExerciseImpl extends PlannedExerciseImpl implements StrengthPlannedExercise {

    private final int sets;
    private final int reps;
    private final double weight;

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

    @Override
    public int getSets() {
        return this.sets;
    }

    @Override
    public int getReps() {
        return this.reps;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public double getVolume() {
        return VolumeCalculator.calculateVolume(sets, reps, weight);
    }

}
