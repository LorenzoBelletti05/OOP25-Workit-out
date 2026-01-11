package it.unibo.workitout.controller.wiki.contracts;

import java.util.List;

import it.unibo.workitout.model.food.api.Meal;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.workout.impl.Exercise;

/**
 * Interface for Wiki Controller.
 */
public interface WikiController {
    /**
     * Starts the Wiki part.
     */
    void start();

    /**
     * New view with smart suggestions.
     * 
     * @param user the user profile.
     * @param exercises the list of exercises for today.
     * @param meal the last meal.
     */
    void showSmartSuggestions(UserProfile user, List<Exercise> exercises, Meal meal);
}
