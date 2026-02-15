package it.unibo.workitout.model.workout.contracts;

import it.unibo.workitout.model.workout.impl.Exercise;

/**
 * This interface is the contracts for a
 * completed single exercise from the Exercise.java class's data.
 * 
 * <p>
 * Using the "static" exercise data from the json file and combined to the one based
 * on other information gived from the user modul to associate and give
 * the best exercise to the WorkoutSheet.
 * </p>
 */
public interface PlannedExercise extends WorkoutFunction {

    /**
     * @return the exercise based on the class {@link Exercise}
     */
    Exercise getExercise();

    /**
     * @return the minutes.
     */
    Integer getMinutes();

    /**
     * @param statusExercise that specify if is completed or not.
     */
    void setCompletedExercise(boolean statusExercise);

    /**
     * @return a boolean state that tell if the exercise is completed.
     */
    boolean isComplited();

}
