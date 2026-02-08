package it.unibo.workitout.view.food.contracts;

import it.unibo.workitout.controller.food.contracts.NutritionController;
import it.unibo.workitout.model.food.api.Food;
import java.util.List;

import javax.swing.JButton;

/**
 * Interface for the Nutrition View.
 */

public interface NutritionView {
    /**
     * Updates the table with the provided list of foods.
     * 
     * @param foods the list of foods to display.
     */
    void updateTable(List<Food> foods);

    /**
     * Updates the summary text.
     * 
     * @param summary the text to display.
     */
    void updateSummary(String summary);

    /**
     * Sets the controller for this view.
     * 
     * @param controller the nutrition controller to link.
     */
    void setController(NutritionController controller);

    /**
     * Provides the back button.
     * 
     * @return the JButton used to return to previous screen
     */
    JButton getBackButton();
}
