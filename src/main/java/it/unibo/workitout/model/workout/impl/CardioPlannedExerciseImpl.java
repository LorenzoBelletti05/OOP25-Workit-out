package it.unibo.workitout.model.workout.impl;

import it.unibo.workitout.model.workout.contracts.CardioPlannedExercise;

public final class CardioPlannedExerciseImpl extends PlannedExerciseImpl implements CardioPlannedExercise {

    private double distance;

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
