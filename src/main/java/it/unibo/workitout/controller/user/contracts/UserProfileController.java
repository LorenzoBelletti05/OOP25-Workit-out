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
}
