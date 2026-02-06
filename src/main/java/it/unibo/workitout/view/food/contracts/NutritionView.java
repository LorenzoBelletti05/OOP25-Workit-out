package it.unibo.workitout.view.food.contracts;

import it.unibo.workitout.model.food.api.Food;
import java.util.List;

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
}
