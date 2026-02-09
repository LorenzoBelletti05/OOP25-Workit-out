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
     * Return the weight used in the exercise.
     * 
     * @return the weight in kg.
     */
    double getWeight();

    /**
     * Return the number of sets planned for the exercise.
     * 
     * @return the number of sets.
     */
    int getSets();

    /**
     * Return the number of repetition per set.
     * 
     * @return the number of repetitions.
     */
    int getReps();

}
