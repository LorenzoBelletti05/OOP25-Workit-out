package it.unibo.workitout.model.workout.contracts;

import java.util.Set;

import it.unibo.workitout.model.workout.impl.WorkoutSheet;

/**
 * Interface that representing the entire workout for the user
 * based on his preferencies.
 * 
 * <p>
 * Is a container for all training "daily" session, each one
 * is a WorkoutSheet provided untill the user is satisfied.
 * </p>
 */
public interface WorkoutPlan {

    /**
     * Return the hole exercise in a session.
     * 
     * @return an unmodifiable set of all session.
     */
    Set<WorkoutSheet> getSheets();

    /**
     * Utility methot that return all the exercises from each sheets.
     * 
     * @return the planned exercises in the plan.
     */
    Set<PlannedExercise> getAllExercise();

    /**
     * Calculate and return the total calories for the plan.
     * 
     * @return sum of all the burned calories from all exercises.
     */
    double getTotalBurnedCalories();

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
     * It aggregates the volume training only for strnght exercise.
     * 
     * @return total volume of exercise in the plan.
     */
    double getTotalStrenghtVolume();



}
