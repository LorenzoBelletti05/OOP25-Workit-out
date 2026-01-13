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

    @Override
    public Exercise getExercise() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExercise'");
    }

    @Override
    public double getBurnedCalories() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBurnedCalories'");
    }

    @Override
    public int getSets() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSets'");
    }

    @Override
    public int getReps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReps'");
    }

}
