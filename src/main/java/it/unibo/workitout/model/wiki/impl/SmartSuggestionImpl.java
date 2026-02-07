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
        final Set<String> tags = new HashSet<>();
        tags.add(goal);
        if (exercises != null) {
            exercises.forEach(e -> tags.add(e.getName()));
        }
        if (meal != null) {
            tags.add("Nutrition");
            tags.add("Alimentazione");
            switch (user.getUserGoal()) {
                case LOSE_WEIGHT:
                    tags.add("Dieta");
                    tags.add("Definizione");
                    break;
                case BUILD_MUSCLE:
                case GAIN_WEIGHT:
                    tags.add("Massa");
                    tags.add("Proteine");
                    tags.add("Ipertrofia");
                    break;
                default:
                    break;
            }
            meal.getFood().forEach(f -> tags.add(f.getName()));
        }

        return stream
            .filter(content -> content.getTags().stream()
                .anyMatch(tag -> tags.stream()
                    .anyMatch(interest -> interest.equalsIgnoreCase(tag))))
            .collect(Collectors.toSet());
    }
}
