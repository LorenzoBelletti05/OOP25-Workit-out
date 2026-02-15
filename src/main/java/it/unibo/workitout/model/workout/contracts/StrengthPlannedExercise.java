package it.unibo.workitout.model.workout.contracts;

/**
 * A sub-interface of PlannedExercise which containe all method in common.
 * 
 * <p>
 * This interface define the weight method for all the exercise where is
 * involed strenght.
 * </p>
 */
public interface StrengthPlannedExercise extends PlannedExercise {

    /**
     * @return the weight in kg
     */
    double getWeight();

    /**
     * @return the number of sets
     */
    int getSets();

    /**
     * @return the number of repetitions
     */
    int getReps();

}
