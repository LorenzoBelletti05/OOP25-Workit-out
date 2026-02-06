package it.unibo.workitout.model.wiki.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        final String goal = user.getUserGoal().name();
        final Stream<WikiContent> stream = wiki.getContents().stream();

        if (meal != null && exercises == null) {
            return stream
                .filter(this::isNutritionContent) 
                .filter(c -> c.getTags().contains(goal)) 
                .collect(Collectors.toSet());
        }

        if (exercises != null && meal == null) {
            final Set<String> exNames = exercises.stream()
                .map(Exercise::getName)
                .collect(Collectors.toSet());

            return stream
                .filter(c -> !isNutritionContent(c)) 
                .filter(c -> c.getTags().contains(goal) || c.getTags().stream().anyMatch(exNames::contains))
                .collect(Collectors.toSet());
        }

        final Set<String> tags = new HashSet<>();
        tags.add(goal);
        if (exercises != null) {
            exercises.forEach(e -> tags.add(e.getName()));
        }
        if (meal != null) {
            tags.add("Nutrition");
        }

        return stream
            .filter(content -> content.getTags().stream()
                .anyMatch(tag -> tags.stream().anyMatch(tag::equalsIgnoreCase)))
            .collect(Collectors.toSet());
    }

    /**
     * Check if content is nutrition related.
     * 
     * @param content the wikicontent.
     * @return true if it's a nutrition content.
     */
    private boolean isNutritionContent(final WikiContent content) {
        return content.getTags().contains("Nutrition") 
            || content.getTags().contains("Dieta") 
            || content.getTags().contains("Alimentazione");
    }
}
