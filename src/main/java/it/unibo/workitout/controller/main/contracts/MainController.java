package it.unibo.workitout.controller.main.contracts;

/**
 * Interface for the Main Controller.
 */
public interface MainController {
    /**
     * Starts all the module controllers.
     */
    void start();

    /**
     * Call the user method to set the burned calories.
     * 
     * @param calories burned calories.
     */
    void communicateBurnedCalories(final double calories);
}   
