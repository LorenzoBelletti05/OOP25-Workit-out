package it.unibo.workitout.model.wiki;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.workitout.model.food.api.Food;
import it.unibo.workitout.model.food.api.Meal;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.contracts.WikiContent;
import it.unibo.workitout.model.wiki.impl.ArticleImpl;
import it.unibo.workitout.model.wiki.impl.SmartSuggestionImpl;
import it.unibo.workitout.model.wiki.impl.WikiImpl;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.ExerciseType;

/**
 * New class for test the SmartSuggestion system.
 */
class SmartSuggestionTest {
    //constants
    private static final String PASTA = "pasta";
    private static final String SQUAT = "Squat";
    private static final double KCAL_PASTA = 350;
    private static final double PROTEINS_PASTA = 12;
    private static final double CARBS_PASTA = 70;
    private static final double FATS_PASTA = 1;
    private static final int USER_AGE = 20;
    private static final double USER_HEIGHT = 170;
    private static final double USER_WEIGHT = 68;
    private static final double EXERCISE_METS = 5.0;
    private static final String TESTO = "testo";
    //models
    private Wiki wiki;
    private UserProfile user;
    private SmartSuggestionImpl smartSuggestion;
    //tags
    private String goalTag;

    /**
     * Set up the test.
     */
    @BeforeEach
    void setUp() {
        this.wiki = new WikiImpl();
        this.smartSuggestion = new SmartSuggestionImpl();
        this.goalTag = UserGoal.BUILD_MUSCLE.name();
        //fake artciles
        this.wiki.addContent(new ArticleImpl(
            "titolo massa", 
            TESTO, 
            Set.of(goalTag, "Strength")
        ));
        this.wiki.addContent(new ArticleImpl(
            "titolo dieta", 
            TESTO, 
            Set.of(goalTag, "Nutrition")
        ));
        this.wiki.addContent(new ArticleImpl(
            "titolo pasta", 
            TESTO, 
            Set.of(PASTA, "Nutrition")
        ));
        this.wiki.addContent(new ArticleImpl(
            "Tecnica Squat", 
            TESTO, 
            Set.of(SQUAT, "Technique")
        ));
        //fake user
        this.user = new UserProfile(
            "Mario", 
            "Rossi", 
            USER_AGE, 
            USER_HEIGHT, 
            USER_WEIGHT, 
            Sex.MALE, 
            ActivityLevel.MODERATE, 
            UserGoal.BUILD_MUSCLE, 
            "MifflinStJeorStrategy"
        );
    }

    /**
     * Testing the suggestions for the exercises.
     */
    @Test
    void testSmartSuggestionWithExercise() {
        //fake exercises
        final Exercise squat = new Exercise(
            SQUAT,
            EXERCISE_METS, 
            goalTag, 
            ExerciseType.STRENGTH
        );
        //suggestions
        final Set<WikiContent> suggestions = smartSuggestion.suggest(
            wiki, 
            user, 
            List.of(squat), 
            null
        );
        //tests
        assertFalse(
            suggestions.isEmpty(), 
            "It shound't be empty");
        assertTrue(
            suggestions.stream().anyMatch(c -> c.getTitle().contains("massa")), 
            "It should show suggestions for the goal");
        assertTrue(
            suggestions.stream().anyMatch(c -> c.getTitle().contains(SQUAT)), 
            "It should show suggestions based on name of the exercises");
    }

    /**
     * Testing the suggestions for the meals.
     */
    @Test
    void testSmartSuggestionWithMeal() {
        //fake meal
        final Food pasta = new Food() {

            @Override
            public String getName() {
                return PASTA;
            }

            @Override
            public double getKcalPer100g() {
                return KCAL_PASTA;
            }

            @Override
            public double getProteins() {
                return PROTEINS_PASTA;
            }

            @Override
            public double getCarbs() {
                return CARBS_PASTA;
            }

            @Override
            public double getFats() {
                return FATS_PASTA;
            }
        };
        final Meal meal = new Meal() {

            @Override
            public String getTime() {
                return "12:00";
            }

            @Override
            public List<Food> getFood() {
                return List.of(pasta);
            }
        };
        //suggestions
        final Set<WikiContent> suggestions = smartSuggestion.suggest(
            wiki, 
            user, 
            null, 
            meal
        );
        //tests
        assertFalse(
            suggestions.isEmpty(), 
            "It should show suggestions based on the meal");
        assertTrue(
            suggestions.stream().anyMatch(c -> c.getTitle().contains(PASTA)), 
            "It should show nutritional suggestions based on the goal");
    }

    /**
     * Testing the suggestions for boths.
     */
    @Test
    void testFullSmartSuggestion() {
        //full fake test
        final Exercise squat = new Exercise(
            SQUAT, 
            EXERCISE_METS, 
            goalTag, 
            ExerciseType.STRENGTH
        );
        final Food pasta = new Food() {

            @Override
            public String getName() {
                return PASTA;
            }

            @Override
            public double getKcalPer100g() {
                return KCAL_PASTA;
            }

            @Override
            public double getProteins() {
                return PROTEINS_PASTA;
            }

            @Override
            public double getCarbs() {
                return CARBS_PASTA;
            }

            @Override
            public double getFats() {
                return FATS_PASTA;
            }
        };
        final Meal meal = new Meal() {

            @Override
            public String getTime() {
                return "12:00";
            }

            @Override
            public List<Food> getFood() {
                return List.of(pasta);
            }
        };
        //suggestions
        final Set<WikiContent> suggestions = smartSuggestion.suggest(
            wiki, 
            user, 
            List.of(squat), 
            meal
        );
        //test
        assertTrue(
            suggestions.size() >= 4, 
            "It should show suggestions for boths");
    }

    /**
     * Testing words extraction from exercise names.
     */
    @Test
    void testWordsExtraction() {
        //add article with gambe tag
        this.wiki.addContent(new ArticleImpl(
            "Allenamento Gambe", 
            TESTO, 
            Set.of("Gambe", "Technique")
        ));
        //exercise with complex name
        final Exercise deadlift = new Exercise(
            "Deadlift [Gambe] (Stacco da terra)",
            EXERCISE_METS, 
            goalTag, 
            ExerciseType.STRENGTH
        );
        //suggestions
        final Set<WikiContent> suggestions = smartSuggestion.suggest(
            wiki, 
            user, 
            List.of(deadlift), 
            null
        );
        //test
        assertTrue(
            suggestions.stream().anyMatch(c -> c.getTitle().contains("Gambe")), 
            "It should extract gambe from exercise name"
        );
    }

    /**
     * Testing fallback when no match.
     */
    @Test
    void testFallbackWhenNoMatch() {
        //user with different goal
        final UserProfile otherUser = new UserProfile(
            "Test", 
            "User", 
            USER_AGE, 
            USER_HEIGHT, 
            USER_WEIGHT, 
            Sex.MALE, 
            ActivityLevel.LOW, 
            UserGoal.MAINTAIN_WEIGHT, 
            "MifflinStJeorStrategy"
        );
        //empty wiki
        final Wiki emptyWiki = new WikiImpl();
        emptyWiki.addContent(new ArticleImpl(
            "Random Article", 
            TESTO, 
            Set.of("Random")
        ));
        //suggestions with no matching tags
        final Set<WikiContent> suggestions = smartSuggestion.suggest(
            emptyWiki, 
            otherUser, 
            null, 
            null
        );
        //test fallback
        assertFalse(
            suggestions.isEmpty(), 
            "It should return all content as fallback"
        );
    }
}
