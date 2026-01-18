package it.unibo.workitout.controller.main.impl;

import it.unibo.workitout.controller.food.contracts.NutritionController;
import it.unibo.workitout.controller.food.impl.NutritionControllerImpl;
import it.unibo.workitout.controller.main.contracts.MainController;
import it.unibo.workitout.controller.wiki.impl.WikiControllerImpl;
import it.unibo.workitout.model.food.impl.DailyLogManager;
import it.unibo.workitout.model.food.impl.FoodRepository;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.wiki.impl.WikiImpl;
import it.unibo.workitout.view.food.impl.NutritionViewImpl;
import it.unibo.workitout.view.main.contracts.MainView;
import it.unibo.workitout.view.wiki.impl.WikiViewImpl;

public class MainControllerImpl implements MainController {
    private final MainView mainView;
    private final UserProfile user;
    
    public MainControllerImpl(MainView mainView, UserProfile user) {
        this.mainView = mainView;
        this.user = user;
    }

    /**
     * Starts all the module controllers.
     */
    @Override
    public void start() {
        final NutritionViewImpl nutritionView = new NutritionViewImpl();
        final NutritionController nutritionController = new NutritionControllerImpl(
            new FoodRepository(), new DailyLogManager(), nutritionView);
        nutritionView.setController(nutritionController);
        nutritionController.start();
        mainView.addModule("Diario Alimentare", nutritionView);
        
        final WikiViewImpl wikiView = new WikiViewImpl();
        final WikiControllerImpl wikiController = new WikiControllerImpl(new WikiImpl(), wikiView);
        wikiController.start();
        mainView.addModule("Wiki", wikiView);
        wikiController.showSmartSuggestions(user, null, null);
        mainView.start();
    }
    
}
