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
            .mapToDouble(e -> {
                double kcalTotaliCibo = (e.getKey().getKcalPer100g() * e.getValue()) / 100;
                return (e.getKey().getProteins() * kcalTotaliCibo) / 4.0;
            })
            .sum();
    }

    @Override
    public double getTotalCarbs() {
        return consumedFoods.entrySet().stream()
            .mapToDouble(e -> {
                double kcalTotaliCibo = (e.getKey().getKcalPer100g() * e.getValue()) / 100.0;
                return (e.getKey().getCarbs() * kcalTotaliCibo) / 4.0;
            })
            .sum();
    }

    @Override
    public double getTotalFats() {
        return consumedFoods.entrySet().stream()
            .mapToDouble(e -> {
                double kcalTotaliCibo = (e.getKey().getKcalPer100g() * e.getValue()) / 100.0;
                return (e.getKey().getFats() * kcalTotaliCibo) / 9.0;
            })
            .sum();
    }

    //Calcolo delle percentuali dei nutrienti, seguendo le proporzioni: P: 4kcal/g, C: 4kcal/g, G: 9kcal/g

    //Calcola che percentuale di calorie proviene dalle proteine
    public double getProteinPercentage() {
        double totalKcal = getTotalKcal();
        if (totalKcal <= 0) return 0;
        return (getTotalProteins() * 4) / totalKcal * 100;
    }

    //calcola che percentuale di calorie proviene dai carboidrati
    public double getCarbsPercentage() {
        double totalKcal = getTotalKcal();
        if (totalKcal <= 0) return 0;
        return (getTotalCarbs() * 4) / totalKcal * 100;
    }

    //calcola che percentuale di calorie proviene dai grassi
    public double getFatPercentage() {
        double totalKcal = getTotalKcal();
        if (totalKcal <= 0) return 0;
        return (getTotalFats() * 9) / totalKcal * 100;
    }

    @Override
    public Map<Food, Integer> getFoodsConsumed() {
        return new HashMap<>(consumedFoods);
    }

    @Override
    public String toString() {
        return String.format("Log del %s: %.1f kcal [P: %.1f%%, C: %.1f%%, G: %.1f%%]",
            date.toString(), getTotalKcal(), getProteinPercentage(), getCarbsPercentage(), getFatPercentage());
    }
}