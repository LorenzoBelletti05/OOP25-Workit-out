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

/**
 * Implementation of the UserProfile controller.
 */
public final class UserProfileControllerImpl implements UserProfileController {

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

        this.dashboard.getExerciseButton().addActionListener(al -> {
            this.dashboard.setVisible(false);
        });
    }

    private void editProfile() {
        if (this.userManager.getUserProfile() != null) {
            fillProfileButton();
            isFirstAccess(false);
        }
        dashboard.setVisible(false);
        view.setVisible(true);
    }

    private void fillProfileButton() {
        final UserProfile userProfile = this.userManager.getUserProfile();
        view.setNameInput(userProfile.getName());
        view.setSurnameInput(userProfile.getSurname());
        view.setAgeInput(userProfile.getAge());
        view.setHeightInput(userProfile.getHeight());
        view.setWeightInput(userProfile.getWeight());
        view.setSexInput(userProfile.getSex());
        view.setActivityInput(userProfile.getActivityLevel());
        view.setUserGoalInput(userProfile.getGoal());
        view.setBMRStrategyInput(userProfile.getStrategy());
    }

    /**
     * Sets the button based on if is the first access of user or not.
     * 
     * @param isFirstAccess true if it is the first access
     */
    public void isFirstAccess(final boolean isFirstAccess) {
        this.view.setBackButton(!isFirstAccess);
    }

    /**
     * Sets the UserManager for this controller.
     * 
     * @param userManager the UserManager
     */
    public void setUserManager(final UserManager userManager) {
        this.userManager = userManager;
    }

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
            final UserGoal userGoal = view.UserGoalInput();
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
                LoadSaveData.saveUserProfile(LoadSaveData.createPath("user_profile.json"), userProfile);
            } catch (final Exception expt) {
                showInputDataError("The insert data is not saved \n " + expt.getMessage());
            }

            this.userManager = new UserManager(strategy, userProfile);

            final double bmr = userManager.getBMR();
            final double tdee = userManager.getTDEE();
            final double dailyCalories = userManager.getDailyCalories();

            if (dailyCalories <= 0) {
                throw new IllegalStateException("The total calories are negative, please check your input data.");
            }
            dashboard.showData(this.userManager);

           UserExerciseControllerImpl.getIstance().setDataUser(bmr, tdee, dailyCalories, activityLevel, userGoal);

            if (goToDashboard != null) {
                goToDashboard.run();
            }

        } catch (final Exception expt) {
            showInputDataError("The insert data is not correct \n " + expt.getMessage());
        }

    }

    private void showInputDataError(final String errorDescription) {
        JOptionPane.showMessageDialog(null, errorDescription, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
