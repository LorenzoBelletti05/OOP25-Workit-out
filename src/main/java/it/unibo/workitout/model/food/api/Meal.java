package it.unibo.workitout.model.food.api;

import java.util.List;

/**
 * Represents a meal consisting of a time and a list of food items.
 */
public interface Meal {

    /**
     * Returns the time of the meal.
     * 
     * @return the time of the meal.
     */
    String getTime();

    /**
     * Returns the list of food items included in the meal.
     * 
     * @return a list of food items.
     */
    List<Food> getFood();
}
