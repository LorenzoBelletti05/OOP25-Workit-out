package it.unibo.workitout.model.workout.impl;

import it.unibo.workitout.model.workout.contracts.PlannedExercise;

/**
 * Implementation of {@link PlannedExercise}.
 * 
 * <p>
 * It decorates the {@link Exercise} with specific training parameters.
 * Set, repetitions, and weight/intensity divided in two interfaces.
 * 
 * The class will be abstract because it can implements or not the interfaces.
 * </p>
 */
public abstract class AbstractPlannedExerciseImpl implements PlannedExercise {

    private final Exercise exercise;
    private final Integer minutes;
    private boolean completed;

    /**
     * Constructor to set the exercise and the minutes.
     * 
     * @param exercise raw
     * 
     * @param minutes the minutes for that exercise
     */
    public AbstractPlannedExerciseImpl(
        final Exercise exercise, 
        final Integer minutes
    ) {
        this.exercise = exercise;
        this.minutes = minutes;
    }

    /**
     * @return the volume
     */
    @Override
    public abstract double getVolume();

    /** {@inheritDoc} */
    @Override
    public Exercise getExercise() {
        return this.exercise;
    }

    /** {@inheritDoc} */
    @Override
    public Integer getMinutes() {
        return this.minutes;
    }

    /**
     * @return the burned calories based on the minutes.
     */
    @Override
    public double getBurnedCalories() {
        return this.getExercise().calorieBurned(minutes);
    }

    /**
     * @return the name of the exercise
     */
    @Override
    public String getName() {
        return this.exercise.getName();
    }

    /** {@inheritDoc} */
    @Override
    public void setCompletedExercise(final boolean completedExerecise) {
        this.completed = completedExerecise;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isComplited() {
        return this.completed;
    }

}
