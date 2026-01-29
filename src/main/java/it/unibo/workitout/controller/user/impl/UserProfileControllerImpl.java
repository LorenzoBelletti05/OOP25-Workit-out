package it.unibo.workitout.controller.user.impl;

import javax.swing.JOptionPane;

import it.unibo.workitout.controller.user.contracts.UserProfileController;
import it.unibo.workitout.controller.workout.impl.UserExerciseControllerImpl;
import it.unibo.workitout.model.user.model.contracts.BMRCalculatorStrategy;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.BMRStrategyChoise;
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
    private UserManager userManager;

    public UserProfileControllerImpl (final UserProfileView view, final UserDashboardView dashboard){
        this.view = view;
        this.dashboard = dashboard;
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
            BMRStrategyChoise selectedStrategy = view.getBMRStrategyInput();
            BMRCalculatorStrategy strategy = selectedStrategy.getStrategy();
            UserProfile userProfile = new UserProfile(name, surname, age, height, weight, sex, activityLevel, userGoal);
            this.userManager = new UserManager(strategy, userProfile);

            double bmr = userManager.getBMR();
            double tdee = userManager.getTDEE();
            double dailyCalories = userManager.getDailyCalories();

            if(dailyCalories <= 0) {
                throw new IllegalStateException("The total calories are negative, please check your input data.");
            }

            dashboard.showData(this.userManager);
            dashboard.setVisible(true);
            view.close();
            
            new UserExerciseControllerImpl(bmr, tdee, dailyCalories, activityLevel, userGoal);            

        } catch (Exception expt) {
            showInputDataError("The insert data is not correct \n " + expt.getMessage());
        }
    }

    private void showInputDataError(String errorDescription) {
        JOptionPane.showMessageDialog(null, errorDescription, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
