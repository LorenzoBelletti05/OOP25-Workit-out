package it.unibo.workitout.model.workout.impl;

import it.unibo.workitout.model.workout.contracts.CardioPlannedExercise;

/**
 * Specific exercise type class, which extends the abstract class PlannedExercise with his behavior.
 */
public final class CardioPlannedExerciseImpl extends AbstractPlannedExerciseImpl implements CardioPlannedExercise {

    /**
     * Private final field which indicate the distance in km.
     */
    private final double distance;

    /**
     * Contructor to set the data when a exercise is create.
     * 
     * @param exercise the raw exercise.
     * @param minutes the minutes of workout.
     * @param distance the distance.
     */
    public CardioPlannedExerciseImpl(
        final Exercise exercise,
        final Integer minutes,
        final double distance
    ) {
        super(exercise, minutes);

        this.distance = distance;
    }

    @Override
    public double getVolume() {
        return this.distance; //the volume for cardio is 0 because ther's no weight but can be return also the distance.
    }

    @Override
    public double getDistance() {
        return this.distance;
    }

}
