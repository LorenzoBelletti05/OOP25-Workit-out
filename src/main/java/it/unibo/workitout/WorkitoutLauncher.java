package it.unibo.workitout;

import it.unibo.workitout.model.wiki.impl.WikiImpl;
import it.unibo.workitout.view.wiki.impl.WikiViewImpl;
import it.unibo.workitout.view.workout.impl.ExerciseViewerImpl;
import it.unibo.workitout.view.workout.impl.PlanViewerImpl;
import it.unibo.workitout.controller.wiki.impl.WikiControllerImpl;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.view.wiki.contracts.WikiView;
import it.unibo.workitout.view.main.impl.MainViewImpl;
import it.unibo.workitout.controller.main.impl.MainControllerImpl;
import it.unibo.workitout.controller.wiki.contracts.WikiController;
import it.unibo.workitout.model.food.impl.FoodRepository;
import it.unibo.workitout.model.food.impl.DailyLogManager;
import it.unibo.workitout.view.food.impl.NutritionViewImpl;
import it.unibo.workitout.controller.food.impl.NutritionControllerImpl;
import it.unibo.workitout.controller.food.contracts.NutritionController;

/**
 * Main entry point for the application.
 */
public final class WorkitoutLauncher {

    /**
     * Main method.
     * @param args ...
     */
    public static void main(final String[] args) {
        final MainViewImpl mainView = new MainViewImpl();

        //WIKI
        final Wiki modelWiki = new WikiImpl();
        final WikiView viewWiki = new WikiViewImpl();
        final WikiController controllerWiki = new WikiControllerImpl(modelWiki, viewWiki);

        //NUTRITION
        final FoodRepository foodRepo = new FoodRepository();
        final DailyLogManager logManager = new DailyLogManager();
        final NutritionViewImpl nutritionView = new NutritionViewImpl();
        final NutritionController nutritionController = new NutritionControllerImpl(foodRepo, logManager, nutritionView);
        nutritionView.setController(nutritionController);
        
        final UserProfile testUser = new UserProfile(
            "Mario", "Rossi", 25, 180.0, 80.0, 
            Sex.MALE, ActivityLevel.HIGH, UserGoal.BUILD_MUSCLE, null
        );

        final MainControllerImpl mainController = new MainControllerImpl(mainView);

        //WORKOUT
        final PlanViewerImpl planView = new PlanViewerImpl();
        final ExerciseViewerImpl exerView = new ExerciseViewerImpl();

        mainController.start();
    }
}