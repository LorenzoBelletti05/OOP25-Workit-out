package it.unibo.workitout.controller.main.impl;

import java.time.LocalDate;

import it.unibo.workitout.controller.food.contracts.NutritionController;
import it.unibo.workitout.controller.food.impl.NutritionControllerImpl;
import it.unibo.workitout.controller.main.contracts.MainController;
import it.unibo.workitout.controller.user.impl.UserProfileControllerImpl;
import it.unibo.workitout.controller.wiki.impl.WikiControllerImpl;
import it.unibo.workitout.controller.workout.impl.UserExerciseControllerImpl;
import it.unibo.workitout.model.food.impl.DailyLogManager;
import it.unibo.workitout.model.food.impl.FoodRepository;
import it.unibo.workitout.model.main.dataManipulation.LoadSaveData;
import it.unibo.workitout.model.user.model.contracts.BMRCalculatorStrategy;
import it.unibo.workitout.model.user.model.impl.HarrisBenedictStrategy;
import it.unibo.workitout.model.user.model.impl.MifflinStJeorStrategy;
import it.unibo.workitout.model.user.model.impl.UserManager;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.wiki.impl.WikiImpl;
import it.unibo.workitout.view.food.impl.NutritionViewImpl;
import it.unibo.workitout.view.main.contracts.MainView;
import it.unibo.workitout.view.user.impl.UserDashboardViewImpl;
import it.unibo.workitout.view.user.impl.UserProfileViewImpl;
import it.unibo.workitout.view.wiki.impl.WikiViewImpl;
import it.unibo.workitout.view.workout.impl.PlanViewerImpl;

public class MainControllerImpl implements MainController {
    private static final String LOGIN = "LOGIN";
    private static final String DASHBOARD = "DASHBOARD";
    private static final String WIKI = "WIKI";
    private static final String FOOD = "FOOD";
    private static final String EXERCISE = "EXERCISE";

    private final MainView mainView;
    private UserProfile user;  
    
    public MainControllerImpl(MainView mainView) {
        this.mainView = mainView;
    }

    /**
     * Starts all the module controllers.
     */
    @Override
    public void start() {
        final UserDashboardViewImpl dashboardView = new UserDashboardViewImpl();
        final UserProfileViewImpl profileView = new UserProfileViewImpl();

        Runnable goToDashboard = () -> mainView.showView(DASHBOARD);

        UserProfileControllerImpl userController = new UserProfileControllerImpl(profileView, dashboardView, goToDashboard);

        this.user = LoadSaveData.loadUserProfile(LoadSaveData.createPath("user_profile.json"));

        if(this.user != null) {
            LocalDate now = LocalDate.now();
            if(!this.user.getLastAccess().equals(now)) {
                this.user.setLastAccess();
                LoadSaveData.saveUserProfile(LoadSaveData.createPath("user_profile.json"), this.user);
            }

            BMRCalculatorStrategy strategy;
            if(user.getStrategy().equals("MifflinStJeorStrategy")) {
                strategy = new MifflinStJeorStrategy();
            } else {
                strategy = new HarrisBenedictStrategy();
            }

            UserManager userManager = new UserManager(strategy, this.user);
            userController.setUserManager(userManager);
            dashboardView.showData(userManager);
            userController.isFirstAccess(false);
            mainView.addModule(LOGIN, profileView);
            mainView.addModule(DASHBOARD, dashboardView);
            mainView.showView(DASHBOARD);
        } else {
            userController.isFirstAccess(true);
            mainView.addModule(LOGIN, profileView);
            mainView.addModule(DASHBOARD, dashboardView);
            mainView.showView(LOGIN);
        }


        profileView.getBackButton().addActionListener(al -> {
            mainView.showView(DASHBOARD);
        });

        final NutritionViewImpl nutritionView = new NutritionViewImpl();
        final NutritionController nutritionController = new NutritionControllerImpl(
        new FoodRepository(), new DailyLogManager(), nutritionView);
        nutritionView.setController(nutritionController);
        nutritionController.start();

        final WikiViewImpl wikiView = new WikiViewImpl();
        wikiView.addMainBackListener(view -> mainView.showView(DASHBOARD));
        final WikiControllerImpl wikiController = new WikiControllerImpl(new WikiImpl(), wikiView);
        wikiController.start();
        
        if (this.user != null) {
            wikiController.showSmartSuggestions(this.user, null, null);
        }

        final PlanViewerImpl exerciseView = new PlanViewerImpl();

        mainView.addModule(WIKI, wikiView);
        mainView.addModule(FOOD, nutritionView);
        mainView.addModule(EXERCISE, exerciseView);

        dashboardView.getProfileButton().addActionListener(al -> {
            mainView.showView(LOGIN);
        });

        dashboardView.getFoodButton().addActionListener(al -> {
            mainView.showView(FOOD);
        });

        dashboardView.getInfoButton().addActionListener(al -> {
            mainView.showView(WIKI);
        });

        dashboardView.getExerciseButton().addActionListener(al -> {            
            UserExerciseControllerImpl.getIstance().refreshTableWorkoutData(() -> {
                mainView.showView(EXERCISE);
            });
        });

        exerciseView.getBackButton().addActionListener(al -> {
            mainView.showView(DASHBOARD);
        });

        mainView.start();        
    }
    
}
