package it.unibo.workitout.model.food.impl;

import it.unibo.workitout.model.food.api.DailyLog;
import it.unibo.workitout.model.food.api.Food;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * Implementation of a daily food log.
 */
public final class DailyLogImpl implements DailyLog {
    private static final double HUNDRED = 100.0;
    private static final double KCAL_PER_PROT_CARB = 4.0;
    private static final double KCAL_PER_FAT = 9.0;

    private final LocalDate date;
    private final Map<Food, Integer> consumedFoods = new HashMap<>();

    /**
     * Constructs a new daily log.
     * 
     * @param date the date of this log.
     */
    public DailyLogImpl(final LocalDate date) {
        this.date = date;
    }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public void addFoodEntry(final Food food, final int grams) {
        consumedFoods.merge(food, grams, Integer::sum);
    }

    @Override
    public double getTotalKcal() {
        return consumedFoods.entrySet().stream()
            .mapToDouble(e -> e.getKey().getKcalPer100g() * e.getValue() / HUNDRED)
            .sum();
    }

    @Override
    public double getTotalProteins() {
        return consumedFoods.entrySet().stream()
            .mapToDouble(e -> {
                final double kcalTotaliCibo = e.getKey().getKcalPer100g() * e.getValue() / HUNDRED;
                return e.getKey().getProteins() * kcalTotaliCibo / KCAL_PER_PROT_CARB;
            })
            .sum();
    }

    @Override
    public double getTotalCarbs() {
        return consumedFoods.entrySet().stream()
            .mapToDouble(e -> {
                final double kcalTotaliCibo = e.getKey().getKcalPer100g() * e.getValue() / HUNDRED;
                return e.getKey().getCarbs() * kcalTotaliCibo / KCAL_PER_PROT_CARB;
            })
            .sum();
    }

    @Override
    public double getTotalFats() {
        return consumedFoods.entrySet().stream()
            .mapToDouble(e -> {
                final double kcalTotaliCibo = e.getKey().getKcalPer100g() * e.getValue() / HUNDRED;
                return e.getKey().getFats() * kcalTotaliCibo / KCAL_PER_FAT;
            })
            .sum();
    }

    /**
     * Returns the percentage of calories from proteins.
     * 
     * @return percentage of calories from proteins.
     */
    public double getProteinPercentage() {
        final double totalKcal = getTotalKcal();
        if (totalKcal <= 0) {
            return 0;
        }
        return getTotalProteins() * KCAL_PER_PROT_CARB / totalKcal * HUNDRED;
    }

    /**
     * Returns the percentage of calories from carbs.
     * 
     * @return percentage of calories from carbs.
     */
    public double getCarbsPercentage() {
        final double totalKcal = getTotalKcal();
        if (totalKcal <= 0) {
            return 0;
        }
        return getTotalCarbs() * KCAL_PER_PROT_CARB / totalKcal * HUNDRED;
    }

    /**
     * Returns the percentage of calories from fats.
     * 
     * @return percentage of calories from fats.
     */
    public double getFatPercentage() {
        final double totalKcal = getTotalKcal();
        if (totalKcal <= 0) {
            return 0;
        }
        return getTotalFats() * KCAL_PER_FAT / totalKcal * HUNDRED;
    }

    @Override
    public Map<Food, Integer> getFoodsConsumed() {
        return Collections.unmodifiableMap(new HashMap<>(consumedFoods));
    }

    @Override
    public String toString() {
        return String.format(java.util.Locale.ROOT,
            "Log del %s: %.1f kcal [P: %.1f%%, C: %.1f%%, G: %.1f%%]",
            date.toString(), getTotalKcal(), getProteinPercentage(),
            getCarbsPercentage(), getFatPercentage());
    }
}
