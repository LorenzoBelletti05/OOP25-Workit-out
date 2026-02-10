package it.unibo.workitout.view.workout.contracts;

import javax.swing.JButton;

/**
 * Interface to the plan view.
 */
public interface PlanViewer {

    /**
     * Close method used from the user modul.
     */
    void close();

    /**
     * methods to set the visibility of this view.
     * 
     * @param visible boolean parameter.
     * 
     */
    void setVisible(boolean visible);

    /**
     * Method that return the button back for the user modul.
     * 
     * @return the Jbutton
     */
    JButton getBackButton();

    /**
     * method to set the table with all the plan.
     */
    void setTable();

}
