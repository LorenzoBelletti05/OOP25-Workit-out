package it.unibo.workitout.model.wiki.contracts;

import java.util.List;
import java.util.Set;

import it.unibo.workitout.model.food.api.Meal;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.workout.impl.Exercise;

/**
 * Interface for smart suggestion.
 */
@FunctionalInterface
public interface SmartSuggestion {

    /**
     * Method for get the filtered content.
     * 
     * @param wiki the wiki model.
     * @param user the user profile.
     * @param exercises the list of exercises for today.
     * @param meal the last meal.
     * @return filtered contents.
     */
    Set<WikiContent> suggest(Wiki wiki, UserProfile user, List<Exercise> exercises, Meal meal);
}
