package it.unibo.workitout.model.food.api;

import java.time.LocalDate;
import java.util.Map;

/**
 * Represents a daily log that tracks food consumptions and nutritional totals for a specific date.
 */
public interface DailyLog {

    /**
     * Returns the date.
     * 
     * @return the date of the log.
     */
    LocalDate getDate();

    /**
     * Adds a food entry with its relative weight in grams.
     * 
     * @param food the food to be added.
     * @param grams the amount of food in grams.
     */
    void addFoodEntry(Food food, int grams);

    /**
     * Calculates the total calories consumed during the day.
     * 
     * @return the total kilocalories.
     */
    double getTotalKcal();

    /**
     * Calculates the total amount of proteins consumed during the day.
     * 
     * @return the total proteins.
     */
    double getTotalProteins();

    /**
     * Calculates the total amount of carbs consumed during the day.
     * 
     * @return the total carbs.
     */
    double getTotalCarbs();

    /**
     * Calculates the total amount of fats consumed during the day.
     * 
     * @return the total fats in grams.
     */
    double getTotalFats();

    /**
     * Returns an unmodifiable view of the foods consumed and their quantities.
     * 
     * @return a map containing foods as keys and grams as values.
     */
    Map<Food, Integer> getFoodsConsumed();
}
