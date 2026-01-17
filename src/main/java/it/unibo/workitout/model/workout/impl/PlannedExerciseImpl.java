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
public abstract class PlannedExerciseImpl implements PlannedExercise {

    private Exercise exercise = null;
    private Integer minutes = 0;

    public PlannedExerciseImpl(
        final Exercise exercise, 
        final Integer minutes
    ) {
        this.exercise = exercise;
        this.minutes = minutes;
    }

    @Override
    public Exercise getExercise() {
        return this.exercise;
    }

    @Override
    public Integer getMinutes() {
        return this.minutes;
    }

    @Override
    public double getBurnedCalories() {
        final double burnedCaloriesForTime = this.getExercise().calorieBurned(minutes); //used this variable for debug, could have just return from this.get....
        return burnedCaloriesForTime;
    }
}
