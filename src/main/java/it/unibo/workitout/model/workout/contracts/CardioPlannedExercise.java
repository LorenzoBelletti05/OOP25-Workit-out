package it.unibo.workitout.model.workout.contracts;

/**
 * A sub-interface of PlannedExercise which contains all method in common.
 * 
 * <p>
 * This interface define the cardio method for all the exercise where is
 * involed cardio (long distance)
 * </p>
 */
public interface CardioPlannedExercise extends PlannedExercise {

    /**
     * @return the weight in kg
     */
    double getDistance();

}
