package it.unibo.workitout.view.user.contracts;

import javax.swing.JButton;

import it.unibo.workitout.model.user.model.impl.UserManager;

/**
 * The interface of UserDashboardViewImpl.
 */
public interface UserDashboardView {

    /**
     * Displays user data in the dashboard.
     * 
     * @param userManager the manager containing the user data
     */
    void showData(UserManager userManager);

    /**
     * Returns the profile button.
     * 
     * @return the profile button.
     */
    JButton getProfileButton();

    /**
     * Returns the food button.
     * 
     * @return the food button.
     */
    JButton getFoodButton();

    /**
     * Returns the info button.
     * 
     * @return the info button.
     */
    JButton getInfoButton();

    /**
     * Returns the exercise button.
     * 
     * @return the exercise button.
     */
    JButton getExerciseButton();
}
