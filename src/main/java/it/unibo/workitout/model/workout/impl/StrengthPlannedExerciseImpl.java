package it.unibo.workitout.model.workout.impl;

import it.unibo.workitout.model.workout.contracts.StrengthPlannedExercise;

/**
 * Specific exercise type class, which extends the abstract class PlannedExercise with his behavior.
 */
public final class StrengthPlannedExerciseImpl extends PlannedExerciseImpl implements StrengthPlannedExercise {

    private final int sets;
    private final int reps;
    private final double weight;

    /**
     * Costrutctor that save data in field and give to the super class (plannedExercise) the data required from his costrutor.
     * 
     * @param exercise the raw exercise.
     * 
     * @param minutes the minute of the exercise.
     * 
     * @param sets the specific set for Strenght exe.
     * 
     * @param reps the specific reps for Strenght exe.
     * 
     * @param weight the specific weight for Strenght exe.
     * 
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
