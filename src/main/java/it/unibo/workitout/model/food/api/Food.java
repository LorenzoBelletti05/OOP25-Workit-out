package it.unibo.workitout.model.food.api;

import java.io.Serializable;

/**
 * Represents a food with nutritional information.
 */
public interface Food extends Serializable {

    /**
     * Returns the name of the food.
     * 
     * @return name of the food.
     */
    String getName();

    /**
     * Returns the calories per 100g.
     * 
     * @return calories per 100g.
     */
    double getKcalPer100g();

    /**
     * Returns the protein percentage.
     * 
     * @return proteins percentage.
     */
    double getProteins();

    /**
     * Returns the carbs percentage.
     * 
     * @return carbohydrates percentage.
     */
    double getCarbs();

    /**
     * Returns the fats percentage.
     * 
     * @return fats percentage.
     */
    double getFats();
}

