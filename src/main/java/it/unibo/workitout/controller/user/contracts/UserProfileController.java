package it.unibo.workitout.controller.user.contracts;

import it.unibo.workitout.model.user.model.impl.UserManager;

/**
 * Interface for the UserProfile Controller.
 */
public interface UserProfileController {

    /**
     * Calculates the user profile based on input data.
     */
    void calculateProfile();

    /**
     * Sets the button based on if it is first access.
     * 
     * @param isFirstAccess true if it is first access.
     */
    void isFirstAccess(boolean isFirstAccess);

    /**
     * Sets the UserManager.
     * 
     * @param userManager the user manager.
     */
    void setUserManager(UserManager userManager);

    /**
     * Updates the total calories burned with exercise.
     * 
     * @param burnedCalories the total of burned calories
     */
    void updateBurnedCalories(double burnedCalories);

    /**
     * Updates the user's daily nutrients intake.
     * 
     * @param kcal calories consumed
     * @param proteins proteins consumed
     * @param carbs carbs consumed
     * @param fats fats consumed
     */
    void updateNutrients(double kcal, double proteins, double carbs, double fats);
}
