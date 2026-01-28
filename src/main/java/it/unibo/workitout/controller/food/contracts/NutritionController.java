package it.unibo.workitout.controller.food.contracts;

import java.util.Map;
import java.util.List;
import it.unibo.workitout.model.food.api.Food;

/**
 * Interface for the Nutrition Controller.
 */
public interface NutritionController {

    /**
     * Starts the controller and loads data.
     */
    void start();

    /**
     * Searches for food.
     * @param query the search string.
     */
    void searchFood(final String query);

    /**
     * Adds food to the daily log.
     * @param food the food to add.
     * @param grams the amount in grams.
     */
    void addFoodToDailyLog(final Food food, final int grams);

    /**
     * @return a map of today's nutrients.
     */
    Map<String, Double> getTodayNutrients();

    /**
     * Filters food by high protein content.
     */
    void filterHighProtein();

    /**
     * Filters food by low carb content.
     */
    void filterLowCarbs();

    /**
     * Filters food by low fat content.
     */
    void filterLowFat();

    /**
     * @return a list of all available foods.
     */
    List<Food> getAllFoods();
}
