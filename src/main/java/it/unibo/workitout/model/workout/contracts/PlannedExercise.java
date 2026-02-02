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
public interface PlannedExercise extends WorkoutFunction {

    /**
     * Reclaim the exercise.
     * 
     * @return the exercise based on the class {@link Exercise}
     */
    Exercise getExercise();

    /**
     * Get the minutes of the exercise.
     * 
     * @return the minutes.
     */
    Integer getMinutes();

    /**
     * Set the status of the exercise.
     * 
     * @param statusExercise that specify if is completed or not.
     */
    void setCompletedExercise(boolean statusExercise);

    /**
     * Return the status of the exercise.
     * 
     * @return a boolean state that tell if the exercise is completed.
     */
    boolean isComplited();   

}
