package it.unibo.workitout.controller.food.contracts;

import java.util.Map;
import java.util.List;
import it.unibo.workitout.model.food.api.Food;

public interface NutritionController {
    void start();

    void searchFood(String query);

    void addFoodToDailyLog(Food food, int grams);

    Map<String, Double> getTodayNutrients();

    void filterHighProtein();
    void filterLowCarbs();
    void filterLowFat();
    List<Food> getAllFoods();
}
