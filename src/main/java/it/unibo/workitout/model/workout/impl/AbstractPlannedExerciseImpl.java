package it.unibo.workitout.model.workout.impl;

import it.unibo.workitout.model.workout.contracts.PlannedExercise;

/**
 * Implementation of planned exercise.
 * 
 * <p>
 * It decorates the {@link Exercise} with specific training parameters.
 * Set, repetitions, and weight/intensity subdivided by the two sub-interface
 * from PlannedExercise.
 * 
 * The class will be abstract because it can implements or not the sub-interface.
 * </p>
 */
public abstract class AbstractPlannedExerciseImpl implements PlannedExercise {

    private final Exercise exercise;
    private final Integer minutes;
    private boolean completed;

    /**
     * Costructor that gived the exercise and the minutes save it in the filed.
     * 
     * @param exercise the raw exercise.
     * 
     * @param minutes the minutes for that exercise.
     * 
     */
    public AbstractPlannedExerciseImpl(
        final Exercise exercise, 
        final Integer minutes
    ) {
        this.exercise = exercise;
        this.minutes = minutes;
    }

    /**
     * Give the name passed and saved before.
     */
    @Override
    public Exercise getExercise() {
        return this.exercise;
    }

    /**
     * Give the minutes passed and saved before.
     */ 
    @Override
    public Integer getMinutes() {
        return this.minutes;
    }

    /**
     * Give the burned calories calculated on the data.
     */
    @Override
    public double getBurnedCalories() {
        //used this variable for debug, could have just return from this.get....
        final double burnedCaloriesForTime = this.getExercise().calorieBurned(minutes);
        return burnedCaloriesForTime;
    }

    /**
     * Give the name of the exe passed and saved before.
     */
    @Override
    public String getName() {
        return this.exercise.getName();
    }

    /**
     * It set the boolean variable of the exercise to make it result as complited.
     */
    @Override
    public void setCompletedExercise(final boolean compleExerecise) {
        this.completed = compleExerecise;
    }

    /**
     * It return the status of the boolean variable completed exercise. 
     */
    @Override
    public boolean isComplited() {
        return this.completed;
    }

}
