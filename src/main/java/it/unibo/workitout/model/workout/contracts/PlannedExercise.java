package it.unibo.workitout.model.workout.contracts;

import it.unibo.workitout.model.workout.impl.Exercise;

/**
 * This interface is the contracts for a
 * completed single exercise from the Exercise.java class's data.
 * 
 * <p>
 * Using the "static" exercise data from the csv file and combined to the one based
 * on other information gived from the user modul to associate and give
 * the best exercise to the WorkoutSheet.
 * </p>
 */
public interface PlannedExercise {

    /**
     * Reclaim the exercise.
     * 
     * @return the exercise based on the class {@link Exercise}
     */
    Exercise getExercise();

    /**
     * Give, based on the exercise data calculated during the performance of the exercise based the calories burned.
     * 
     * @return total calories burned as a double.
     */
    double getCalculatedBurnedCalories();

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

    /**
     * Return the actual exercise weight
     * 
     * @return the weight of the exercise
     */
    double getWeight();
}
