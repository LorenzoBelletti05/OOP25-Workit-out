package it.unibo.workitout.controller.user.impl;

import java.io.IOException;
import javax.swing.JOptionPane;

import it.unibo.workitout.controller.user.contracts.UserProfileController;
import it.unibo.workitout.controller.workout.impl.UserExerciseControllerImpl;
import it.unibo.workitout.model.main.datamanipulation.LoadSaveData;
import it.unibo.workitout.model.user.model.contracts.BMRCalculatorStrategy;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.BMRStrategyChoice;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserManager;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.view.user.contracts.UserDashboardView;
import it.unibo.workitout.view.user.contracts.UserProfileView;

/**
 * Implementation of the UserProfile controller.
 */
public final class UserProfileControllerImpl implements UserProfileController {
    private static final String FILE_NAME = "user_profile.json";
    private static final String ERR_MESS = "The insert data is not saved \n ";

    private final UserProfileView view;
    private final UserDashboardView dashboard;
    private final Runnable goToDashboard;
    private UserManager userManager;

    /**
     * Constructor for the UserProfileController.
     * 
     * @param view          the view for editing the profile
     * @param dashboard     the main dashboard view
     * @param runnable      action to perform to go to dashboard
     */
    public UserProfileControllerImpl(final UserProfileView view, final UserDashboardView dashboard, final Runnable runnable) {
        this.view = view;
        this.dashboard = dashboard;
        this.goToDashboard = runnable;
        this.view.setController(this); 

        this.dashboard.getProfileButton().addActionListener(al -> {
            editProfile();
        });
    }

    /**
     * Insert in the view an existing profile.
     */
    private void editProfile() {
        if (this.userManager == null) {
            return;
        }
        if (this.userManager.getUserProfile() != null) {
            fillProfileButton();
            isFirstAccess(false);
        }
    }

    /**
     * Fills the input field and select the combo box with data of current user.
     */
    private void fillProfileButton() {
        final UserProfile userProfile = this.userManager.getUserProfile();
        view.setNameInput(userProfile.getName());
        view.setSurnameInput(userProfile.getSurname());
        view.setAgeInput(userProfile.getAge());
        view.setHeightInput(userProfile.getHeight());
        view.setWeightInput(userProfile.getWeight());
        view.setSexInput(userProfile.getSex());
        view.setActivityInput(userProfile.getActivityLevel());
        view.setUserGoalInput(userProfile.getUserGoal());
        view.setBMRStrategyInput(userProfile.getStrategy());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void isFirstAccess(final boolean isFirstAccess) {
        this.view.setBackButton(!isFirstAccess);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserManager(final UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculateProfile() {
        try {
            final String name = view.getNameInput();
            final String surname = view.getSurnameInput();
            final int age = Integer.parseInt(view.getAgeInput());
            final double height = Double.parseDouble(view.getHeightInput());
            final double weight = Double.parseDouble(view.getWeightInput());
            final Sex sex = view.getSexInput();
            final ActivityLevel activityLevel = view.getActivityInput();
            final UserGoal userGoal = view.getUserGoalInput();
            final BMRStrategyChoice selectedStrategy = view.getBMRStrategyInput();
            final BMRCalculatorStrategy strategy = selectedStrategy.getStrategy();
            final UserProfile userProfile = new UserProfile(
                name,
                surname,
                age,
                height,
                weight,
                sex,
                activityLevel,
                userGoal,
                strategy.toString()
            );

            try {
                LoadSaveData.saveUserProfile(LoadSaveData.createPath(FILE_NAME), userProfile);
            } catch (final IOException expt) {
                showInputDataError(ERR_MESS + expt.getMessage());
            }

            this.userManager = new UserManager(strategy, userProfile);

            final double dailyCalories = userManager.getDailyCalories();

            if (dailyCalories <= 0) {
                showInputDataError("The total calories are negative, please check your input data");
            }
                dashboard.showData(this.userManager);

            final double bmr = userManager.getBMR();
            final double tdee = userManager.getTDEE();

           UserExerciseControllerImpl.getInstance().setDataUser(bmr, tdee, dailyCalories, activityLevel, userGoal);

            if (goToDashboard != null) {
                goToDashboard.run();
            }

        } catch (final IllegalArgumentException expt) {
            showInputDataError("The insert data is not correct \n " + expt.getMessage());
        }

    }

    /**
     * Method for display an error message.
     * 
     * @param errorDescription the text description of the error
     */
    private void showInputDataError(final String errorDescription) {
        JOptionPane.showMessageDialog(null, errorDescription, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBurnedCalories(final double burnedCalories) {
        if (this.userManager == null) {
            return;
        }
        this.userManager.addBurnedCalories(burnedCalories);

        try {
            LoadSaveData.saveUserProfile(LoadSaveData.createPath(FILE_NAME), this.userManager.getUserProfile());
        } catch (final IOException expt) {
            showInputDataError(ERR_MESS + expt.getMessage());
        }

        this.dashboard.showData(this.userManager);
    }

    @Override
    public void updateNutrients(final double kcal, final double proteins, final double carbs, final double fats) {
        if (this.userManager == null) {
            return;
        }
        this.userManager.addConsumedFood(kcal, carbs, proteins, fats);
        try {
            LoadSaveData.saveUserProfile(LoadSaveData.createPath(FILE_NAME),
                this.userManager.getUserProfile());
        } catch (final IOException expt) {
            showInputDataError(ERR_MESS + expt.getMessage());
        }
        this.dashboard.showData(this.userManager);
    }
}
