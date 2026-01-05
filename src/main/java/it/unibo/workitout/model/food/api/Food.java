package it.unibo.workitout.model.food.api;
import java.util.List;

//interfaccia Food
public interface Food {
    double getCalories();
    String getNutrients();
    String getName();
}

//interfaccia Meal
interface Meal {
    String getTime();
    List<Food> getFood();
}

//interfaccia DailyLog
interface DailyLog {
    String getDate();
    double getTotalCalories();
}