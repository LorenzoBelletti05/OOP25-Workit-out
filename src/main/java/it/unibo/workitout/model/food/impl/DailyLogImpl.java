package it.unibo.workitout.model.food.impl;

import it.unibo.workitout.model.food.api.DailyLog;
import it.unibo.workitout.model.food.api.Food;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DailyLogImpl implements DailyLog {
    private final LocalDate date;
    private final Map<Food, Integer> consumedFoods = new HashMap<>();

    public DailyLogImpl(LocalDate date) {
        this.date = date;
    }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public void addFoodEntry(Food food, int grams) {
        //se il cibo è già presente, viene sommata a quella esistente
        consumedFoods.merge(food, grams, Integer::sum);
    }

    @Override
    public double getTotalKcal() {
        return consumedFoods.entrySet().stream()
            .mapToDouble(e -> (e.getKey().getKcalPer100g() * e.getValue()) / 100.0)
            .sum();
    }

    @Override
    public double getTotalProteins() {
        return consumedFoods.entrySet().stream()
            .mapToDouble(e -> (e.getKey().getProteins() * e.getValue()) / 100.0)
            .sum();
    }

    @Override
    public double getTotalCarbs() {
        return consumedFoods.entrySet().stream()
            .mapToDouble(e -> (e.getKey().getCarbs() * e.getValue()) / 100.0)
            .sum();
    }

    @Override
    public double getTotalFats() {
        return consumedFoods.entrySet().stream()
            .mapToDouble(e -> (e.getKey().getFats() * e.getValue()) / 100.0)
            .sum();
    }

    @Override
    public Map<Food, Integer> getFoodsConsumed() {
        return new HashMap<>(consumedFoods);
    }
}