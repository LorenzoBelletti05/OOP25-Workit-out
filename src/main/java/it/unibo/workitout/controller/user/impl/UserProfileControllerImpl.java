package it.unibo.workitout.controller.user.impl;

import javax.swing.JOptionPane;

import it.unibo.workitout.controller.user.contracts.UserProfileController;
import it.unibo.workitout.controller.workout.impl.UserExerciseControllerImpl;
import it.unibo.workitout.model.main.dataManipulation.LoadSaveData;
import it.unibo.workitout.model.user.model.contracts.BMRCalculatorStrategy;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.BMRStrategyChoice;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserManager;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.view.user.contracts.UserDashboardView;
import it.unibo.workitout.view.user.contracts.UserProfileView;
import it.unibo.workitout.view.workout.impl.PlanViewerImpl;

public class UserProfileControllerImpl implements UserProfileController{

    private final UserProfileView view;
    private final UserDashboardView dashboard;
    private final Runnable goToDashboard;
    private UserManager userManager;

    public UserProfileControllerImpl (final UserProfileView view, final UserDashboardView dashboard, final Runnable runnable){

        this.view = view;
        this.dashboard = dashboard;
        this.goToDashboard = runnable;
        this.view.setController(this);       

        this.dashboard.getProfileButton().addActionListener(al -> {
            editProfile();
        });

        this.dashboard.getExerciseButton().addActionListener(al -> {
            this.dashboard.setVisible(false);
            new PlanViewerImpl().setVisible(true);
        });
    }

    private void editProfile() {
        dashboard.setVisible(false);
        view.setVisible(true);
    }

    public void firstAccess(boolean isFirstAccess) {
        this.view.setBackButton(!isFirstAccess);
    }

    @Override
    public void calculateProfile() {

        try{
            String name = view.getNameInput();
            String surname = view.getSurnameInput();
            int age = Integer.parseInt(view.getAgeInput());
            double height = Double.parseDouble(view.getHeightInput());
            double weight = Double.parseDouble(view.getWeightInput());
            Sex sex = view.getSexInput();
            ActivityLevel activityLevel = view.getActivityInput();
            UserGoal userGoal = view.UserGoalInput();
            BMRStrategyChoice selectedStrategy = view.getBMRStrategyInput();
            BMRCalculatorStrategy strategy = selectedStrategy.getStrategy();
            UserProfile userProfile = new UserProfile(name, surname, age, height, weight, sex, activityLevel, userGoal, strategy.toString());

            try {
                //LoadSaveData.saveUserProfile("user_profile.json", userProfile);
            } catch (Exception expt) {
                showInputDataError("The insert data is not saved \n " + expt.getMessage());
            }
            this.userManager = new UserManager(strategy, userProfile);
            double bmr = userManager.getBMR();
            double tdee = userManager.getTDEE();
            double dailyCalories = userManager.getDailyCalories();

            if(dailyCalories <= 0) {
                throw new IllegalStateException("The total calories are negative, please check your input data.");
            }
            dashboard.showData(this.userManager);

           UserExerciseControllerImpl.getIstance().setDataUser(bmr, tdee, dailyCalories, activityLevel, userGoal);           

            if(goToDashboard != null){
                goToDashboard.run();
            }

        } catch (Exception expt) {
            showInputDataError("The insert data is not correct \n " + expt.getMessage());
        }

    }

    private void showInputDataError(String errorDescription) {
        JOptionPane.showMessageDialog(null, errorDescription, "Error", JOptionPane.ERROR_MESSAGE);
    }

}