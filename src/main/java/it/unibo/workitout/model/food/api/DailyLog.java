package it.unibo.workitout.model.food.api;

import java.time.LocalDate;
import java.util.Map;

public interface DailyLog {
    LocalDate getDate();

    //Aggiunge un cibo e la sua quantità
    void addFoodEntry(Food food, int grams);

    //Dati totali della giornata
    double getTotalKcal();
    double getTotalProteins();
    double getTotalCarbs();
    double getTotalFats();

    //Mappa dei cibi inseriti e le relative quantità
    Map<Food, Integer> getFoodsConsumed();
}