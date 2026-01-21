package it.unibo.workitout.model.workout.contracts;

/**
 * A sub-interface of PlannedExercise which containe all method in common.
 * 
 * <p>
 * This interface define the cardio method for all the exercise where is
 * involed cardio (long distance)
 * </p>
 */
public interface CardioPlannedExercise extends PlannedExercise {

    /**
     * Return the distance of the exercise.
     * 
     * @return the weight in kg.
     */
    double getDistance();

}
