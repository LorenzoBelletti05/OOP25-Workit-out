package it.unibo.workitout.view.user.contracts;

import javax.swing.JButton;

import it.unibo.workitout.controller.user.contracts.UserProfileController;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.BMRStrategyChoice;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;

/**
 * This is the interface of UserProfileViewImpl.
 */
public interface UserProfileView {

    /**
     * Returns the name insert by the user.
     * 
     * @return the user's name
     */
    String getNameInput();

    /**
     * Returns the surname insert by the user.
     * 
     * @return the user's surname
     */
    String getSurnameInput();

    /**
     * Returns the age insert by the user.
     * 
     * @return the user's age
     */
    String getAgeInput();

    /**
     * Returns the height insert by the user.
     * 
     * @return the user's height
     */
    String getHeightInput();

    /**
     * Returns the weight insert by the user.
     * 
     * @return the user's weight
     */
    String getWeightInput();

    /**
     * Returns the sex selected by the user.
     * 
     * @return the user's sex
     */
    Sex getSexInput();

    /**
     * Returns the activity level selected by the user.
     * 
     * @return the user's name
     */
    ActivityLevel getActivityInput();

    /**
     * Returns the user goal selected by the user.
     * 
     * @return the user's name
     */
    UserGoal getUserGoalInput();

    /**
     * Returns the bmr strategy selected by the user.
     * 
     * @return the user's name
     */
    BMRStrategyChoice getBMRStrategyInput();

    /**
     * Returns the back button.
     * 
     * @return the back button
     */
    JButton getBackButton();

    /**
     * Sets the controller for this view.
     * 
     * @param controller the user profile controller
     */
    void setController(UserProfileController controller);

    /**
     * Sets the visibility of the back button.
     * 
     * @param visible true to show, false to hide
     */
    void setBackButton(boolean visible);

    /**
     * Sets the name of the of user in name field.
     * 
     * @param name the name to display
     */
    void setNameInput(String name);

    /**
     * Sets the surname of the of user in surname field.
     * 
     * @param surname the surname to display
     */
    void setSurnameInput(String surname);

    /**
     * Sets the age of the of user in age field.
     * 
     * @param age the age to display
     */
    void setAgeInput(int age);

    /**
     * Sets the height of the of user in height field.
     * 
     * @param height the height to display
     */
    void setHeightInput(double height);

    /**
     * Sets the name of the of user in name field.
     * 
     * @param weight the weight to display
     */
    void setWeightInput(double weight);

    /**
     * Sets the sex in the sex combo box.
     * 
     * @param sex the sex to select
     */
    void setSexInput(Sex sex);

    /**
     * Sets the activity level in the activityLevel combo box.
     * 
     * @param activityLevel the activityLevel to select
     */
    void setActivityInput(ActivityLevel activityLevel);

    /**
     * Sets the user goal in the user goal combo box.
     * 
     * @param userGoal the user goal to select
     */
    void setUserGoalInput(UserGoal userGoal);

    /**
     * Sets the strategy in the strategy combo box.
     * 
     * @param strategy the strategy to select
     */
    void setBMRStrategyInput(String strategy);
}
