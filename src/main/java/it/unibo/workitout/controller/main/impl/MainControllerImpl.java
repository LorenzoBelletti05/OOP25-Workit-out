package it.unibo.workitout.controller.main.impl;

import it.unibo.workitout.controller.food.contracts.NutritionController;
import it.unibo.workitout.controller.food.impl.NutritionControllerImpl;
import it.unibo.workitout.controller.main.contracts.MainController;
import it.unibo.workitout.controller.user.impl.UserProfileControllerImpl;
import it.unibo.workitout.controller.wiki.impl.WikiControllerImpl;
import it.unibo.workitout.model.food.impl.DailyLogManager;
import it.unibo.workitout.model.food.impl.FoodRepository;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.wiki.impl.WikiImpl;
import it.unibo.workitout.view.food.impl.NutritionViewImpl;
import it.unibo.workitout.view.main.contracts.MainView;
import it.unibo.workitout.view.user.impl.UserDashboardViewImpl;
import it.unibo.workitout.view.user.impl.UserProfileViewImpl;
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
        final UserDashboardViewImpl dashboardView = new UserDashboardViewImpl();
        final UserProfileViewImpl profileView = new UserProfileViewImpl();

        Runnable goToDashboard = () -> mainView.showView("DASHBOARD");
        new UserProfileControllerImpl(profileView, dashboardView, goToDashboard);


        profileView.getBackButton().addActionListener(al -> {
            mainView.showView("DASHBOARD");
        });

        final NutritionViewImpl nutritionView = new NutritionViewImpl();
        final NutritionController nutritionController = new NutritionControllerImpl(
            new FoodRepository(), new DailyLogManager(), nutritionView);
        nutritionView.setController(nutritionController);
        
        final WikiViewImpl wikiView = new WikiViewImpl();
        final WikiControllerImpl wikiController = new WikiControllerImpl(new WikiImpl(), wikiView);

        mainView.addModule("LOGIN", profileView);
        mainView.addModule("DASHBOARD", dashboardView);
        mainView.addModule("WIKI", wikiView);
        mainView.addModule("FOOD", nutritionView);

        dashboardView.getProfileButton().addActionListener(al -> {
            mainView.showView("LOGIN");
        });

        dashboardView.getFoodButton().addActionListener(al -> {
            mainView.showView("FOOD");
        });

        dashboardView.getInfoButton().addActionListener(al -> {
            mainView.showView("WIKI");
        });
        
        mainView.showView("LOGIN");
        mainView.start();        
    }
    
}
