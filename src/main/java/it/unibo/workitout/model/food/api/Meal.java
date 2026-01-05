package it.unibo.workitout.model.food.api;
import java.util.List;

//interfaccia Meal
public interface Meal {
    String getTime();
    List<Food> getFood();
}