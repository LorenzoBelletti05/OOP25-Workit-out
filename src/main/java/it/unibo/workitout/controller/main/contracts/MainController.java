package it.unibo.workitout.controller.main.contracts;

import java.io.IOException;

/**
 * Interface for the Main Controller.
 */
public interface MainController {
    /**
     * Starts all the module controllers.
     * 
     * @throws IOException error save.
     * 
     */
    void start() throws IOException;

    /**
     * Call the user method to set the burned calories.
     * 
     * @param calories burned calories.
     */
    void communicateBurnedCalories(double calories);

}
