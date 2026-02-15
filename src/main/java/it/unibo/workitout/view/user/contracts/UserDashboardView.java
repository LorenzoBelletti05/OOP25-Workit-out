package it.unibo.workitout.view.user.contracts;

import java.awt.event.ActionListener;

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
     * Adds an ActionListener to profile button.
     * 
     * @param al the action listener
     */
    void addProfileActListener(ActionListener al);

    /**
     * Adds an ActionListener to food button.
     * 
     * @param al the action listener
     */
    void addFoodActListener(ActionListener al);

    /**
     * Adds an ActionListener to info button.
     * 
     * @param al the action listener
     */
    void addInfoActListener(ActionListener al);

    /**
     * Adds an ActionListener to exercise button.
     * 
     * @param al the action listener
     */
    void addExerciseActListener(ActionListener al);
}
