package it.unibo.workitout.model.wiki.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
     * @return filtered contents sorted by relevance.
     */
    @Override
    public Set<WikiContent> suggest(final Wiki wiki, final UserProfile user, final List<Exercise> exercises, final Meal meal) {
        final String goal = user.getUserGoal().name();
        final Set<String> tags = new HashSet<>();
        tags.add(goal);
        //extract words from exercises
        if (exercises != null) {
            for (final Exercise exercise : exercises) {
                extractWords(exercise.getName(), tags);
            }
        }
        //add the tags for the meal
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

            if (meal.getFood() != null) {
                meal.getFood().forEach(f -> tags.add(f.getName()));
            }
        }
        //filter and sort by relevance
        final List<WikiContent> scored = new ArrayList<>();
        for (final WikiContent content : wiki.getContents()) {
            final int score = calculateScore(content, tags);
            if (score > 0) {
                scored.add(content);
            }
        }
        //sort by score
        scored.sort(
            Comparator.comparingInt(
                (WikiContent c) -> calculateScore(c, tags))
                .reversed()
        );
        if (scored.isEmpty()) {
            return wiki.getContents();
        }
        return new LinkedHashSet<>(scored);
    }

    /**
     * Extract words from an exercise name.
     * 
     * @param exerciseName the title of exercise.
     * @param tags set of tags.
     */
    private void extractWords(final String exerciseName, final Set<String> tags) {
        if (exerciseName == null) {
            return;
        }
        //clean the title of the exercises
        final String cleaned = exerciseName
            .replaceAll("[\\[\\]()\\-]", " ")
            .toLowerCase(Locale.ROOT);
        //tags to add based on the words
        for (final String word : cleaned.split("\\s+")) {
            if (word.length() > 2) {
                tags.add(word);
            }
        }
    }

    /**
     * Calculate relevance score for an content.
     * 
     * @param content the content to check.
     * @param interests the tags.
     * @return the score of the content.
     */
    private int calculateScore(final WikiContent content, final Set<String> interests) {
        int score = 0;
        final String lowerTitle = content.getTitle().toLowerCase(Locale.ROOT);

        for (final String interest : interests) {
            final String lowerInterest = interest.toLowerCase(Locale.ROOT);

            //tags
            for (final String articleTag : content.getTags()) {
                final String lowerTag = articleTag.toLowerCase(Locale.ROOT);
                if (lowerTag.contains(lowerInterest) || lowerInterest.contains(lowerTag)) {
                    score += 2;
                }
            }

            //title
            if (lowerTitle.contains(lowerInterest)) {
                score += 3;
            }
        }
        return score;
    }
}
