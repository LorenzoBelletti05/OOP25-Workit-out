package it.unibo.workitout.controller.user.impl;

import javax.swing.JOptionPane;

import it.unibo.workitout.controller.user.contracts.UserProfileController;
import it.unibo.workitout.model.user.model.contracts.BMRCalculatorStrategy;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.BMRStrategyChoise;
import it.unibo.workitout.model.user.model.impl.NutritionalTarget;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserManager;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.view.user.contracts.UserProfileView;

public class UserProfileControllerImpl implements UserProfileController{

    private final UserProfileView view;
    private UserManager userManager;

    public UserProfileControllerImpl (final UserProfileView view){
        this.view = view;
        this.view.setController(this);
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
            NutritionalTarget macronutrients = userManager.getMacronutrients();

        view.close();
        } catch (Exception expt) {
            showInputDataError("The insert data is not correct " + expt.getMessage());
        }
    }

    private void showInputDataError(String errorDescription) {
        JOptionPane.showMessageDialog(null, errorDescription, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
