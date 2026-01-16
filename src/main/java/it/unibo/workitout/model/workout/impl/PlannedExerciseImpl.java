package it.unibo.workitout.model.workout.impl;

import it.unibo.workitout.model.workout.contracts.PlannedExercise;

/**
 * Implementation of planned exercise.
 * 
 * <p>
 * It decorates the {@link Exercise} with specific training parameters
 * as a set, repetitions, and weight/intensity subdivided by the two sub-interface
 * from PlannedExercise.
 * 
 * The class will be abstract because it can implements or not the sub-interface.
 * </p>
 */
public final class PlannedExerciseImpl implements PlannedExercise {

    private Exercise exercise = null;
    private Integer minutes = 0;
    private Integer sets = 0;
    private Integer reps = 0;
    private double weight = 0.0;

    public PlannedExerciseImpl(
        final Exercise exercise, 
        final Integer minutes, 
        final Integer sets, 
        final Integer reps, 
        final double weight
    ) {
        this.exercise = exercise;
        this.minutes = minutes;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }


    @Override
    public Exercise getExercise() {
        return this.exercise;
    }

    @Override
    public double getCalculatedBurnedCalories() {
        final double burnedCaloriesForTime = this.getExercise().calorieBurned(minutes);
        return burnedCaloriesForTime;
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
    public double getVolumeExercise() {
        return VolumeCalculator.calculateVolume(getSets(), getReps(), getWeight());
    }

}
