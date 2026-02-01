/*package it.unibo.workitout.model.wiki;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import javax.lang.model.type.ExecutableType;

import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.contracts.WikiContent;
import it.unibo.workitout.model.wiki.impl.SmartSuggestionImpl;
import it.unibo.workitout.model.wiki.impl.WikiImpl;
import it.unibo.workitout.model.wiki.impl.WikiRepositoryImpl;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.ExerciseType;

class SmartSuggestionTest {

@Test
void testSmartSuggestion() {
    final Wiki wiki = new WikiImpl();
    new WikiRepositoryImpl().loadAll(wiki);

    final UserProfile user = new UserProfile("Mario", "Rossi", 
       25, 180, 80, Sex.MALE, ActivityLevel.MODERATE, UserGoal.BUILD_MUSCLE);

    final Exercise squat = new Exercise("Squat", 5.0, Set.of(), ExerciseType.STRENGTH);
    final var engine = new SmartSuggestionImpl();
    final Set<WikiContent> suggestions = engine.suggest(wiki, user, List.of(squat), null);
    assertFalse(suggestions.isEmpty(), "La lista dei suggerimenti non deve essere vuota");

    final boolean hasGoalContent = suggestions.stream()
                .anyMatch(c -> c.getTags().contains("BUILD_MUSCLE"));
    final boolean hasExerciseContent = suggestions.stream()
                .anyMatch(c -> c.getTitle().contains("Squat"));

    assertTrue(hasGoalContent, "Dovrebbe suggerire contenuti per BUILD_MUSCLE");
    assertTrue(hasExerciseContent, "Dovrebbe suggerire contenuti relativi allo Squat");
    }
}*/
