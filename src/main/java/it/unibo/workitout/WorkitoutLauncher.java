package it.unibo.workitout;

import it.unibo.workitout.model.wiki.impl.WikiImpl;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.view.wiki.impl.WikiViewImpl;
import it.unibo.workitout.controller.wiki.impl.WikiControllerImpl;
import it.unibo.workitout.model.food.api.Food;
import it.unibo.workitout.model.food.api.Meal;
import it.unibo.workitout.model.food.impl.FoodImpl;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.view.wiki.contracts.WikiView;

import java.util.List;
import java.util.Set;

import it.unibo.workitout.controller.wiki.contracts.WikiController;

/**
 * Main entry point for the application.
 */
public final class WorkitoutLauncher {

    /**
     * Main method.
     * @param args ...
     */
    public static void main(final String[] args) {
        final Wiki model = new WikiImpl();
        final WikiView view = new WikiViewImpl();
        final WikiController controller = new WikiControllerImpl(model, view);
        controller.start();

        // Uncomment for testing
        /*final UserProfile testUser = new UserProfile(
            "Mario", "Rossi", 25, 180.0, 80.0, 
            Sex.MALE, ActivityLevel.HIGH, UserGoal.BUILD_MUSCLE
        );
        final Exercise squat = new Exercise("Squat", 5.0, Set.of());
        final Food pasta = new FoodImpl("Pasta", 350, 0.15, 0.82, 0.03);
        final Meal testMeal = new Meal() {
            @Override
            public String getTime() { return "12:30"; }
            @Override
            public List<Food> getFood() { return List.of(pasta); }
        };

        controller.showSmartSuggestions(testUser, List.of(squat), testMeal);*/
    }
}