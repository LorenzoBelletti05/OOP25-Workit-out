package it.unibo.workitout.model.workout.contracts;

import java.util.Map;
import java.util.Set;

/**
 * Interface that representing the entire workout for the user based on his preferencies.
 * 
 * <p>
 * Is a container for all training "daily" session, each one
 * is a WorkoutSheet provided untill the user is satisfied.
 * </p>
 */
public interface WorkoutPlan extends WorkoutFunction {

    /**
     * Method to add the sheet to the plan. 
     * 
     * @param dateNext in string
     * @param workoutSheet of the week
     */
    void addWorkSheet(String dateNext, WorkoutSheet workoutSheet);

    /**
     * @return an unmodifiable set of all session.
     */
    Set<WorkoutSheet> getSheets();

    /**
     * @return the planned exercises in the plan.
     */
    Set<PlannedExercise> getAllExercise();

    /**
     * @return the plan based on strenght.
     */
    Set<StrengthPlannedExercise> getStrenghtExercise();

    /**
     * @return the plan based on cardio.
     */
    Set<CardioPlannedExercise> getCardiotExercise();

    /**
     * @return the unmodifiable set of Workoutsheet.
     */
    Map<String, WorkoutSheet> getWorkoutPlan();

}
