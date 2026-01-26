package it.unibo.workitout;

import it.unibo.workitout.model.wiki.impl.WikiImpl;
import it.unibo.workitout.view.wiki.impl.WikiViewImpl;
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
        final Wiki model = new WikiImpl();
        final WikiView view = new WikiViewImpl();
        final WikiController controller = new WikiControllerImpl(model, view);
        controller.start(); 

        //NUTRITION
        final FoodRepository foodRepo = new FoodRepository();
        final DailyLogManager logManager = new DailyLogManager();
        final NutritionViewImpl nutritionView = new NutritionViewImpl();
        final NutritionController nutritionController = new NutritionControllerImpl(foodRepo, logManager, nutritionView);
        nutritionView.setController(nutritionController);
        nutritionController.start();
        
        final UserProfile testUser = new UserProfile(
            "Mario", "Rossi", 25, 180.0, 80.0, 
            Sex.MALE, ActivityLevel.HIGH, UserGoal.BUILD_MUSCLE
        );
                                              
        final MainViewImpl mainView = new MainViewImpl();
        final MainControllerImpl mainController = new MainControllerImpl(mainView, testUser);

        mainController.start();
    }
}