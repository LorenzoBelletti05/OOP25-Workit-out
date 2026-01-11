package it.unibo.workitout.model.wiki.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.workitout.model.food.api.Meal;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.wiki.contracts.SmartSuggestion;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.contracts.WikiContent;
import it.unibo.workitout.model.workout.impl.Exercise;

/**
 * Implementation of SmartSuggestion.
 */
public class SmartSuggestionImpl implements SmartSuggestion {

    /**
     * Method for get the filtered content.
     * 
     * @param wiki the wiki model.
     * @param user the user profile.
     * @param exercises the list of exercises for today.
     * @param meal the last meal.
     * @return filtered contents.
     */
    @Override
    public Set<WikiContent> suggest(final Wiki wiki, final UserProfile user, final List<Exercise> exercises, final Meal meal) {
        final Set<String> tags = new HashSet<>();
        tags.add(user.getGoal().name());

        if (exercises != null) {
            exercises.forEach(e -> tags.add(e.getName()));
        }

        if (meal != null) {
            tags.add("Nutrition");
            if (meal.getFood().size() > 3) {
                tags.add("Dieta");
            }
        }

        return wiki.getContents().stream()
            .filter(content -> content.getTags().stream()
            .anyMatch(tag -> tags.stream()
            .anyMatch(tag::equalsIgnoreCase)))
            .collect(Collectors.toSet());
    }
}
