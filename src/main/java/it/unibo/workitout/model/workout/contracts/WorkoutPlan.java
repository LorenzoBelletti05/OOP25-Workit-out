package it.unibo.workitout.model.workout.contracts;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface that representing the entire workout for the user
 * based on his preferencies.
 * 
 * <p>
 * Is a container for all training "daily" session, each one
 * is a WorkoutSheet provided untill the user is satisfied.
 * </p>
 */
public interface WorkoutPlan extends WorkoutFunction {

    /**
     * Method that add the sheet to the plan.
     * 
     * @param sheet the sheet to add.
     *  
     */
    void addWorkSheet(WorkoutSheet sheet);

    /**
     * Return the hole exercise in a session.
     *
     * @return an unmodifiable set of all session.
     */
    Set<WorkoutSheet> getSheets();

    /**
     * Utility method that return all the exercises from each sheets.
     * 
     * @return the planned exercises in the plan.
     */
    Set<PlannedExercise> getAllExercise();

    /**
     * Return all the filter plan based on strenght.
     *
     * @return the plan based on strenght.
     */
    Set<StrengthPlannedExercise> getStrenghtExercise();

    /**
     * Return all the filter plan based on cardio.
     *
     * @return the plan based on cardio.
     */
    Set<CardioPlannedExercise> getCardiotExercise();

    /**
     * Public getter that return the class unmoodifiable structure data.
     * 
     * @return the unmodifiable set of Workoutsheet.
     */
    Map<LocalDate, WorkoutSheet> getWorkoutPlan();
}
